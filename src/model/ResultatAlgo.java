package src.model;

public class ResultatAlgo {
    private String prenom;
    private String nom;
    private String antecedents;
    private double similarite;
    private String descriptionCrime; // Champ pour stocker la description

    public ResultatAlgo(String prenom, String nom, String antecedents, double similarite, String descriptionCrime) {
        this.prenom = prenom;
        this.nom = nom;
        this.antecedents = antecedents;
        this.similarite = similarite;
        this.descriptionCrime = descriptionCrime;
    }

    // Getters
    public String getPrenom() { return prenom; }
    public String getNom() { return nom; }
    public String getAntecedents() { return antecedents; }
    public double getSimilarite() { return similarite; }
    public String getDescriptionCrime() { return descriptionCrime; } // Getter pour la description

    @Override
    public String toString() {
        return "ResultatAlgo{" +
                "prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", antecedents='" + antecedents + '\'' +
                ", similarite=" + similarite +
                ", descriptionCrime='" + descriptionCrime + '\'' +
                '}';
    }
}