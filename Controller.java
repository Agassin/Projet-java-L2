import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Vue view;
    private List<Modele> affairesEnCours;
    private List<Modele> affairesArchivees;

    public Controller(Vue view) {
        this.view = view;
        // Initialisation des affaires
        this.affairesEnCours = new ArrayList<>();
        this.affairesArchivees = new ArrayList<>();
        
        // Ajouter des affaires pour l'exemple
        affairesEnCours.add(new Modele("image1.png", "Affaire 1", "En cours", "Description de l'affaire 1"));
        affairesEnCours.add(new Modele("image2.png", "Affaire 2", "En cours", "Description de l'affaire 2"));
        affairesArchivees.add(new Modele("image3.png", "Affaire 3", "Archivée", "Description de l'affaire 3"));
        affairesArchivees.add(new Modele("image4.png", "Affaire 4", "Archivée", "Description de l'affaire 4"));
    }

    // Méthode pour mettre à jour la vue avec les affaires en cours
    public void updateAffairesEnCours() {
        // Ici, tu peux mettre à jour la vue en fonction de l'état du modèle
    }

    // Méthode pour mettre à jour la vue avec les affaires archivées
    public void updateAffairesArchivees() {
        // Idem pour les affaires archivées
    }
}
