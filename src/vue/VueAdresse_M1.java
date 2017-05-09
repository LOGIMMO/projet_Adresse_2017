/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modele.dao.DaoAdresse;
import modele.dao.Jdbc;
import modele.metier.Adresse;

/**
 * Version sans gestion de la touche ESCAPE dans le champ de saisie de l'Id
 * événement ActionPerformed sur jTextFieldId
 * @author btssio
 */
public class VueAdresse_M1 extends javax.swing.JFrame {

    private int etat; // 1 affichage, 2 ajout, 3 suppression, 4 modification, 
    // 5 recherche, possibilité d'utiliser enum

    private Adresse adresseCourante;    // l'adresse courante

    private Ecouteur ecouteur;          // l'écouteur d'événements

    /**
     * Creates new form VueAdresse
     */
    public VueAdresse_M1() {
        // initialisation des composants grahiques de l'interface
        initComponents();
        // instanciation d'un listener
        ecouteur = new Ecouteur();
        // ajout du listener au différents contrôles écoutés
        jButtonRechercher.addActionListener(ecouteur);
        jButtonAjouter.addActionListener(ecouteur);
        jButtonModifier.addActionListener(ecouteur);
        jButtonSupprimer.addActionListener(ecouteur);
        jButtonValider.addActionListener(ecouteur);
        jButtonAnnuler.addActionListener(ecouteur);
        jButtonQuitter.addActionListener(ecouteur);
        jTextFieldId.addActionListener(ecouteur);


        // création du singleton Jdbc
        Jdbc.creer("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:", "@localhost:1521:XE", "", "btssio", "btssio");

        // affichage de la première adresse
        afficherPremiereAdresse();
    }

    /**
     * Fixe les contrôles actifs en mode Affichage. les zones de saisie sont
     * inactives les boutons Ajouter, Modifier, supprimer sont actifs les
     * boutons Annuler et Valider sont inactifs le bouton Quitter est toujours
     * actif etat vaut 1
     */
    private void modeAffichage() {
        etat = 1;
        jTextFieldId.setEnabled(false);
        jTextFieldRue.setEnabled(false);
        jTextFieldCdp.setEnabled(false);
        jTextFieldVille.setEnabled(false);
        jButtonAjouter.setEnabled(true);
        jButtonModifier.setEnabled(true);
        jButtonSupprimer.setEnabled(true);
        jButtonRechercher.setEnabled(true);
        jButtonAnnuler.setEnabled(false);
        jButtonValider.setEnabled(false);
    }

    /**
     * Fixe les contrôles actifs en mode Ajout. les zones de saisie sont actives
     * les boutons Ajouter, Modifier, supprimer sont inactifs les boutons
     * Annuler et Valider sont actifs le bouton Quitter est toujours actif etat
     * vaut 2
     */
    private void modeAjout() {
        etat = 2;
        jTextFieldId.setEnabled(true);
        jTextFieldRue.setEnabled(true);
        jTextFieldCdp.setEnabled(true);
        jTextFieldVille.setEnabled(true);
        jButtonAjouter.setEnabled(false);
        jButtonModifier.setEnabled(false);
        jButtonSupprimer.setEnabled(false);
        jButtonRechercher.setEnabled(false);
        jButtonAnnuler.setEnabled(true);
        jButtonValider.setEnabled(true);
    }

    /**
     * Fixe les contrôles actifs en mode Suppression. les zones de saisie sont
     * inactives les boutons Ajouter, Modifier, supprimer sont inactifs les
     * boutons Annuler et Valider sont inactifs le bouton Quitter est toujours
     * actif etat vaut 3
     */
    private void modeSuppression() {
        etat = 3;
        jTextFieldId.setEnabled(false);
        jTextFieldRue.setEnabled(false);
        jTextFieldCdp.setEnabled(false);
        jTextFieldVille.setEnabled(false);
        jButtonAjouter.setEnabled(false);
        jButtonModifier.setEnabled(false);
        jButtonSupprimer.setEnabled(false);
        jButtonRechercher.setEnabled(false);
        jButtonAnnuler.setEnabled(false);
        jButtonValider.setEnabled(false);
    }

