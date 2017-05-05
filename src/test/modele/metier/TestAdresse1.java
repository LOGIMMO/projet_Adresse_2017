package test.modele.metier;

import modele.metier.Adresse1;
import modele.metier.Ville;

/**
 * Test unitaire de la classe Adresse1
 * @author btssio
 */
public class TestAdresse1 {

    public static void main(String[] args) {
        Adresse1 adr, adr1, adr2;
        System.out.println("\nTest n°1 : instanciation et accesseurs");
        adr = new Adresse1(1, "141 route de Clisson", new Ville(1, "NANTES", "44000", "44", 300000));
        System.out.println(adr);
        System.out.println("\nTest n°2 : mutateurs");
        adr.setRue("56 boulevard de la Prairie aux Ducs");
        adr.setVille(new Ville(1, "SAINT SEBASTIEN SUR LOIRE", "44240", "44", 30000));
        System.out.println(adr);
        adr1 = new Adresse1(1,null, null);
        System.out.println(adr1.equals(adr));
        adr2 = new Adresse1(2,null, null);
        System.out.println(adr1.equals(adr2));
    }
}
