
import javax.swing.*;

// MAIN : Lancer l'application
public class Luncher {
    public static void main(String[] args) {
        AffaireController controller = new AffaireController();
        controller.ajouterAffaire(new Affaire("Affaire 1", "Ouvert", "Description 1"));
        controller.ajouterAffaire(new Affaire("Affaire 2", "Fermé", "Description 2"));
        controller.ajouterAffaire(new Affaire("Affaire 3", "En cours", "Description 3"));

        SwingUtilities.invokeLater(() -> new AffaireView(controller).setVisible(true));
    }
}
