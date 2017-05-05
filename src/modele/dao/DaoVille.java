/*
 * DaoAdresse
 * @author btssio
 * @version 15/04/2014
 */
package modele.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modele.metier.Ville;
import java.util.List;

/**
 *
 * @author nicolas
 */
public class DaoVille {

    public static Ville selectOne(int idVille) throws SQLException {
        Ville uneVille = null;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM VILLE WHERE VILLE_ID= ?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, idVille);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            long id = rs.getLong("VILLE_ID");
            String nom = rs.getString("VILLE_NOM");
            String cp = rs.getString("VILLE_CODE_POSTAL");
            String departement = rs.getString("VILLE_DEPARTEMENT");
            long population = rs.getLong("VILLE_POPULATION_2010_ORDER");
            uneVille = new Ville(id, nom, cp, departement, population);
        }
        return uneVille;
    }

    public static List<Ville> selectAll() throws SQLException {
        List<Ville> lesVilles = new ArrayList<Ville>();
        Ville uneVille;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM VILLE";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            long id = rs.getLong("VILLE_ID");
            String nom = rs.getString("VILLE_NOM");
            String cp = rs.getString("VILLE_CODE_POSTAL");
            String departement = rs.getString("VILLE_DEPARTEMENT");
            long population = rs.getLong("VILLE_POPULATION_2010_ORDER");
            uneVille = new Ville(id, nom, cp, departement, population);
            lesVilles.add(uneVille);
        }
        return lesVilles;
    }

    public static List<Ville> selectAllByCp(String cp) throws SQLException {
        List<Ville> lesVilles = new ArrayList<Ville>();
        Ville uneVille;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM VILLE WHERE VILLE_CODE_POSTAL = ?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setString(1, cp);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            long id = rs.getLong("VILLE_ID");
            String nom = rs.getString("VILLE_NOM");
            String departement = rs.getString("VILLE_DEPARTEMENT");
            long population = rs.getLong("VILLE_POPULATION_2010_ORDER");
            uneVille = new Ville(id, nom, cp, departement, population);
            lesVilles.add(uneVille);
        }
        return lesVilles;
    }

    public static List<Ville> selectAll(int cleTri, int ordreTri) throws SQLException {
        List<Ville> lesVilles = new ArrayList<Ville>();
        Ville uneVille;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM VILLE";
        switch (cleTri) {
            case 1: // Tri par Id
                requete += " ORDER BY VILLE_ID";
                break;
            case 2: // Tri par ville
                requete += " ORDER BY VILLE_NOM";
                break;
            case 3: // Tri par ville
                requete += " ORDER BY VILLE_CODE_POSTAL";
                break;
        }
        if (cleTri == 1 || cleTri == 2) {
            switch (ordreTri) {
                case 1: // Tri croissant
                    requete += " ASC";
                    break;
                case 2: // Tri décroissant
                    requete += " DESC";
                    break;
            }
        }
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            long id = rs.getLong("VILLE_ID");
            String nom = rs.getString("VILLE_NOM");
            String cp = rs.getString("VILLE_CODE_POSTAL");
            String departement = rs.getString("VILLE_DEPARTEMENT");
            long population = rs.getLong("VILLE_POPULATION_2010_ORDER");
            uneVille = new Ville(id, nom, cp, departement, population);
            lesVilles.add(uneVille);
        }
        return lesVilles;
    }

    public static List<Ville> selectAllByCp(String cp, int cleTri, int ordreTri) throws SQLException {
        List<Ville> lesVilles = new ArrayList<Ville>();
        Ville uneVille;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM VILLE WHERE VILLE_CODE_POSTAL = ?";
        switch (cleTri) {
            case 1: // Tri par Id
                requete += " ORDER BY VILLE_ID";
                break;
            case 2: // Tri par ville
                requete += " ORDER BY VILLE_NOM";
                break;
            case 3: // Tri par ville
                requete += " ORDER BY VILLE_CODE_POSTAL";
                break;
        }
        if (cleTri == 1 || cleTri == 2) {
            switch (ordreTri) {
                case 1: // Tri croissant
                    requete += " ASC";
                    break;
                case 2: // Tri décroissant
                    requete += " DESC";
                    break;
            }
        }
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setString(1, cp);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            long id = rs.getLong("VILLE_ID");
            String nom = rs.getString("VILLE_NOM");
            String departement = rs.getString("VILLE_DEPARTEMENT");
            long population = rs.getLong("VILLE_POPULATION_2010_ORDER");
            uneVille = new Ville(id, nom, cp, departement, population);
            lesVilles.add(uneVille);
        }
        return lesVilles;
    }

    public static List<Ville> selectAllByCpApprox(String cp, int cleTri, int ordreTri) throws SQLException {
        List<Ville> lesVilles = new ArrayList<Ville>();
        Ville uneVille;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // ***** Début préparation requête
        // Version Like
        String requete = "SELECT * FROM VILLE WHERE VILLE_CODE_POSTAL LIKE ?";
        // Version REGEXO_LIKE
//        String requete = "SELECT * FROM VILLE WHERE REGEXP_LIKE(VILLE_CODE_POSTAL, ?)";
        // ***** Fin préparation requête
        switch (cleTri) {
            case 1: // Tri par Id
                requete += " ORDER BY VILLE_ID";
                break;
            case 2: // Tri par ville
                requete += " ORDER BY VILLE_NOM";
                break;
            case 3: // Tri par ville
                requete += " ORDER BY VILLE_CODE_POSTAL";
                break;
        }
        if (cleTri == 1 || cleTri == 2) {
            switch (ordreTri) {
                case 1: // Tri croissant
                    requete += " ASC";
                    break;
                case 2: // Tri décroissant
                    requete += " DESC";
                    break;
            }
        }
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        // ***** Début préparation paramètre requête
        // Version avec LIKE
        // pb si cp = 750, renvoie les codes postaux 07500
        if (cp.length() == 5) {
            cp = "%" + cp;
        }
        cp += "%";  // on cherche tous les cp qui commencent par le cp saisi

        // Version avec REGEX_LIKE
//        cp = cp + "[0-9]{" + (5 - cp.length()) + "," + (5 - cp.length()) + "}";
        // ***** Fin préparation paramétre requête
        
        System.out.println(cp);
        pstmt.setString(1, cp);

        rs = pstmt.executeQuery();
        while (rs.next()) {
            long id = rs.getLong("VILLE_ID");
            String nom = rs.getString("VILLE_NOM");
            String cdp = rs.getString("VILLE_CODE_POSTAL");
            String departement = rs.getString("VILLE_DEPARTEMENT");
            long population = rs.getLong("VILLE_POPULATION_2010_ORDER");
            uneVille = new Ville(id, nom, cdp, departement, population);
            lesVilles.add(uneVille);
        }
        return lesVilles;
    }
}
