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

/*
 * ProfilePanel.java
 *
 * Created on Mar 26, 2011, 2:40:43 PM
 */

package com.stacksync.desktop.gui.settings;

import com.stacksync.desktop.config.profile.Profile;

/**
 *
 * @author Philipp C. Heckel <philipp.heckel@gmail.com>
 */
public final class ProfilePanel extends SettingsPanel {

    /** Creates new form ProfilePanel */
    public ProfilePanel(Profile profile) {
        this.profile = profile;
        
        initComponents();
        load();
        
        /// setting text ///
        jLabel1.setText(resourceBundle.getString("profp_name"));
        cbActive.setText(resourceBundle.getString("profp_activate"));               
        jLabel2.setText(resourceBundle.getString("profp_settings"));
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    public void load() {
        cbActive.setSelected(profile.isEnabled());
        txtProfileName.setText(profile.getName());
    }

    @Override
    public void save() {
        profile.setEnabled(cbActive.isSelected());
        profile.setName(txtProfileName.getText());
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
        txtProfileName = new javax.swing.JTextField();
        cbActive = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();

        jLabel1.setLabelFor(txtProfileName);
        jLabel1.setText("Profile Name:");
        jLabel1.setName("jLabel1"); // NOI18N

        txtProfileName.setName("txtProfileName"); // NOI18N

        cbActive.setText("Activate Profile");
        cbActive.setName("cbActive"); // NOI18N

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() | java.awt.Font.BOLD));
        jLabel2.setText("Profile Settings");
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(cbActive)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProfileName, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbActive)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProfileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(213, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbActive;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtProfileName;
    // End of variables declaration//GEN-END:variables

    @Override
    public void clean() {    }
    
    @Override
    public boolean check() {        
        return true;
    }

}