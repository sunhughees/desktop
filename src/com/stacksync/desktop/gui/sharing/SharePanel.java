package com.stacksync.desktop.gui.sharing;

import com.stacksync.commons.exceptions.ShareProposalNotCreatedException;
import com.stacksync.commons.exceptions.UserNotFoundException;
import com.stacksync.desktop.config.Config;
import com.stacksync.desktop.config.profile.Profile;
import com.stacksync.desktop.gui.error.ErrorMessage;
import com.stacksync.desktop.syncserver.Server;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SharePanel extends javax.swing.JPanel implements DocumentListener{

    private static final Config config = Config.getInstance();
    private ResourceBundle resourceBundle;
    
    private JFrame frame;
    
    /**
     * Creates new form SharePanel
     */
    public SharePanel(JFrame frame) {
        
        super();
        this.resourceBundle = config.getResourceBundle();
        this.frame = frame;
        initComponents();
        
        this.lblMail.setText(resourceBundle.getString("share_panel_email"));
        this.lblFolder.setText(resourceBundle.getString("share_panel_folder"));
        this.cancelButton.setText(resourceBundle.getString("wizard_cancel"));
        this.shareButton.setText(resourceBundle.getString("share_button_share"));
        this.shareButton.setEnabled(false);
        
        this.emailField.getDocument().addDocumentListener(this);
        this.folderNameField.getDocument().addDocumentListener(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        emailField = new javax.swing.JTextField();
        lblMail = new javax.swing.JLabel();
        lblFolder = new javax.swing.JLabel();
        folderNameField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        shareButton = new javax.swing.JButton();

        lblMail.setText("__E-mail:");

        lblFolder.setText("__Folder name:");

        cancelButton.setText("__Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        shareButton.setText("__Share");
        shareButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shareButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblFolder)
                            .addComponent(lblMail))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(folderNameField)
                            .addComponent(emailField, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(shareButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(folderNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFolder))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(shareButton)
                    .addComponent(cancelButton))
                .addContainerGap(36, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void shareButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shareButtonActionPerformed

        if (this.emailField.getText().isEmpty() || this.folderNameField.getText().isEmpty()) {
            return;
        }
        
        Profile profile = config.getProfile();
        Server server = profile.getServer();
        List<String> mails = new ArrayList<String>();
        mails.add(this.emailField.getText());
        try {
            server.createShareProposal(profile.getAccountId(), mails, this.folderNameField.getText());
            this.frame.setVisible(false);
        } catch (ShareProposalNotCreatedException ex) {
            ErrorMessage.showMessage(this, "Error", "An error ocurred, please try again later.\nVerify email accounts.");
        } catch (UserNotFoundException ex) {
            ErrorMessage.showMessage(this, "Error", "An error ocurred, please try again later.");
        }
    }//GEN-LAST:event_shareButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.frame.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField emailField;
    private javax.swing.JTextField folderNameField;
    private javax.swing.JLabel lblFolder;
    private javax.swing.JLabel lblMail;
    private javax.swing.JButton shareButton;
    // End of variables declaration//GEN-END:variables

    /*
     * Functions to enable/disable dynamically the share button
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        setEnableShareButton();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        setEnableShareButton();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        setEnableShareButton();
    }
    
    private void setEnableShareButton() {
        if (this.emailField.getText().equals("") ||
                this.folderNameField.getText().equals("")) {
            this.shareButton.setEnabled(false);
        } else {
            this.shareButton.setEnabled(true);
            getRootPane().setDefaultButton(this.shareButton);
        }
    }
}
