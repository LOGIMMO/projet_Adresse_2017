package test.modele.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modele.dao.DaoVille;
import modele.dao.Jdbc;
import modele.metier.Ville;

/**
 * Test unitaire de la classe Adresse
 *
 * @author btssio
 */
public class TestDaoVille {

    public static void main(String[] args) {
        Ville ville, ville1;
        List<Ville> lesVilles;

        Jdbc.creer("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:", "@localhost:1521:XE", "", "btssio", "btssio");
        try {
            Jdbc.getInstance().connecter();
            int i = 0;
            try {
                System.out.println("\nTest n°" + ++i + " : lecture une ville existante");
                ville = DaoVille.selectOne(2);
                System.out.println(ville);
                System.out.println("\nTest n°" + ++i + " : lecture une ville inexistante");
                ville = DaoVille.selectOne(0);
                System.out.println(ville);
                System.out.println("\nTest n°" + ++i + " : lecture de villes");
                lesVilles = DaoVille.selectAll();
                int n = 10;
                for (int j = 0; j < n; j++) {
                    System.out.println(lesVilles.get(j));
                }
                System.out.println("\nTest n°" + ++i + " : lecture de villes triéées par nom");
                lesVilles = DaoVille.selectAll(2, 1);
                for (int j = 0; j < n; j++) {
                    System.out.println(lesVilles.get(j));
                }
                System.out.println("\nTest n°" + ++i + " : lecture de villes triéées par nom décroissant");
                lesVilles = DaoVille.selectAll(2, 2);
                for (int j = 0; j < n; j++) {
                    System.out.println(lesVilles.get(j));
                }
                System.out.println("\nTest n°" + ++i + " : lecture de villes triéées par id décroissant");
                lesVilles = DaoVille.selectAll(1, 2);
                for (int j = 0; j < n; j++) {
                    System.out.println(lesVilles.get(j));
                }
                String cp = null;
//                cp = "7";
//                System.out.println("\nTest n°" + ++i + " : sélection de villes par code postal " + cp);
//                lesVilles = DaoVille.selectAllByCpApprox(cp, 1, 1);
//                for (int j = 0; j < lesVilles.size(); j++) {
//                    System.out.println(lesVilles.get(j).getId() + " - " + lesVilles.get(j).getNom() + " - " + lesVilles.get(j).getCp());
//                }
//                cp = "75";
//                System.out.println("\nTest n°" + ++i + " : sélection de villes par code postal " + cp);
//                lesVilles = DaoVille.selectAllByCpApprox(cp, 1, 1);
//                for (int j = 0; j < lesVilles.size(); j++) {
//                    System.out.println(lesVilles.get(j).getId() + " - " + lesVilles.get(j).getNom() + " - " + lesVilles.get(j).getCp());
//                }
//                cp = "750";
//                System.out.println("\nTest n°" + ++i + " : sélection de villes par code postal " + cp);
//                lesVilles = DaoVille.selectAllByCpApprox(cp, 1, 1);
//                for (int j = 0; j < lesVilles.size(); j++) {
//                    System.out.println(lesVilles.get(j).getId() + " - " + lesVilles.get(j).getNom() + " - " + lesVilles.get(j).getCp());
//                }
//                cp = "7500";
//                System.out.println("\nTest n°" + ++i + " : sélection de villes par code postal " + cp);
//                lesVilles = DaoVille.selectAllByCpApprox(cp, 1, 1);
//                for (int j = 0; j < lesVilles.size(); j++) {
//                    System.out.println(lesVilles.get(j).getId() + " - " + lesVilles.get(j).getNom() + " - " + lesVilles.get(j).getCp());
//                }
                cp = "75011";
                System.out.println("\nTest n°" + ++i + " : sélection de villes par code postal " + cp);
                lesVilles = DaoVille.selectAllByCpApprox(cp, 1, 1);
                for (int j = 0; j < lesVilles.size(); j++) {
                    System.out.println(lesVilles.get(j).getId() + " - " + lesVilles.get(j).getNom() + " - " + lesVilles.get(j).getCp());
                }
            } catch (SQLException ex) {
                System.out.println("pb SQL");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Pilote absent");
        } catch (SQLException ex) {
            System.out.println("Echec connexion");
        }
    }
}
