public class Personne {
    private String nom;
    private String prenom;
    private String quartier;
    private String profession;
    private String genre;
    private String antecedents; // Peut contenir une liste d'affaires ou "Vierge"
    private List<Personne> liensPotentiels;

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

    public void ajouterLienPotentiel(Personne p) {
        liensPotentiels.add(p);
    }

    @Override
    public String toString() {
        return getNomComplet() + " (" + profession + ", " + quartier + ")";
    }
}
