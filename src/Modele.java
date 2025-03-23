package src;

import java.util.ArrayList;

public class Affaire {
    private String nomAffaire;
    private String crime;
    private Personne suspect;
    private Personne coupable;  // Peut être null si aucun coupable trouvé
    private String lieu;
    private String etat;
    private String date;

    public Affaire(String nomAffaire, String crime, Personne suspect, Personne coupable, String lieu, String etat, String date) {
        this.nomAffaire = nomAffaire;
        this.crime = crime;
        this.suspect = suspect;
        this.coupable = coupable; // Peut être null
        this.lieu = lieu;
        this.etat = etat;
        this.date = date;
    }

    public Personne getCoupable() {
        return coupable;
    }

    @Override
    public String toString() {
        return "Affaire: " + nomAffaire + " | Crime: " + crime + " | Lieu: " + lieu + " | État: " + etat;
    }




    
}
public class Personne {
    private String nom;
    private String prenom;
    private String quartier;
    private String profession;
    private String genre;
    private String antecedents; // Peut contenir une liste d'affaires ou "Vierge"
    private ArrayList<Personne> liensPotentiels;

    public Personne(String nom, String prenom, String quartier, String profession, String genre, String antecedents) {
        this.nom = nom;
        this.prenom = prenom;
        this.quartier = quartier;
        this.profession = profession;
        this.genre = genre;
        this.antecedents = antecedents;
        this.liensPotentiels = new ArrayList<>();
    }

    public String getNomComplet() {
        return prenom + " " + nom;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }


    
    public String getQuartier() {
        return quartier;
    }

    public String getProfession() {
        return profession;
    }

    public String getGenre() {
        return genre;
    }

    public String getAntecedents() {
        return antecedents;
    }

    public ArrayList<Personne> getLiensPotentiels() {
        return liensPotentiels;
    }

    public void ajouterLienPotentiel(Personne p) {
        liensPotentiels.add(p);
    }

    @Override
    public String toString() {
        return getNomComplet() + " (" + profession + ", " + quartier + ")";
    }
}