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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modele.dao.DaoAdresse;
import modele.dao.DaoAdresse2;
import modele.dao.Jdbc;
import modele.metier.Adresse2;
import modele.metier.Ville;

/**
 *
 * @author btssio
 */
public class VueAdresse_M5_DesCPs extends javax.swing.JFrame {

    private int etat; // 1 affichage, 2 ajout, 3 suppression, 4 modification, 
    // 5 recherche, possibilité d'utiliser enum

    private List<Adresse2> lesAdresses;  // liste des adresses
    private int indiceAdresseCourante;  // indice de l'adresse courante dans la liste
    private Adresse2 adresseCourante;    // l'adresse courante
    private int cleTri = 0;                 // clé de tri, 0 = ordre initial, 1 = id
    // 2 = ville
    private int ordreTri = 1;               // ordre de tri, 1 = croissant, 2 = décroissant

    private Ecouteur ecouteur;          // l'écouteur d'événements
    private Ville villeCourante;        // la ville courante

    /**
     * Creates new form VueAdresse
     */
    public VueAdresse_M5_DesCPs() {
        // initialisation des composants grahiques de l'interface
        initComponents();
        jTextFieldCdp.setSize(4, 19);
        jRadioButtonTriCreation.setSelected(true);
        // instanciation d'un listener
        ecouteur = new Ecouteur(this);
        // ajout du listener au différents contrôles écoutés
        jButtonRechercher.addActionListener(ecouteur);
        jButtonAjouter.addActionListener(ecouteur);
        jButtonModifier.addActionListener(ecouteur);
        jButtonSupprimer.addActionListener(ecouteur);
        jButtonValider.addActionListener(ecouteur);
        jButtonAnnuler.addActionListener(ecouteur);
        jButtonQuitter.addActionListener(ecouteur);
        jTextFieldId.addKeyListener(ecouteur);
        jButtonPrecedent.addActionListener(ecouteur);
        jButtonSuivant.addActionListener(ecouteur);
        jRadioButtonTriCreation.addActionListener(ecouteur);
        jRadioButtonTriId.addActionListener(ecouteur);
        jRadioButtonTriVille.addActionListener(ecouteur);
        jCheckBoxTriDecroissant.addActionListener(ecouteur);
        jButtonChoisirVille.addActionListener(ecouteur);

        // création du singleton Jdbc
        Jdbc.creer("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:", "@localhost:1521:XE", "", "btssio", "btssio");

        try {
            // initialisation de la liste des adresses
            Jdbc.getInstance().connecter();
            lesAdresses = DaoAdresse2.selectAll();
            afficherPremiereAdresse();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Pilote BDD absent");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Problème avec la Base de données");
        }

