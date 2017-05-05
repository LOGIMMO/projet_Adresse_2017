package modele.metier;

import modele.metier.Ville;

/**
 * Classe représentant les adresses des clients de l'agence bancaire
 *
 * @author btssio
 * @version 1.0 :
 *
 */
public class Adresse1 {

    private int id; 
    private String rue;
    private Ville ville;
    
    public Adresse1(int id, String rue, Ville ville) {
        this.id = id;
        this.rue = rue;
        this.ville = ville;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        return "Adresse{" + "id=" + id + ", rue=" + rue + ", ville=" + ville.toString()+ '}';
    }

   
    
    /**
     * Redéfinition de la méthode equals
     * 
     * @param adresse
     * @return true si 2 adresses ont le même identifiant BDD
     */
    @Override
    public boolean equals(Object adresse) {
        if (adresse == null) {
            return false;
        } else if (adresse instanceof Adresse1) {
            return this.id == ((Adresse1) adresse).id;
        } else {
            return false;
        }
    }
}