    /**
     * Fixe les contrôles actifs en mode Modification. les zones de saisie sont
     * inactives, sauf celle de l'id les boutons Ajouter, Modifier, supprimer
     * sont inactifs les boutons Annuler et Valider sont actifs le bouton
     * Quitter est toujours actif etat vaut 4
     */
    private void modeModification() {
        etat = 4;
        jTextFieldId.setEnabled(false);
        jTextFieldRue.setEnabled(true);
        jTextFieldCdp.setEnabled(true);
        jTextFieldVille.setEnabled(true);
        jButtonAjouter.setEnabled(false);
        jButtonModifier.setEnabled(false);
        jButtonSupprimer.setEnabled(false);
        jButtonRechercher.setEnabled(false);
        jButtonAnnuler.setEnabled(true);
        jButtonValider.setEnabled(true);
    }

    /**
     * Fixe les contrôles actifs en mode Recherche. La zone de saisie de l'id
     * est active, les autres inactives les boutons Ajouter, Modifier, supprimer
     * sont inactifs les boutons Annuler et Valider sont actifs le bouton
     * Quitter est toujours actif etat vaut 5
     */
    private void modeRecherche() {
        etat = 5;
        jTextFieldId.setEnabled(true);
        jTextFieldRue.setEnabled(false);
        jTextFieldCdp.setEnabled(false);
        jTextFieldVille.setEnabled(false);
        jButtonAjouter.setEnabled(false);
        jButtonModifier.setEnabled(false);
        jButtonSupprimer.setEnabled(false);
        jButtonRechercher.setEnabled(false);
        jButtonAnnuler.setEnabled(true);
        jButtonValider.setEnabled(true);
    }

    /**
     * Affiche dans l'interface une adresse passée en paramètre.
     *
     * @param l'adresse à afficher
     */
    private void afficherAdresse(Adresse uneAdresse) {
        this.jTextFieldId.setText(Integer.toString(uneAdresse.getId()));
        this.jTextFieldRue.setText(uneAdresse.getRue());
        this.jTextFieldCdp.setText(uneAdresse.getCp());
        this.jTextFieldVille.setText(uneAdresse.getVille());
    }

    /**
     * Remet à vide les zones de saisie.
     */
    private void effacerAdresse() {
        this.jTextFieldId.setText("");
        this.jTextFieldRue.setText("");
        this.jTextFieldCdp.setText("");
        this.jTextFieldVille.setText("");
    }
    
    public void afficherPremiereAdresse() {
        try {
            Jdbc.getInstance().connecter();
            adresseCourante = DaoAdresse.selectFirst();
            afficherAdresse(adresseCourante);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Pilote absent");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Echec de connexion");
        }
        modeAffichage();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jTextFieldId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldRue = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldCdp = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldVille = new javax.swing.JTextField();
        jButtonQuitter = new javax.swing.JButton();
        jPanelActions = new javax.swing.JPanel();
        jButtonValider = new javax.swing.JButton();
        jButtonAnnuler = new javax.swing.JButton();
        jButtonAjouter = new javax.swing.JButton();
        jButtonSupprimer = new javax.swing.JButton();
        jButtonRechercher = new javax.swing.JButton();
        jButtonModifier = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestion des adresses Mission 1");
        setResizable(false);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Id : ");
        jLabel2.setToolTipText("");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Rue : ");
        jLabel3.setToolTipText("");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("CP : ");
        jLabel4.setToolTipText("");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Ville : ");
        jLabel5.setToolTipText("");

        jButtonQuitter.setText("Quitter");

        jPanelActions.setBorder(javax.swing.BorderFactory.createTitledBorder("Actions"));

        jButtonValider.setText("Valider");

        jButtonAnnuler.setText("Annuler");

        jButtonAjouter.setText("Ajouter");

        jButtonSupprimer.setText("Supprimer");

        jButtonRechercher.setText("Rechercher");

        jButtonModifier.setText("Modifier");

