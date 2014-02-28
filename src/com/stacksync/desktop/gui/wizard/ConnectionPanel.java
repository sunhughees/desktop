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
package com.stacksync.desktop.gui.wizard;

import javax.swing.JPanel;
import com.stacksync.desktop.config.profile.Profile;
import com.stacksync.desktop.config.Repository;
import com.stacksync.desktop.connection.plugins.Connection;
import com.stacksync.desktop.connection.plugins.PluginInfo;
import com.stacksync.desktop.connection.plugins.Plugins;
import com.stacksync.desktop.gui.settings.SettingsPanel;

/**
 *
 * @author pheckel
 */
public class ConnectionPanel extends SettingsPanel {    
    private Connection connection;       
    private SettingsPanel pnlConnection;

    /** Creates new form ProfileBasicsPanel2 */
    public ConnectionPanel(Profile profile) {
        this.profile = profile;
        this.pnlConnection = null;
                
        initComponents();
	  
        scrConnection.setViewportView(new JPanel());
        
        // setting text //  
        jLabel9.setText(resourceBundle.getString("cp_simple_title"));
        jLabel10.setText(resourceBundle.getString("cp_simple_intro1"));
        jLabel11.setText(resourceBundle.getString("cp_simple_intro2"));
        lblConnectionTitle.setText(resourceBundle.getString("cp_simple_user_credentials"));
    }

    @Override
    public void clean(){
        if(pnlConnection != null){
            pnlConnection.clean();
        }
    }
    
    @Override
    public boolean check() {        
        boolean check = false;
        if(pnlConnection != null){
            check = pnlConnection.check();
        }
        
        return check;
    }

    @Override
    public void load() {        
        if(pnlConnection == null){
            PluginInfo pluginInfo = Plugins.get("swift_comercial");
            connection = pluginInfo.createConnection();
            pnlConnection = connection.createConfigPanel();
            scrConnection.setViewportView(pnlConnection);
        }
    }

    @Override
    public void save() {
        // Get name
        String newProfileName = resourceBundle.getString("cp_new_profile_name");
        /*String newProfileNameSyntax = resourceBundle.getString("cp_new_profile_name").concat(" %d");
        int newProfileCount = 1;

        while (config.getProfile().get(newProfileName) != null) {
            newProfileName = String.format(newProfileNameSyntax, newProfileCount++);
        }*/

        profile.setName(newProfileName);
        
        // Connection
        pnlConnection.save(); // saves 'connection'

        // Repo
        Repository repository = new Repository();
        repository.setConnection(connection);

        profile.setRepository(repository);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked") 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        scrConnection = new javax.swing.JScrollPane();
        lblConnectionTitle = new javax.swing.JLabel();

        jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getStyle() | java.awt.Font.BOLD, jLabel9.getFont().getSize()+3));
        jLabel9.setText("__Welcome to StackSync");
        jLabel9.setName("lblTitle"); // NOI18N

        jLabel10.setText("__Please, introduce your user credentials.");
        jLabel10.setName("lblIntro1"); // NOI18N

        jLabel11.setText("__If you don't have your credentials, please contact your administrator.");
        jLabel11.setName("lblIntro2"); // NOI18N

        scrConnection.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        scrConnection.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        scrConnection.setName("scrConnection"); // NOI18N

        lblConnectionTitle.setFont(lblConnectionTitle.getFont().deriveFont(lblConnectionTitle.getFont().getStyle() | java.awt.Font.BOLD));
        lblConnectionTitle.setText("__User credentials");
        lblConnectionTitle.setName("lblUserCredentials"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrConnection, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblConnectionTitle, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(4, 4, 4)
                .addComponent(jLabel11)
                .addGap(54, 54, 54)
                .addComponent(lblConnectionTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblConnectionTitle;
    private javax.swing.JScrollPane scrConnection;
    // End of variables declaration//GEN-END:variables
}
