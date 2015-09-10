package com.stacksync.desktop.gui.tray;

import com.ast.cloudABE.GUI.UIUtils;
import com.ast.cloudABE.accessTree.AccessTree;
import com.ast.cloudABE.cloudABEClient.CloudABEClientAdapter;
import com.ast.cloudABE.exceptions.AttributeNotFoundException;
import com.ast.cloudABE.kpabe.KPABESecretKey;
import com.ast.cloudABE.kpabe.RevokeMessage;
import com.ast.cloudABE.kpabe.SystemKey;
import com.ast.cloudABE.userManagement.User;
import com.google.gson.Gson;
import com.stacksync.commons.exceptions.ShareProposalNotCreatedException;
import com.stacksync.commons.exceptions.UserNotFoundException;
import com.stacksync.commons.models.UserWorkspace;
import com.stacksync.desktop.ApplicationController;
import com.stacksync.desktop.Constants;
import com.stacksync.desktop.Environment;
import com.stacksync.desktop.config.Config;
import com.stacksync.desktop.config.profile.Profile;
import com.stacksync.desktop.db.DatabaseHelper;
import com.stacksync.desktop.db.models.CloneFile;
import com.stacksync.desktop.db.models.CloneWorkspace;
import com.stacksync.desktop.gui.error.ErrorMessage;
import com.stacksync.desktop.gui.sharing.SharePanel;
import com.stacksync.desktop.gui.sharing.UnsharePanel;
import com.stacksync.desktop.syncserver.Server;
import com.stacksync.desktop.util.FileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import org.apache.log4j.Logger;

/**
 *
 * @author cotes
 * @author ruizmarc
 */
public class TrayEventListenerImpl implements TrayEventListener {

    private final ApplicationController controller;
    private final Logger logger = Logger.getLogger(TrayEventListenerImpl.class.getName());

    public TrayEventListenerImpl(ApplicationController controller) {
        this.controller = controller;
    }

    @Override
    public void trayEventOccurred(TrayEvent event) {
        switch (event.getType()) {
            case OPEN_FOLDER:
                File folder = new File((String) event.getArgs().get(0));
                FileUtil.openFile(folder);
                break;

            /*case PREFERENCES:
             settingsDialog.setVisible(true);
             break;*/
            case WEBSITE:
                FileUtil.browsePage(Constants.APPLICATION_URL);
                break;

            case WEBSITE2:
                FileUtil.browsePage(Constants.APPLICATION_URL2);
                break;

            case PAUSE_SYNC:
                this.controller.pauseSync();
                break;

            case RESUME_SYNC:
                this.controller.resumeSync();
                break;
            case SHARE:
                this.share();
                break;
            case UNSHARE:
                this.unshare();
                break;
            case QUIT:
                this.controller.doShutdownTray();
                break;

            default:
                //checkthis
                logger.warn("Unknown tray event type: " + event);
            // Fressen.
        }
    }

    private JFrame createSharingFrame(String titleIdentificator) {

        Config config = Config.getInstance();
        ResourceBundle resourceBundle = config.getResourceBundle();

        String title = resourceBundle.getString(titleIdentificator);
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon(config.getResDir() + File.separator + "logo48.png").getImage());

