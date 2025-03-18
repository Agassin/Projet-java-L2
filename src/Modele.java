class Affaire {
    private String titre;
    private String statut;
    private String description;

    public Affaire(String titre, String statut, String description) {
        this.titre = titre;
        this.statut = statut;
        this.description = description;
    }

    public String getTitre() { return titre; }
    public String getStatut() { return statut; }
    public String getDescription() { return description; }
}
