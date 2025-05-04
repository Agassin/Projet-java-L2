package src.model;

import com.sun.tools.javac.Main;

public class ResultatAlgo {
    private String prenom;
    private String nom;
    private String antecedents;
    private double similarite;
    private String description;

    public ResultatAlgo(String prenom, String nom, String antecedents, double similarite, String description) {
        this.prenom = prenom;
        this.nom = nom;
        this.antecedents = antecedents;
        this.similarite = similarite;
        this.description = description;
    }

    // Getters (facultatif pour afficher ou filtrer ensuite)
    public String getPrenom() { return prenom; }
    public String getNom() { return nom; }
    public String getAntecedents() { return antecedents; }
    public double getSimilarite() { return similarite; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + antecedents + ") - Similarité: " + similarite + "\nDescription: " + description;
    }

}

