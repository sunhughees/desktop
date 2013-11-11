/*
 * Syncany, www.syncany.org
 * Copyright (C) 2011 Philipp C. Heckel <philipp.heckel@gmail.com> 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.stacksync.desktop.connection.plugins.ftp;

import com.stacksync.desktop.connection.plugins.ConfigPanel;
import com.stacksync.desktop.connection.plugins.TransferManager;
import com.stacksync.desktop.exceptions.StorageConnectException;
import com.stacksync.desktop.gui.error.ErrorMessage;

/**
 *
 * @author Philipp C. Heckel <philipp.heckel@gmail.com>
 */
public class FtpConfigPanel extends ConfigPanel {
	
    /** Creates new form FtpConnectionPanel */
    public FtpConfigPanel(FtpConnection connection) {
        super(connection);
        initComponents();
        
        /// setting text ///
        jLabel1.setText(resourceBundle.getString("ftpc_server_name"));
        jLabel2.setText(resourceBundle.getString("ftpc_username"));
        jLabel3.setText(resourceBundle.getString("ftpc_password"));
        jLabel4.setText(resourceBundle.getString("ftpc_port"));
        jLabel7.setText(resourceBundle.getString("ftpc_path"));
        
    }

    @Override
    public void load() {
        txtHost.setText(getConnection().getHost());
        spnPort.setValue(new Integer( (getConnection().getPort() == 0) ? 21 : getConnection().getPort() ));
        txtUsername.setText(getConnection().getUsername());
        txtPassword.setText(getConnection().getPassword());
        txtFolder.setText(getConnection().getPath());
    }

    @Override
    public void save() {
        getConnection().setHost(txtHost.getText());
        getConnection().setPort((Integer) spnPort.getValue());
        getConnection().setUsername(txtUsername.getText());
        getConnection().setPassword(new String(txtPassword.getPassword()));
        getConnection().setPath(txtFolder.getText());
    }

    @Override
    public FtpConnection getConnection() {
        return (FtpConnection) super.getConnection();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtHost = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spnPort = new javax.swing.JSpinner();
        txtPassword = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        txtFolder = new javax.swing.JTextField();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Server Name:");
        jLabel1.setName("jLabel1"); // NOI18N

        txtHost.setText("10.30.239.228");
        txtHost.setName("txtHost"); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("User Name:");
        jLabel2.setName("jLabel2"); // NOI18N

        txtUsername.setText("testerftp");
        txtUsername.setName("txtUsername"); // NOI18N

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Password:");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText("Port:");
        jLabel4.setName("jLabel4"); // NOI18N

        spnPort.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(21), Integer.valueOf(1), null, Integer.valueOf(1)));
        spnPort.setName("spnPort"); // NOI18N

        txtPassword.setText("testpass");
        txtPassword.setName("txtPassword"); // NOI18N

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Path:");
        jLabel7.setName("jLabel7"); // NOI18N

        txtFolder.setText("/home/testerftp");
        txtFolder.setName("txtFolder"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7))
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtHost, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(spnPort, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtPassword)
                    .addComponent(txtUsername)
                    .addComponent(txtFolder))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(spnPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSpinner spnPort;
    private javax.swing.JTextField txtFolder;
    private javax.swing.JTextField txtHost;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

    @Override
    public void clean() {  
        txtFolder.setText("");
        txtHost.setText("");
        txtPassword.setText("");
        txtUsername.setText("");    
    }
    
    private String getUserName(){
        return txtUsername.getText().trim();
    }
    
    private String getPassword(){
        return new String(txtPassword.getPassword());
    }
    
    private String getFolder(){
        return txtFolder.getText().trim();
    }
    
    private String getHost(){
        return txtHost.getText().trim();
    }
    
    private Integer getPort(){
        return Integer.parseInt(spnPort.getValue().toString());
    }
    
    
    @Override
    public boolean check() {
        String host = getHost();
        String userName = getUserName();
        String password = getPassword();
        
        Integer port = getPort();
        String folder = getFolder();
        
        // check UserName
        if(userName.isEmpty()){
            ErrorMessage.showMessage(this, "Error", "The username is empty.");
            return false;
        } else{        
            if(userName.contains(":") || userName.contains("-") || userName.contains("_") || 
               userName.contains("/") || userName.contains("\\")){
               ErrorMessage.showMessage(this, "Error", "The username have invalid characters(:-_/\\)");
               return false;
            }
        }

        // check Password
        if(password.isEmpty()){
            ErrorMessage.showMessage(this, "Error", "The password is empty.");
            return false;
        }
        
        // check Server
        if(host.isEmpty()){
            ErrorMessage.showMessage(this, "Error", "The host is empty.");
            return false;
        }        
          
        //ping to host
        FtpConnection connection = new FtpConnection();
        connection.setHost(host);
        connection.setPort(port);
        
        connection.setUsername(userName);
        connection.setPassword(password);
        connection.setPath(folder);
        
        TransferManager transfer = connection.createTransferManager();
        try {           
            transfer.connect();
        } catch (StorageConnectException ex) {
            ErrorMessage.showMessage(this, "Error", "The host is incorrect. Host is not reachable.\n" + ex.getMessage());
            return false;
        }            

        return true;
    }

}