        // affichage de la première adresse
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
        jButtonAjouter.setEnabled(true);
        jButtonModifier.setEnabled(true);
        jButtonSupprimer.setEnabled(true);
        jButtonRechercher.setEnabled(true);
        jButtonAnnuler.setEnabled(false);
        jButtonValider.setEnabled(false);
        jButtonPrecedent.setEnabled(true);
        jButtonSuivant.setEnabled(true);
        jButtonChoisirVille.setEnabled(false);
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
        jButtonAjouter.setEnabled(false);
        jButtonModifier.setEnabled(false);
        jButtonSupprimer.setEnabled(false);
        jButtonRechercher.setEnabled(false);
        jButtonAnnuler.setEnabled(true);
        jButtonValider.setEnabled(true);
        jButtonPrecedent.setEnabled(false);
        jButtonSuivant.setEnabled(false);
        jButtonChoisirVille.setEnabled(true);
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
        jButtonAjouter.setEnabled(false);
        jButtonModifier.setEnabled(false);
        jButtonSupprimer.setEnabled(false);
        jButtonRechercher.setEnabled(false);
        jButtonAnnuler.setEnabled(false);
        jButtonValider.setEnabled(false);
        jButtonPrecedent.setEnabled(false);
        jButtonSuivant.setEnabled(false);
        jButtonChoisirVille.setEnabled(false);
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
        jButtonAjouter.setEnabled(false);
        jButtonModifier.setEnabled(false);
        jButtonSupprimer.setEnabled(false);
        jButtonRechercher.setEnabled(false);
        jButtonAnnuler.setEnabled(true);
        jButtonValider.setEnabled(true);
        jButtonPrecedent.setEnabled(false);
        jButtonSuivant.setEnabled(false);
        jButtonChoisirVille.setEnabled(true);
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
        jButtonAjouter.setEnabled(false);
        jButtonModifier.setEnabled(false);
        jButtonSupprimer.setEnabled(false);
        jButtonRechercher.setEnabled(false);
        jButtonAnnuler.setEnabled(true);
        jButtonValider.setEnabled(true);
        jButtonPrecedent.setEnabled(false);
        jButtonSuivant.setEnabled(false);
        jButtonChoisirVille.setEnabled(false);
    }

    /**
     * Affiche dans l'interface une adresse passée en paramètre.
     *
     * @param l'adresse à afficher
     */
    private void afficherAdresse(Adresse2 uneAdresse) {
        this.jTextFieldId.setText(Integer.toString(uneAdresse.getId()));
        this.jTextFieldRue.setText(uneAdresse.getRue());
        this.jTextFieldCdp.setText(uneAdresse.getCp());
        this.jTextFieldVille.setText(uneAdresse.getVille().getNom());
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupTri = new javax.swing.ButtonGroup();
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
        jButtonSuivant = new javax.swing.JButton();
        jButtonPrecedent = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jRadioButtonTriCreation = new javax.swing.JRadioButton();
        jRadioButtonTriId = new javax.swing.JRadioButton();
        jRadioButtonTriVille = new javax.swing.JRadioButton();
        jCheckBoxTriDecroissant = new javax.swing.JCheckBox();
        jButtonChoisirVille = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestion des adresses Mission 5");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Id : ");
        jLabel2.setToolTipText("");

        jTextFieldId.setFocusCycleRoot(true);
        jTextFieldId.setNextFocusableComponent(jTextFieldRue);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Rue : ");
        jLabel3.setToolTipText("");

        jTextFieldRue.setNextFocusableComponent(jButtonChoisirVille);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("CP : ");
        jLabel4.setToolTipText("");

        jTextFieldCdp.setEnabled(false);
        jTextFieldCdp.setMaximumSize(new java.awt.Dimension(4, 19));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Ville : ");
        jLabel5.setToolTipText("");

        jTextFieldVille.setEnabled(false);
        jTextFieldVille.setMaximumSize(new java.awt.Dimension(4, 19));
        jTextFieldVille.setName(""); // NOI18N
        jTextFieldVille.setNextFocusableComponent(jTextFieldCdp);

        jButtonQuitter.setText("Quitter");

        jPanelActions.setBorder(javax.swing.BorderFactory.createTitledBorder("Actions"));

        jButtonValider.setText("Valider");

        jButtonAnnuler.setText("Annuler");

        jButtonAjouter.setText("Ajouter");

        jButtonSupprimer.setText("Supprimer");

        jButtonRechercher.setText("Rechercher");

        jButtonModifier.setText("Modifier");

        jButtonSuivant.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vue/images/fleche_droite.png"))); // NOI18N

        jButtonPrecedent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vue/images/fleche_gauche.png"))); // NOI18N

        javax.swing.GroupLayout jPanelActionsLayout = new javax.swing.GroupLayout(jPanelActions);
        jPanelActions.setLayout(jPanelActionsLayout);
        jPanelActionsLayout.setHorizontalGroup(
            jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonPrecedent, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanelActionsLayout.createSequentialGroup()
                        .addComponent(jButtonAjouter)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSupprimer))
                    .addComponent(jButtonAnnuler))
                .addGap(18, 18, 18)
                .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelActionsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonValider, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addGroup(jPanelActionsLayout.createSequentialGroup()
                        .addComponent(jButtonModifier)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonRechercher)))
                .addComponent(jButtonSuivant, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanelActionsLayout.setVerticalGroup(
            jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActionsLayout.createSequentialGroup()
                .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSuivant, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanelActionsLayout.createSequentialGroup()
                        .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonPrecedent, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButtonAjouter)
                                .addComponent(jButtonSupprimer)
                                .addComponent(jButtonModifier)
                                .addComponent(jButtonRechercher)))
                        .addGap(0, 1, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAnnuler)
                    .addComponent(jButtonValider))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Adresse");

        buttonGroupTri.add(jRadioButtonTriCreation);
        jRadioButtonTriCreation.setText("Tri ordre création");

        buttonGroupTri.add(jRadioButtonTriId);
        jRadioButtonTriId.setText("Tri  par Id");

        buttonGroupTri.add(jRadioButtonTriVille);
        jRadioButtonTriVille.setText("Tri par Ville");

        jCheckBoxTriDecroissant.setText("Tri décroissant");

        jButtonChoisirVille.setText("Modifier ville");
        jButtonChoisirVille.setEnabled(false);
        jButtonChoisirVille.setNextFocusableComponent(jButtonValider);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(530, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel3))
                                    .addComponent(jLabel5)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldRue, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextFieldVille, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jButtonChoisirVille))
                            .addComponent(jTextFieldCdp, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonTriCreation)
                            .addComponent(jRadioButtonTriId)
                            .addComponent(jRadioButtonTriVille)
                            .addComponent(jCheckBoxTriDecroissant)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 71, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanelActions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonQuitter))))
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldRue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldVille, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonChoisirVille))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCdp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addComponent(jPanelActions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonQuitter))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jRadioButtonTriCreation)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonTriId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonTriVille)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxTriDecroissant)))
                .addContainerGap(25, Short.MAX_VALUE))
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
        Adresse2 cetteAdresse = null;
        if (!jTextFieldId.getText().equals("")) {
            try {
                int idAdresse = Integer.valueOf(jTextFieldId.getText());
                int indice = lesAdresses.indexOf(new Adresse2(idAdresse, null, null, null));
                if (indice != -1) {
                    indiceAdresseCourante = indice;
                    adresseCourante = lesAdresses.get(indiceAdresseCourante);
                    villeCourante = adresseCourante.getVille();
                    afficherAdresse(adresseCourante);
                    modeAffichage();
                } else {
                    JOptionPane.showMessageDialog(this, "Identifiant inconnu");
                    jTextFieldId.selectAll();
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
        Adresse2 cetteAdresse;
        int idAdresse = Integer.valueOf(jTextFieldId.getText());
        String rueAdresse = this.jTextFieldRue.getText();
        String cdpAdresse = this.jTextFieldCdp.getText();
//        String villeAdresse = this.jTextFieldVille.getText();
        
        cetteAdresse = new Adresse2(idAdresse, rueAdresse, cdpAdresse, villeCourante);
        try {
            Jdbc.getInstance().connecter();
            DaoAdresse2.update(idAdresse, cetteAdresse);
            lesAdresses = DaoAdresse2.selectAll();
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
                Adresse2 cetteAdresse;
                try {
                    Jdbc.getInstance().connecter();
                    boolean present = lesAdresses.contains(new Adresse2(idAdresse, null, null, null));
                    if (!present) {
                        cetteAdresse = new Adresse2(idAdresse, jTextFieldRue.getText(), jTextFieldCdp.getText(), villeCourante);
                        DaoAdresse2.insert(idAdresse, cetteAdresse);
                        adresseCourante = cetteAdresse;
                        afficherAdresse(adresseCourante);
                        lesAdresses = DaoAdresse2.selectAll();
                        indiceAdresseCourante = lesAdresses.indexOf(adresseCourante);
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
            Adresse2 cetteAdresse;

            int indice = lesAdresses.indexOf(new Adresse2(idAdresse, null, null, null));
            cetteAdresse = lesAdresses.get(indice);
            if (cetteAdresse != null) {
                int rep = JOptionPane.showConfirmDialog(this, "Etes-vous sûr(e) ?", "Suppression", JOptionPane.YES_NO_OPTION);
                if (rep == JOptionPane.YES_OPTION) {
                    try {
                        Jdbc.getInstance().connecter();
                        DaoAdresse2.delete(idAdresse);
                        effacerAdresse();
                        JOptionPane.showMessageDialog(this, "Suppression effectuée");
                        lesAdresses = DaoAdresse2.selectAll();
                        afficherPremiereAdresse();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Echec de la suppression dans la BDD");
                    } catch (ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(this, "Pilote absent");
                        System.exit(0);
                    }
                }
            } else {
                effacerAdresse();
                JOptionPane.showMessageDialog(this, "Identifiant inconnu");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Le champ Id est vide");
        }
        modeAffichage();
    }

    public void afficherPremiereAdresse() {
        indiceAdresseCourante = 0;
        adresseCourante = lesAdresses.get(indiceAdresseCourante);
        villeCourante = adresseCourante.getVille();
        afficherAdresse(adresseCourante);
        modeAffichage();
    }

    private void selectionAffichage() {
        try {
            lesAdresses = DaoAdresse2.selectAll(cleTri, ordreTri);
            indiceAdresseCourante = 0;
            adresseCourante = lesAdresses.get(indiceAdresseCourante);
            afficherAdresse(adresseCourante);
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
            java.util.logging.Logger.getLogger(VueAdresse_M5_DesCPs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VueAdresse_M5_DesCPs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VueAdresse_M5_DesCPs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VueAdresse_M5_DesCPs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new VueAdresse_M5_DesCPs().setVisible(true);
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

        private VueAdresse_M5_DesCPs vue;

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
            } else if (e.getSource() == jButtonPrecedent) {
                if (indiceAdresseCourante > 0) {
                    indiceAdresseCourante--;
                    adresseCourante = lesAdresses.get(indiceAdresseCourante);;
                    afficherAdresse(adresseCourante);
                }
            } else if (e.getSource() == jButtonSuivant) {
                if (indiceAdresseCourante < lesAdresses.size() - 1) {
                    indiceAdresseCourante++;
                    adresseCourante = lesAdresses.get(indiceAdresseCourante);;
                    afficherAdresse(adresseCourante);
                }
            } else if (e.getSource() == jRadioButtonTriCreation) {
                cleTri = 0;
                selectionAffichage();
            } else if (e.getSource() == jRadioButtonTriId) {
                cleTri = 1;
                selectionAffichage();
            } else if (e.getSource() == jRadioButtonTriVille) {
                cleTri = 2;
                selectionAffichage();
            } else if (e.getSource() == jCheckBoxTriDecroissant) {
                boolean check = jCheckBoxTriDecroissant.isSelected();
                if (check) {
                    ordreTri = 2;
                } else {
                    ordreTri = 1;
                }
                selectionAffichage();
            } else if (e.getSource() == jButtonChoisirVille) {
                VueChoixVilleApproxDesCPs vueChoixVille_Approx = new VueChoixVilleApproxDesCPs(this.getVue(), true);
                vueChoixVille_Approx.setVisible(true);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == Event.ENTER && etat != 2) {
                recherche();
            } else if (e.getKeyCode() == Event.ESCAPE) {
                jButtonAnnuler.doClick();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        public Ecouteur(VueAdresse_M5_DesCPs vue) {
            this.vue = vue;
        }

        public VueAdresse_M5_DesCPs getVue() {
            return vue;
        }

        public void setVue(VueAdresse_M5_DesCPs vue) {
            this.vue = vue;
        }

    }

    public Ville getVilleCourante() {
        return villeCourante;
    }

    public void setVilleCourante(Ville villeCourante) {
        this.villeCourante = villeCourante;
    }

    public JTextField getjTextFieldCdp() {
        return jTextFieldCdp;
    }

    public void setjTextFieldCdp(JTextField jTextFieldCdp) {
        this.jTextFieldCdp = jTextFieldCdp;
    }

    public JTextField getjTextFieldVille() {
        return jTextFieldVille;
    }

    public void setjTextFieldVille(JTextField jTextFieldVille) {
        this.jTextFieldVille = jTextFieldVille;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupTri;
    private javax.swing.JButton jButtonAjouter;
    private javax.swing.JButton jButtonAnnuler;
    private javax.swing.JButton jButtonChoisirVille;
    private javax.swing.JButton jButtonModifier;
    private javax.swing.JButton jButtonPrecedent;
    private javax.swing.JButton jButtonQuitter;
    private javax.swing.JButton jButtonRechercher;
    private javax.swing.JButton jButtonSuivant;
    private javax.swing.JButton jButtonSupprimer;
    private javax.swing.JButton jButtonValider;
    private javax.swing.JCheckBox jCheckBoxTriDecroissant;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanelActions;
    private javax.swing.JRadioButton jRadioButtonTriCreation;
    private javax.swing.JRadioButton jRadioButtonTriId;
    private javax.swing.JRadioButton jRadioButtonTriVille;
    private javax.swing.JTextField jTextFieldCdp;
    private javax.swing.JTextField jTextFieldId;
    private javax.swing.JTextField jTextFieldRue;
    private javax.swing.JTextField jTextFieldVille;
    // End of variables declaration//GEN-END:variables
}
