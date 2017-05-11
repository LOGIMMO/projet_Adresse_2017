package test.modele.dao;

import java.sql.*;
import java.util.List;
import modele.dao.DaoAdresse2;
import modele.dao.Jdbc;
import modele.metier.Adresse2;
import modele.metier.Ville;

/**
 *
 * @author btssio
 */
public class TestDaoAdresse2 {

    public static void main(String[] args) {

        java.sql.Connection cnx = null;

        try {
            test0_Connexion();
            System.out.println("Test0 effectué : connexion\n");
            test1_SelectUnique(2);
            System.out.println("Test1 effectué : sélection unique\n");
            test2_SelectMultiple();
            System.out.println("Test2 effectué : sélection multiple\n");
            test3_Insert(99);
            System.out.println("Test3 effectué : insertion\n");
            test4_Update(99);
            System.out.println("Test4 effectué : mise à jour\n");
            test5_Delete(99);
            System.out.println("Test5 effectué : suppression\n");
            test6_SelectMultiple(0, 1);
            System.out.println("Test6 effectué : sélection multiple triée par ordre de création croissant\n");
            test6_SelectMultiple(0, 2);
            System.out.println("Test6 effectué : sélection multiple triée par ordre de création décroissant\n");
            test6_SelectMultiple(1, 2);
            System.out.println("Test6 effectué : sélection multiple triée par id Décroissant\n");
            test6_SelectMultiple(2, 1);
            System.out.println("Test6 effectué : sélection multiple triée par ville croissante\n");
            test6_SelectMultiple(2, 2);
            System.out.println("Test6 effectué : sélection multiple triée par ville décroissante\n");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur de pilote JDBC : " + e);
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e);
        } finally {
            try {
                if (cnx != null) {
                    cnx.close();
                }
            } catch (SQLException e) {
//                System.err.println("Erreur de fermeture de la connexion JDBC : " + e);
            }
        }

    }

    /**
     * Vérifie qu'une connexion peut être ouverte sur le SGBD
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void test0_Connexion() throws ClassNotFoundException, SQLException {
        Jdbc.creer("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:", "@localhost:1521:XE", "", "btssio", "btssio");
        Jdbc.getInstance().connecter();
        System.out.println("OK");
        Connection cnx = Jdbc.getInstance().getConnexion();
        System.out.println("OK2");
    }

    /**
     * Affiche une adresse d'après son identifiant
     *
     * @throws SQLException
     */
    public static void test1_SelectUnique(int idAdresse) throws SQLException {
        System.out.println("Début test1");
        Adresse2 cetteAdresse = DaoAdresse2.selectOne(idAdresse);
        System.out.println("Adresse d'identifiant : " + idAdresse + " : " + cetteAdresse.toString());
    }

    /**
     * Affiche toutes les villes
     *
     * @param cnx : connexion ouverte sur la base de données
     * @throws SQLException
     */
    public static void test2_SelectMultiple() throws SQLException {
        List<Adresse2> desAdresses = DaoAdresse2.selectAll();
        System.out.println("Les adresses lues : " + desAdresses.toString());
    }

    /**
     * Ajoute un client
     *
     * @throws SQLException
     */
    public static void test3_Insert(int idAdresse) throws SQLException {
        Adresse2 uneAdresse = new Adresse2(idAdresse, "141 route de Clisson", "44240", new Ville(1, null, null, null, 10000));
        int nb = DaoAdresse2.insert(idAdresse, uneAdresse);
        System.out.println("Une nouvelle adresse a été insérée: " + nb);
        test1_SelectUnique(idAdresse);
    }

    /**
     * Modifie une adresse
     *
     * @throws SQLException
     */
    public static void test4_Update(int idAdresse) throws SQLException {
        Adresse2 uneAdresse = new Adresse2(idAdresse, "143 route de Clisson", "44240",new Ville(2, null, null, null, 10000));
        int nb = DaoAdresse2.update(idAdresse, uneAdresse);
        System.out.println("Une adresse a été modifiée: " + nb);
        test1_SelectUnique(idAdresse);
    }

    /**
     * Supprime un enregistrement
     *
     * @throws SQLException
     */
    public static void test5_Delete(int idAdresse) throws SQLException {
        int nb = DaoAdresse2.delete(idAdresse);
        System.out.println("Une adresse a été supprimée: " + nb);
        test2_SelectMultiple();
    }

    /**
     * Affiche toutes les villes
     *
     * @param cnx : connexion ouverte sur la base de données
     * @throws SQLException
     */
    public static void test6_SelectMultiple(int cleTri, int ordreTri) throws SQLException {
        List<Adresse2> desAdresses = DaoAdresse2.selectAll(cleTri, ordreTri);
        System.out.println("Les adresses lues : " + desAdresses.toString());
    }
}
