package com.stacksync.desktop.gui.sharing;

import com.stacksync.desktop.config.Config;
import java.awt.Frame;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author cotes
 */
public class PasswordDialog extends java.awt.Dialog implements DocumentListener {

    protected final ResourceBundle resourceBundle = Config.getInstance().getResourceBundle();
    private String password;
    
    /**
     * Creates new form PasswordDialog
     */
    public PasswordDialog(java.awt.Frame parent, boolean modal, String folderName) {
        super(parent, modal);
        initComponents();
        
        this.passwordField.setText("");
        this.confirmationField.setText("");
        this.passwordLabel.setText(resourceBundle.getString("pass_panel_password"));
        this.confirmationLabel.setText(resourceBundle.getString("pass_panel_confirm"));
        this.acceptButton.setText(resourceBundle.getString("pass_panel_accept"));
        this.cancelButton.setText(resourceBundle.getString("pass_panel_cancel"));
        this.descriptionLbl.setText(resourceBundle.getString("pass_panel_description")+folderName+":</html>");
        setTitle(resourceBundle.getString("pass_panel_title"));
        this.acceptButton.setEnabled(false);
        this.passwordField.getDocument().addDocumentListener(this);
        this.confirmationField.getDocument().addDocumentListener(this);
        
        setLocationRelativeTo(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        passwordLabel = new javax.swing.JLabel();
        confirmationLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        confirmationField = new javax.swing.JPasswordField();
        acceptButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        descriptionLbl = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        passwordLabel.setText("jLabel3");

        confirmationLabel.setText("jLabel4");

        passwordField.setText("jPasswordField3");

        confirmationField.setText("jPasswordField4");

        acceptButton.setText("jButton2");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("jButton1");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        descriptionLbl.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(descriptionLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(passwordLabel)
                            .addComponent(confirmationLabel))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cancelButton)
                                .addGap(37, 37, 37)
                                .addComponent(acceptButton)
                                .addGap(0, 0, 0))
                            .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                            .addComponent(confirmationField))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(descriptionLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordField))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmationLabel)
                    .addComponent(confirmationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acceptButton)
                    .addComponent(cancelButton))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        tryExit();
    }//GEN-LAST:event_closeDialog

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButtonActionPerformed
        this.password = new String(this.passwordField.getPassword());
        setVisible(false);
        dispose();
    }//GEN-LAST:event_acceptButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        tryExit();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void tryExit() {
        String title = resourceBundle.getString("pass_panel_ed_title");
        String descr = resourceBundle.getString("pass_panel_ed_descr");
        int result = JOptionPane.showConfirmDialog(new Frame(), descr, title, JOptionPane.YES_NO_OPTION);
        if (result == 0) {
            this.password = null;
            setVisible(false);
            dispose();
        }
    }
    
    public String getPassword() {
        return password;
    }
    
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
        
        if (this.passwordField.getPassword().length == 0 ||
                this.confirmationField.getPassword().length == 0) {
            this.acceptButton.setEnabled(false);
        } else {
            this.acceptButton.setEnabled(true);
        }
    }
    
    public static void main(String[] args) {
        PasswordDialog dialog = new PasswordDialog(new Frame(), true, "Test");
        dialog.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPasswordField confirmationField;
    private javax.swing.JLabel confirmationLabel;
    private javax.swing.JLabel descriptionLbl;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    // End of variables declaration//GEN-END:variables
}
