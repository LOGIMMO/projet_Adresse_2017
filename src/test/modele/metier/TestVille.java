package test.modele.metier;

import modele.metier.Ville;

/**
 * Test unitaire de la classe Adresse
 * @author btssio
 */
public class TestVille {

    public static void main(String[] args) {
        Ville ville;
        System.out.println("\nTest n°1 : instanciation et accesseurs");
        ville = new Ville(1, "NANTES", "44000", "44", 300000);
        System.out.println(ville);
        System.out.println("\nTest n°2 : mutateurs");
        ville.setNom("Nantes");
        ville.setCp("44100");
        ville.setDepartement("45");
        ville.setPopulation(400000);
        System.out.println(ville);
    }
}