        return frame;
    }

    private void share() {
        //Show share panel

        SharePanel panel = showSharePanel();

        Config config = Config.getInstance();
        Profile profile = config.getProfile();
        Server server = profile.getServer();

        DatabaseHelper db = DatabaseHelper.getInstance();
        CloneFile sharedFolder = db.getFileOrFolder(panel.getFolderSelected());

        Map<String, CloneWorkspace> workspaces = db.getWorkspaces();

        String publicKeyjson = null;

        boolean alreadyShared = false;
        boolean invited = false;

        try {
            if (panel.isAbeEncrypted()) {

                Gson gson = new Gson();
                String RESOURCES_PATH = Environment.getInstance().getDefaultUserConfigDir().getAbsolutePath() + "/conf/abe/";

                CloudABEClientAdapter abeClient = null;

                abeClient = new CloudABEClientAdapter(RESOURCES_PATH);

                for (CloneWorkspace workspace : workspaces.values()) {

                    if (workspace.getPathWorkspace().equals("/" + sharedFolder.getRelativePath()) && workspace.isAbeEncrypted()) { //If a workspace already exists in the shared folder

                        if (workspace.getMasterKey() != null) { //If i'm the owner, reuse master key

                            alreadyShared = true;

                            SystemKey masterKey = gson.fromJson(new String(workspace.getMasterKey()), SystemKey.class);
                            SystemKey publicKey = gson.fromJson(new String(workspace.getPublicKey()), SystemKey.class);

                            publicKeyjson = new String(workspace.getPublicKey());

                            try {
                                abeClient.setupABESystem(0, publicKey, masterKey, workspace.getGroupGenerator());
                            } catch (AttributeNotFoundException ex) {
                                logger.error(ex);
                            }

                        } else { //If the workspace is already shared with me but, i'm not the owner
                            invited = true;
                        }
                        break;
                    }
                }

                CloneWorkspace workspace = null;
                
                if (!alreadyShared && !invited) { // If not shared and i'm not invited, I will be the owner

                    abeClient.setupABESystem(0);
                    publicKeyjson = gson.toJson(abeClient.getPublicKey());
                    String masterKey = gson.toJson(abeClient.getMasterKey());

                    workspace = createSharedCloneWorkspace(abeClient, sharedFolder, masterKey, publicKeyjson, profile.getAccountId());
                }

                if (!invited) {

                    inviteABEUsers(panel.getEmails(), abeClient, server, profile, publicKeyjson, sharedFolder.getId(), RESOURCES_PATH, gson);

                } else {
                    ErrorMessage.showMessage(panel, "Error", "You don't have permission to invited new users.");
                    return;
                }
                
            } else {
                server.createShareProposal(profile.getAccountId(), panel.getEmails(), sharedFolder.getId(), false, panel.isAbeEncrypted());
            }

        } catch (ShareProposalNotCreatedException ex) {
            ErrorMessage.showMessage(panel, "Error", "An error ocurred, please try again later.\nVerify email accounts.");
        } catch (Exception ex) {
            ErrorMessage.showMessage(panel, "Error", "An error ocurred, please try again later.");
        }
    }

    private SharePanel showSharePanel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            logger.error(ex);
        } catch (InstantiationException ex) {
            logger.error(ex);
        } catch (IllegalAccessException ex) {
            logger.error(ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            logger.error(ex);
        }

        JFrame frame = createSharingFrame("share_panel_title");

        SharePanel panel = new SharePanel(frame);

        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);

        synchronized (panel.lock) {
            try {
                panel.lock.wait();
            } catch (InterruptedException ex) {
                logger.error(ex);
            }
        }

        return panel;
    }

    private CloneWorkspace createSharedCloneWorkspace(CloudABEClientAdapter abeClient, CloneFile sharedFolder, String masterKey, String publicKeyjson, String accountID) {

        CloneWorkspace newWorkspace = new CloneWorkspace();

        newWorkspace.setId(sharedFolder.getId().toString());
        newWorkspace.setName(sharedFolder.getName());
        newWorkspace.setLocalLastUpdate(0);
        newWorkspace.setRemoteRevision(0);
        newWorkspace.setEncrypted(false);
        newWorkspace.setAbeEncrypted(true);
        newWorkspace.setDefaultWorkspace(false);
        newWorkspace.setOwner(accountID);
        newWorkspace.getPathWorkspace();
        newWorkspace.setIsApproved(false);
        newWorkspace.setGroupGenerator(abeClient.getGroupGenerator());

        newWorkspace.setMasterKey(masterKey.getBytes());
        newWorkspace.setPublicKey(publicKeyjson.getBytes());

        HashMap<String, Long> attributeVersionInit = new HashMap<String, Long>();

        for (String attribute : abeClient.getAttributeUniverse()) {
            attributeVersionInit.put(attribute, new Long(1));
        }

        newWorkspace.setAttributesVersion(attributeVersionInit);

        newWorkspace.merge();

        return newWorkspace;
    }

    private void inviteABEUsers(List<String> emails, CloudABEClientAdapter abeClient, Server server, Profile profile, String publicKeyjson, Long sharedFolderId, String RESOURCES_PATH, Gson gson) throws AttributeNotFoundException, ShareProposalNotCreatedException, UserNotFoundException {

        HashMap<String, HashMap<String, byte[]>> emailsKeys = new HashMap<String, HashMap<String, byte[]>>();

        for (String email : emails) {
            System.out.println("Setting permissions for: " + email);

            String attSet = UIUtils.getAccessStructure(RESOURCES_PATH, null, email);

            User invitedUser = abeClient.newABEUserInvited(attSet);
            AccessTree accessTree = invitedUser.getSecretKey().getAccess_tree();

            KPABESecretKey secretKeyLight = new KPABESecretKey(invitedUser.getSecretKey().getLeaf_keys(), null);

            String secretKeyjson = gson.toJson(secretKeyLight);

            HashMap<String, byte[]> secretKeyStruct = new HashMap<String, byte[]>();

            secretKeyStruct.put("secret_key", secretKeyjson.getBytes());
            secretKeyStruct.put("access_struct", accessTree.toString().getBytes());

            emailsKeys.put(email, secretKeyStruct);

            System.out.println("[" + email + "] Setting up access logical expression to: " + attSet);
        }

        HashMap<String, Integer> attributeUniverse = new HashMap<String, Integer>();
        
        for(int i=0; i<abeClient.getAttributeUniverse().size(); i++){
            attributeUniverse.put(abeClient.getAttributeUniverse().get(i), i+1);
        }
        
        /*FIXME! Be careful, emails and keys are sent in plain text without encryption, 
         key distribution problem should be solved in order to guarantee security and privacy */
        server.createShareProposal(profile.getAccountId(), publicKeyjson.getBytes(), emailsKeys, sharedFolderId, false, true, attributeUniverse);

    }

    private void unshare() {
        
        UnsharePanel panelUnshare = showUnsharePanel();
                
        DatabaseHelper database = DatabaseHelper.getInstance();

        Map<String, CloneWorkspace> allworkspaces = database.getWorkspaces();

        CloneFile unsharedFolder = database.getFileOrFolder(panelUnshare.getFolderSelected());

        //Check if the selected folder is a workspace
        for (CloneWorkspace workspace : allworkspaces.values()) {

            if (workspace.getPathWorkspace().equals("/" + unsharedFolder.getRelativePath()) && workspace.isAbeEncrypted()) { //If the folder is a workspace

                if (workspace.getMasterKey() == null) {  //If the workspace is already shared with me but, i'm not the owner

                    ErrorMessage.showMessage(panelUnshare, "Error", "You are not the owner of the folder.");

                } else { // Else I'm the owner, and only the owner can revoke

                    String RESOURCES_PATH = Environment.getInstance().getDefaultUserConfigDir().getAbsolutePath() + "/conf/abe/";

                    try {

                        Gson gson = new Gson();
                        CloudABEClientAdapter abeOwner = new CloudABEClientAdapter(RESOURCES_PATH);

                        SystemKey ownerMasterKey = gson.fromJson(new String(workspace.getMasterKey()), SystemKey.class);
                        SystemKey ownerPublicKey = gson.fromJson(new String(workspace.getPublicKey()), SystemKey.class);

                        abeOwner.setupABESystem(0, ownerPublicKey, ownerMasterKey, workspace.getGroupGenerator());

                        /*
                                
                         // 1. Load attributes
                         ArrayList<String> attributeUniverse = CloudInvitedABEClientAdapter.getAttUniverseFromXML(RESOURCES_PATH + CABEConstants.XML_PATH);
                                
                         //BE CAREFUL DATA OWNER DOESN'T HAVE ACCES STRUCTRE - TO FIX
                         // 2. Create AccessTree class from the access structure string (i.e. Attr1 & Attr2)
                         AccessTree accessTree = KPABE.setAccessTree(workspace.getAccessStructure());
                                
                         // 3. Adjust the IDs of the tree with the ones used in the attribute universe.
                         accessTree = new AccessTree(AccessTreeIDsAdjuster.adjustAccessTreeIDs(accessTree, attributeUniverse));

                         // First we create the secretKey from the workspace instance, but it lacks the access tree.
                         KPABESecretKey secretKey = gson.fromJson(new String(workspace.getSecretKey()),  KPABESecretKey.class); 
                         // Here we set the access tree for the secret key and we get the final SK necessary to the ABEEncryption.
                         secretKey = new KPABESecretKey(secretKey.getLeaf_keys(),accessTree);
                                
                         */
                        //It is null because it is not used by the moment.
                        KPABESecretKey secretKey = null;

                        Config configUnshare = Config.getInstance();
                        Profile profileUnshare = configUnshare.getProfile();
                        Server serverUnshare = profileUnshare.getServer();

                        List<UserWorkspace> workspaceUsers = serverUnshare.getWorkspaceMembers(profileUnshare.getAccountId(), workspace.getId());

                        List<RevokeMessage> revokeMessages = new ArrayList();

                        for (String email : panelUnshare.getEmails()) {

                            boolean found = false;

                            for (UserWorkspace userWorkspace : workspaceUsers) {
                                if (userWorkspace.getUser().getEmail().equals(email)) {
                                    String accessStruc = userWorkspace.getAccessStruc();
                                    revokeMessages.add(abeOwner.revokeUser(email, accessStruc, workspace.getAttributesVesion(), secretKey));
                                    found = true;
                                    break;
                                }
                            }

                            if (!found) {
                                ErrorMessage.showMessage(panelUnshare, "Error", "Email " + email + " is not in this workspace");
                            }
                        }

                        //TODO - Very important!!
                        //workspace.setPublicKey(abeOwner.getPublicKey());
                        //workspace.setMasterKey(abeOwner.getMasterKey());
                    } catch (Exception ex) {
                        java.util.logging.Logger.getLogger(TrayEventListenerImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                break;
            }
        }
    }
    
     private UnsharePanel showUnsharePanel(){
         try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            logger.error(ex);
        } catch (InstantiationException ex) {
            logger.error(ex);
        } catch (IllegalAccessException ex) {
            logger.error(ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            logger.error(ex);
        }

        JFrame frameUnshare = createSharingFrame("share_panel_title");

        UnsharePanel panelUnshare = new UnsharePanel(frameUnshare);

        frameUnshare.setContentPane(panelUnshare);
        frameUnshare.pack();
        frameUnshare.setVisible(true);

        synchronized (panelUnshare.lock) {
            try {
                panelUnshare.lock.wait();
            } catch (InterruptedException ex) {
                logger.error(ex);
            }
        }
        
        return panelUnshare;

     }
}
