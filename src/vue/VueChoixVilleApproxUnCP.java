/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modele.dao.DaoVille;
import modele.dao.Jdbc;
import modele.metier.Ville;

/**
 *
 * @author btssio
 */
public class VueChoixVilleApproxUnCP extends javax.swing.JDialog {

    private Ecouteur ecouteur;          // l'écouteur d'événements

    /**
     * Creates new form VueChoixVille
     */
    public VueChoixVilleApproxUnCP(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        // instanciation d'un listener
        ecouteur = new Ecouteur(this);
        // ajout du listener au différents contrôles écoutés
        jComboBoxVille.addActionListener(ecouteur);
        jTextFieldCP.addKeyListener(ecouteur);
        jButtonValider.addActionListener(ecouteur);
        jButtonAnnuler.addActionListener(ecouteur);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButtonValider = new javax.swing.JButton();
        jComboBoxVille = new javax.swing.JComboBox();
        jTextFieldCP = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldCPChoisi = new javax.swing.JTextField();
        jButtonAnnuler = new javax.swing.JButton();

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        jLabel1.setText("Choix d'une ville");

        jLabel2.setText("Code postal  :");

        jLabel3.setText("Ville :");

        jButtonValider.setText("Valider");

        jComboBoxVille.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxVille.setModel(new javax.swing.DefaultComboBoxModel(new String[]{""}));

        jLabel5.setText("Code choisi :");

        jTextFieldCPChoisi.setEnabled(false);

        jButtonAnnuler.setText("Annuler");
        jButtonAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnnulerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(121, 121, 121))
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jButtonAnnuler)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(jButtonValider)
                        .addGap(74, 74, 74))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldCPChoisi, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxVille, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldCP, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxVille, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldCPChoisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonValider)
                    .addComponent(jButtonAnnuler))
                .addGap(23, 23, 23))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnulerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAnnulerActionPerformed

    private void initialisationComboVilles() {
        String unCp = jTextFieldCP.getText().trim();
//        int longueurCp = unCp.length();
//        if (longueurCp < 5) {
//            unCp += '%';
//        }
        Jdbc.creer("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:", "@localhost:1521:XE", "", "btssio", "btssio");

        try {
            Jdbc.getInstance().connecter();
            DefaultComboBoxModel jComboBoxModelVille = new DefaultComboBoxModel();
            List<Ville> lesVilles = DaoVille.selectAllByCpApprox(unCp, 2, 1);
            for (Ville ville : lesVilles) {
                jComboBoxModelVille.addElement(ville);
            }
            jComboBoxVille.setModel(jComboBoxModelVille);
            if (lesVilles.size() > 0) {
                if (jTextFieldCP.getText().length() == 5) {
                    jTextFieldCPChoisi.setText(jTextFieldCP.getText());
                } else {
                    jTextFieldCPChoisi.setText(((Ville)jComboBoxModelVille.getElementAt(0)).getCp());
                }
            } else {
                jTextFieldCPChoisi.setText("");
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Pilote absent");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Echec de connexion");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VueChoixVilleApproxUnCP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VueChoixVilleApproxUnCP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VueChoixVilleApproxUnCP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VueChoixVilleApproxUnCP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

  
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VueChoixVilleApproxUnCP dialog = new VueChoixVilleApproxUnCP(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    /**
     * classe privée Ecouteur d'événements, implémentant les méthodes de
     * l'interface ActionListener, ie la méthode actionPerformed et els méthodes
     * de l'interface KeyListener, ie les méthodes keyTyped, keyPressed et
     * keyReleased
     */
    private class Ecouteur implements ActionListener, KeyListener {

        private VueChoixVilleApproxUnCP vue;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jComboBoxVille) {
                jTextFieldCPChoisi.setText(((Ville) jComboBoxVille.getSelectedItem()).getCp());
            } else if (e.getSource() == jButtonValider) {
                if (!jTextFieldCPChoisi.getText().equals("")) {
                    Ville ville = (Ville) jComboBoxVille.getSelectedItem();
                    ((VueAdresse_M5_UnCP) (this.getVue().getParent())).getjTextFieldCdp().setText(jTextFieldCPChoisi.getText());
                    ((VueAdresse_M5_UnCP) (this.getVue().getParent())).getjTextFieldVille().setText(ville.getNom());
                    ((VueAdresse_M5_UnCP) (this.getVue().getParent())).setVilleCourante(ville);
                    this.getVue().dispose();
                } else {
                    JOptionPane.showMessageDialog(this.getVue(), "Vous devez saisir le début d'un code postal existant ou Annuler");
                    this.getVue().jTextFieldCP.requestFocus();
                }
            } else if (e.getSource() == jButtonAnnuler) {
                    this.getVue().dispose();                
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == Event.ENTER) {
                if (!jTextFieldCP.getText().equals("")) {
                    initialisationComboVilles();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        public VueChoixVilleApproxUnCP getVue() {
            return vue;
        }

        public void setVue(VueChoixVilleApproxUnCP vue) {
            this.vue = vue;
        }

        public Ecouteur(VueChoixVilleApproxUnCP vue) {
            this.vue = vue;
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnnuler;
    private javax.swing.JButton jButtonValider;
    private javax.swing.JComboBox jComboBoxVille;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jTextFieldCP;
    private javax.swing.JTextField jTextFieldCPChoisi;
    // End of variables declaration//GEN-END:variables
}
