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
import modele.metier.Adresse1;
import java.util.List;
import modele.metier.Ville;

/**
 *
 * @author nicolas
 */
public class DaoAdresse1 {

    public static Adresse1 selectOne(int idAdresse) throws SQLException {
        Adresse1 uneAdresse = null;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM ADRESSE1 A";
        requete += " INNER JOIN VILLE V ON V.VILLE_ID = A.IDVILLE ";
        requete += " WHERE ID= ?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, idAdresse);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("ID");
            String rue = rs.getString("RUE");
            long idVille = rs.getLong("VILLE_ID");
            String nom = rs.getString("VILLE_NOM");
            String cp = rs.getString("VILLE_CODE_POSTAL");
            String departement = rs.getString("VILLE_DEPARTEMENT");
            long population = rs.getLong("VILLE_POPULATION_2010_ORDER");
            uneAdresse = new Adresse1(id, rue, new Ville(idVille, nom, cp, departement, population));
        }
        return uneAdresse;
    }

    public static List<Adresse1> selectAll() throws SQLException {
        List<Adresse1> lesAdresses = new ArrayList<Adresse1>();
        Adresse1 uneAdresse;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM ADRESSE1 A";
        requete += " INNER JOIN VILLE V ON V.VILLE_ID = A.IDVILLE ";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("ID");
            String rue = rs.getString("RUE");
            long idVille = rs.getLong("VILLE_ID");
            String nom = rs.getString("VILLE_NOM");
            String cp = rs.getString("VILLE_CODE_POSTAL");
            String departement = rs.getString("VILLE_DEPARTEMENT");
            long population = rs.getLong("VILLE_POPULATION_2010_ORDER");
            uneAdresse = new Adresse1(id, rue, new Ville(idVille, nom, cp, departement, population));
            lesAdresses.add(uneAdresse);
        }
        return lesAdresses;
    }

    public static List<Adresse1> selectAll(int cleTri, int ordreTri) throws SQLException {
        List<Adresse1> lesAdresses = new ArrayList<Adresse1>();
        Adresse1 uneAdresse;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM ADRESSE1 A";
        requete += " INNER JOIN VILLE V ON V.VILLE_ID = A.IDVILLE ";
        switch (cleTri) {
            case 0: // tri par ordre de création
                requete += " ORDER BY A.ROWID";
                break;                
            case 1: // Tri par Id
                requete += " ORDER BY ID";
                break;
            case 2: // Tri par ville
                requete += " ORDER BY V.VILLE_NOM";
                break;
        }
//        if (cleTri == 1 || cleTri == 2) {
            switch (ordreTri) {
                case 1: // Tri croissant
                    requete += " ASC";
                    break;
                case 2: // Tri décroissant
                    requete += " DESC";
                    break;
//            }
        }
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("ID");
            String rue = rs.getString("RUE");
            long idVille = rs.getLong("VILLE_ID");
            String nom = rs.getString("VILLE_NOM");
            String cp = rs.getString("VILLE_CODE_POSTAL");
            String departement = rs.getString("VILLE_DEPARTEMENT");
            long population = rs.getLong("VILLE_POPULATION_2010_ORDER");
            uneAdresse = new Adresse1(id, rue, new Ville(idVille, nom, cp, departement, population));
            lesAdresses.add(uneAdresse);
        }
        return lesAdresses;
    }

    public static int insert(int idAdresse, Adresse1 uneAdresse) throws SQLException {
        int nb;
        Jdbc jdbc = Jdbc.getInstance();
        String requete;
        ResultSet rs;
        PreparedStatement pstmt;
        requete = "INSERT INTO ADRESSE1 (ID, RUE, IDVILLE) VALUES (?, ?, ?)";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, idAdresse);
        pstmt.setString(2, uneAdresse.getRue());
        pstmt.setLong(3, uneAdresse.getVille().getId());
        nb = pstmt.executeUpdate();
        return nb;
    }

    public static int update(int idAdresse, Adresse1 uneAdresse) throws SQLException {
        int nb;
        Jdbc jdbc = Jdbc.getInstance();
        String requete;
        ResultSet rs;
        PreparedStatement pstmt;
        requete = "UPDATE ADRESSE1 SET RUE = ? , IDVILLE = ? WHERE ID = ?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setString(1, uneAdresse.getRue());
        pstmt.setLong(2, uneAdresse.getVille().getId());
        pstmt.setInt(3, idAdresse);
        nb = pstmt.executeUpdate();
        return nb;
    }

    public static int delete(int idAdresse) throws SQLException {
        int nb;
        Jdbc jdbc = Jdbc.getInstance();
        String requete;
        ResultSet rs;
        PreparedStatement pstmt;
        requete = "DELETE  FROM ADRESSE1 WHERE ID = ?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, idAdresse);
        nb = pstmt.executeUpdate();
        return nb;
    }
}