        javax.swing.GroupLayout jPanelActionsLayout = new javax.swing.GroupLayout(jPanelActions);
        jPanelActions.setLayout(jPanelActionsLayout);
        jPanelActionsLayout.setHorizontalGroup(
            jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelActionsLayout.createSequentialGroup()
                        .addComponent(jButtonAjouter)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSupprimer))
                    .addComponent(jButtonAnnuler))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonModifier)
                    .addComponent(jButtonValider, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jButtonRechercher)
                .addContainerGap())
        );
        jPanelActionsLayout.setVerticalGroup(
            jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActionsLayout.createSequentialGroup()
                .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAjouter)
                    .addComponent(jButtonSupprimer)
                    .addComponent(jButtonModifier)
                    .addComponent(jButtonRechercher))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAnnuler)
                    .addComponent(jButtonValider))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Adresse");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3))
                            .addComponent(jLabel5)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextFieldVille, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                    .addComponent(jTextFieldCdp, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldRue, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 41, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanelActions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonQuitter))
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(214, 214, 214))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldRue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCdp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldVille, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jPanelActions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonQuitter)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    /**
     * recherche dans la BDD l'adresse dont l'id a été saisi. Si elle est
     * trouvée, cette adresse devient l'adresse courante et on passe en mode
     * affichage
     */
    private void recherche() {
        Adresse cetteAdresse = null;
        if (!jTextFieldId.getText().equals("")) {
            try {
                int idAdresse = Integer.valueOf(jTextFieldId.getText());
                try {
                    Jdbc.getInstance().connecter();
                    cetteAdresse = DaoAdresse.selectOne(idAdresse);
                    if (cetteAdresse != null) {
                        adresseCourante = cetteAdresse;
                        afficherAdresse(adresseCourante);
                        modeAffichage();
                    } else {
                        JOptionPane.showMessageDialog(this, "Identifiant inconnu");
                        jTextFieldId.selectAll();
                    }
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, "Pilote BDD absent");
                    System.exit(0);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Problème avec la Base de données");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "il faut saisir un entier");
                jTextFieldId.setText("");
                jTextFieldId.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Le champ Id est vide");
            jTextFieldId.requestFocus();
        }
    }

    /**
     * Mofifie dans la BDD l'adresse courante.
     */
    private void modification() {
        Adresse cetteAdresse = null;
        int idAdresse = Integer.valueOf(jTextFieldId.getText());
        String rueAdresse = this.jTextFieldRue.getText();
        String cdpAdresse = this.jTextFieldCdp.getText();
        String villeAdresse = this.jTextFieldVille.getText();
        cetteAdresse = new Adresse(idAdresse, rueAdresse, cdpAdresse, villeAdresse);
        try {
            Jdbc.getInstance().connecter();
            DaoAdresse.update(idAdresse, cetteAdresse);
            JOptionPane.showMessageDialog(this, "Modification effectuée");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Echec de la mise à jour de la base de données");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Pilote BDD absent");
            System.exit(0);
        }
        modeAffichage();
    }

    /**
     * Ajoute dans la BDD l'adresse saisie. La nouvelle adresse devient
     * l'adresse courante. En cas d'échec, la premierè adresse devient l'adresse
     * courante et est affichée
     */
    private void ajout() {
        boolean erreurBDD = false;
        boolean erreurSaisie = false;
        if (!jTextFieldId.getText().equals("")) {
            try {
                int idAdresse = Integer.valueOf(jTextFieldId.getText());
                Adresse cetteAdresse;
                try {
                    Jdbc.getInstance().connecter();
                    // on vérifie q'une adresse de même id n'existe pas dans la BDD
                    cetteAdresse = DaoAdresse.selectOne(idAdresse);
                    // l'adresse a été ajoutée à la BDD, elle devient l'adresse courante
                    // et on l'affiche
                    if (cetteAdresse == null) {
                        cetteAdresse = new Adresse(idAdresse, jTextFieldRue.getText(), jTextFieldCdp.getText(), jTextFieldVille.getText());
                        DaoAdresse.insert(idAdresse, cetteAdresse);
                        adresseCourante = cetteAdresse;
                        afficherAdresse(adresseCourante);
                        JOptionPane.showMessageDialog(this, "Ajout effectué");
                    } else {
                        erreurSaisie = true;
                        JOptionPane.showMessageDialog(this, "L'identifiant existe déjà");
                    }
                } catch (SQLException ex) {
                    erreurBDD = true;
                    JOptionPane.showMessageDialog(this, "Echec de l'ajout dans la base de données");
                } catch (ClassNotFoundException ex) {
                    erreurBDD = true;
                    JOptionPane.showMessageDialog(this, "Pilote BDD absent");
                    System.exit(0);
                }
            } catch (NumberFormatException ex) {
                erreurSaisie = true;
                JOptionPane.showMessageDialog(this, "il faut saisir un entier dans l'dentifiant");
            }
        } else {
            erreurSaisie = true;
            JOptionPane.showMessageDialog(this, "L'identifiant ne peut pas être vide");
        }
        // s'il y a eu une erreur avec la BDD, on réaffiche la première adresse.
        if (erreurBDD) {
            afficherPremiereAdresse();
            // s'il y a une rreur de saisie sur l'id, on place le cursuer sur la zone de saisie de l'id
        } else if (erreurSaisie) {
            jTextFieldId.requestFocus();
            jTextFieldId.selectAll();
        } else {
            modeAffichage();
        }
    }

    /**
     * Supprime l'adresse courante de la BDD. La première adresse devient
     * l'adresse courante et est affichée
     */
    private void suppression() {
        int idAdresse = Integer.valueOf(jTextFieldId.getText());
        if (!jTextFieldId.getText().equals("")) {
            Adresse cetteAdresse;
            try {
                Jdbc.getInstance().connecter();
                cetteAdresse = DaoAdresse.selectOne(idAdresse);
                if (cetteAdresse != null) {
                    int rep = JOptionPane.showConfirmDialog(this, "Etes-vous sûr(e) de vouloir supprimer l'adresse d'id " + idAdresse + " ?", "Suppression", JOptionPane.YES_NO_OPTION);
                    if (rep == JOptionPane.YES_OPTION) {
                        DaoAdresse.delete(idAdresse);
                        effacerAdresse();
                        JOptionPane.showMessageDialog(this, "Suppression effectuée");
                        adresseCourante = DaoAdresse.selectFirst();
                        afficherAdresse(adresseCourante);
                    }
                } else {
                    effacerAdresse();
                    JOptionPane.showMessageDialog(this, "Identifiant inconnu");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Echec de la suppression dans la BDD");
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Pilote absent");
                System.exit(0);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Le champ Id est vide");
        }
        modeAffichage();
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
            java.util.logging.Logger.getLogger(VueAdresse_M1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VueAdresse_M1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VueAdresse_M1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VueAdresse_M1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VueAdresse_M1().setVisible(true);
            }
        });
    }

    /**
     * classe privée Ecouteur d'événements, implémentant les méthodes de
     * l'interface ActionListener, ie la méthode actionPerformed 
     */
    private class Ecouteur implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButtonRechercher) {
                modeRecherche();
                jTextFieldId.requestFocus();
                jTextFieldId.selectAll();
                effacerAdresse();
            } else if (e.getSource() == jButtonModifier) {
                modeModification();
                jTextFieldRue.requestFocus();
            } else if (e.getSource() == jButtonAjouter) {
                modeAjout();
                effacerAdresse();
                jTextFieldId.requestFocus();
            } else if (e.getSource() == jButtonSupprimer) {
                modeSuppression();
                suppression();
            } else if (e.getSource() == jButtonValider) {
                switch (etat) {
                    case 2:
                        ajout();
                        break;
                    case 4:
                        modification();
                        break;
                    case 5:
                        recherche();
                        break;
                    default:
                }
            } else if (e.getSource() == jButtonAnnuler) {
                switch (etat) {
                    case 2:     // annulation ajout
                    case 5:     // annulation recherche
                        afficherPremiereAdresse();
                        break;
                    case 4:     // annulation modification
                        afficherAdresse(adresseCourante);
                        modeAffichage();
                        break;
                    default:
                }
            } else if (e.getSource() == jButtonQuitter) {
                System.exit(0);
            } else if (e.getSource() == jTextFieldId) {
                        if (etat != 2) {
                recherche();
                        }
            }

        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAjouter;
    private javax.swing.JButton jButtonAnnuler;
    private javax.swing.JButton jButtonModifier;
    private javax.swing.JButton jButtonQuitter;
    private javax.swing.JButton jButtonRechercher;
    private javax.swing.JButton jButtonSupprimer;
    private javax.swing.JButton jButtonValider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanelActions;
    private javax.swing.JTextField jTextFieldCdp;
    private javax.swing.JTextField jTextFieldId;
    private javax.swing.JTextField jTextFieldRue;
    private javax.swing.JTextField jTextFieldVille;
    // End of variables declaration//GEN-END:variables
}
