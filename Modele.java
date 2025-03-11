public class Modele {
    private String image;
    private String titre;
    private String statut;
    private String description;

    public Modele(String image, String titre, String statut, String description) {
        this.image = image;
        this.titre = titre;
        this.statut = statut;
        this.description = description;
    }
    
    // Getters and Setters
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
