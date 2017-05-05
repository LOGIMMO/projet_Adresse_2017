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
import modele.metier.Adresse;
import java.util.List;

/**
 *
 * @author nicolas
 */
public class DaoAdresse {

    /**
     *
     * @return @throws SQLException
     */
    public static Adresse selectFirst() throws SQLException {
        Adresse uneAdresse = null;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM ADRESSE";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("ID");
            String rue = rs.getString("RUE");
            String cdp = rs.getString("CDP");
            String ville = rs.getString("VILLE");
            uneAdresse = new Adresse(id, rue, cdp, ville);
        }
        return uneAdresse;
    }

    /**
     *
     * @param idAdresse
     * @return
     * @throws SQLException
     */
    public static Adresse selectOne(int idAdresse) throws SQLException {
        Adresse uneAdresse = null;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM ADRESSE WHERE ID= ?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, idAdresse);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("ID");
            String rue = rs.getString("RUE");
            String cdp = rs.getString("CDP");
            String ville = rs.getString("VILLE");
            uneAdresse = new Adresse(id, rue, cdp, ville);
        }
        return uneAdresse;
    }

    /**
     *
     * @return @throws SQLException
     */
    public static List<Adresse> selectAll() throws SQLException {
        List<Adresse> lesAdresses = new ArrayList();
        Adresse uneAdresse;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM ADRESSE";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("ID");
            String rue = rs.getString("RUE");
            String cdp = rs.getString("CDP");
            String ville = rs.getString("VILLE");
            uneAdresse = new Adresse(id, rue, cdp, ville);
            lesAdresses.add(uneAdresse);
        }
        return lesAdresses;
    }

    /**
     *
     * @param cleTri 0= ordre initial de création, 1= ordre de l'id, 2= ordre de
     * la ville
     * @param ordreTri 1= croissant, 2= décroissant
     * @return liste des adresses
     * @throws SQLException
     */
    public static List<Adresse> selectAll(int cleTri, int ordreTri) throws SQLException {
        List<Adresse> lesAdresses = new ArrayList();
        Adresse uneAdresse;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM ADRESSE";
        switch (cleTri) {
            case 0: // Tri par ordre de création
                requete += " ORDER BY ROWID";
                break;
            case 1: // Tri par Id
                requete += " ORDER BY ID";
                break;
            case 2: // Tri par ville
                requete += " ORDER BY VILLE";
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
            String cdp = rs.getString("CDP");
            String ville = rs.getString("VILLE");
            uneAdresse = new Adresse(id, rue, cdp, ville);
            lesAdresses.add(uneAdresse);
        }
        return lesAdresses;
    }

    /**
     *
     * @param idAdresse
     * @param uneAdresse
     * @return
     * @throws SQLException
     */
    public static int insert(int idAdresse, Adresse uneAdresse) throws SQLException {
        int nb;
        Jdbc jdbc = Jdbc.getInstance();
        String requete;
        ResultSet rs;
        PreparedStatement pstmt;
        requete = "INSERT INTO ADRESSE (ID, RUE, CDP , VILLE) VALUES (?, ?, ?, ?)";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, idAdresse);
        pstmt.setString(2, uneAdresse.getRue());
        pstmt.setString(3, uneAdresse.getCp());
        pstmt.setString(4, uneAdresse.getVille());
        nb = pstmt.executeUpdate();
        return nb;
    }

    /**
     *
     * @param idAdresse
     * @param uneAdresse
     * @return
     * @throws SQLException
     */
    public static int update(int idAdresse, Adresse uneAdresse) throws SQLException {
        int nb;
        Jdbc jdbc = Jdbc.getInstance();
        String requete;
        ResultSet rs;
        PreparedStatement pstmt;
        requete = "UPDATE ADRESSE SET RUE = ? , CDP = ? , VILLE = ? WHERE ID = ?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setString(1, uneAdresse.getRue());
        pstmt.setString(2, uneAdresse.getCp());
        pstmt.setString(3, uneAdresse.getVille());
        pstmt.setInt(4, idAdresse);
        nb = pstmt.executeUpdate();
        return nb;
    }

    /**
     *
     * @param idAdresse
     * @return
     * @throws SQLException
     */
    public static int delete(int idAdresse) throws SQLException {
        int nb;
        Jdbc jdbc = Jdbc.getInstance();
        String requete;
        ResultSet rs;
        PreparedStatement pstmt;
        requete = "DELETE  FROM ADRESSE WHERE ID = ?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, idAdresse);
        nb = pstmt.executeUpdate();
        return nb;
    }
}
