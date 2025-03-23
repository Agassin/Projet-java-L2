package src;

import src.controller.AffaireController;
import src.model.Affaire;
import src.vue.AffaireView;
import javax.swing.*;

// MAIN : Lancer l'application
public class Luncher {
    public static void main(String[] args) {
        try {
            AffaireController controller = new AffaireController(); // Créer un contrôleur
            controller.ajouterAffaire(new Affaire("Affaire 1", "Vol", null, null, "Paris", "Ouvert", "2023-01-01")); // Ajouter des affaires
            controller.ajouterAffaire(new Affaire("Affaire 2", "Fraude", null, null, "Lyon", "Fermé", "2023-02-01"));  
            controller.ajouterAffaire(new Affaire("Affaire 3", "Cambriolage", null, null, "Marseille", "En cours", "2023-03-01"));

            SwingUtilities.invokeLater(() -> {
                AffaireView view = new AffaireView(controller);
                view.setVisible(true); // Créer et afficher la vue principale
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
