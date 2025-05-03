package src.model;

public class Affaire {
    private String nomAffaire;
    private String crime;
    private Personne suspect;
    private Personne coupable;  // Peut être null si aucun coupable trouvé
    private String lieu;
    private String etat;
    private String date;
    private String Description;

    public Affaire(String nomAffaire, String crime, Personne suspect, Personne coupable, String lieu, String etat, String date,String Description) {
        this.nomAffaire = nomAffaire;
        this.crime = crime;
        this.suspect = suspect;
        this.coupable = coupable; // Peut être null
        this.lieu = lieu;
        this.etat = etat;
        this.date = date;
        this.Description = Description;
    }

    public Personne getCoupable() {
        return coupable;
    }

    public Personne getSuspect() {
        return this.suspect; 
    }


    public String getEtat() {
        return etat;
    }

    @Override
    public String toString() {
        return "Affaire: " + nomAffaire + " | Crime: " + crime + " | Lieu: " + lieu + " | État: " + etat;
    }

    public String getNomAffaire() {
        return nomAffaire;
    }

    public String getLieu() {
        return this.lieu;
    }

    public String getCrime() {
        return this.crime;
    }

    public String getDate() {
        return this.date;
    }
    public String getDescription() {
        return this.Description;
    }
}