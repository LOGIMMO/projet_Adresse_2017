package modele.metier;

import modele.metier.Ville;

/**
 * Classe représentant les adresses des clients de l'agence bancaire
 *
 * @author btssio
 * @version 1.0 :
 *
 */
public class Adresse2 {

    private int id; 
    private String rue;
    private String cp;
    private Ville ville;
    

    public Adresse2(int id, String rue, String cp, Ville ville) {
        this.id = id;
        this.rue = rue;
        this.cp = cp;
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

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        return "Adresse2{" + "id=" + id + ", rue=" + rue + ", cp=" + cp + ", ville=" + ville + '}';
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
        } else if (adresse instanceof Adresse2) {
            return this.id == ((Adresse2) adresse).id;
        } else {
            return false;
        }
    }
}