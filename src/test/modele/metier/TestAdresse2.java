package test.modele.metier;

import modele.metier.Adresse2;
import modele.metier.Ville;

/**
 * Test unitaire de la classe Adresse2
 * @author btssio
 */
public class TestAdresse2 {

    public static void main(String[] args) {
        Adresse2 adr, adr1, adr2;
        System.out.println("\nTest n°1 : instanciation et accesseurs");
        adr = new Adresse2(1, "141 route de Clisson", "44230", new Ville(1, "NANTES", "44000", "44", 300000));
        System.out.println(adr);
        System.out.println("\nTest n°2 : mutateurs");
        adr.setRue("56 boulevard de la Prairie aux Ducs");
        adr.setCp("44265");
        adr.setVille(new Ville(1, "SAINT SEBASTIEN SUR LOIRE", "44240", "44", 30000));
        System.out.println(adr);
        adr1 = new Adresse2(1,null, null, null);
        System.out.println(adr1.equals(adr));
        adr2 = new Adresse2(2,null, null, null);
        System.out.println(adr1.equals(adr2));
    }
}
