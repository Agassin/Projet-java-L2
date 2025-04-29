package src.model;
import java.util.ArrayList;

public class Personne {
    private String nom;
    private String prenom;
    private String age;
    private String quartier;
    private String profession;
    private String genre;
    private String antecedents; // Peut contenir une liste d'affaires ou "Vierge"
    private String description;

    public Personne(String nom, String prenom,String age, String quartier, String profession, String genre, String antecedents,String description) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.quartier = quartier;
        this.profession = profession;
        this.genre = genre;
        this.antecedents = antecedents;
        this.description = description;
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

    public String getAge() {return age;}
    
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

    public String getDescription() {return description;}

    @Override
    public String toString() {
        return getNomComplet() + " (" + profession + ", " + quartier + ")";
    }


}