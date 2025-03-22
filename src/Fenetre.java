package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fenetre extends JFrame {
    public Fenetre() {
        // Titre de la fenêtre
        setTitle("Fenêtre avec Menu Latéral et Section Principale");

        // Définir la taille de la fenêtre
        setSize(800, 600);

        // Fermer l'application quand on ferme la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout principal pour la fenêtre (BorderLayout)
        setLayout(new BorderLayout());

        // Partie HEADER (en haut)
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Alignement à gauche
        headerPanel.setBackground(Color.LIGHT_GRAY);

        // Ajout d'un logo et d'une barre de recherche
        JLabel logo = new JLabel("Logo");
        JTextField recherche = new JTextField(20);
        headerPanel.add(logo);
        headerPanel.add(recherche);

        // Ajout du profil, notifications, et paramètres
        JButton profilBtn = new JButton("Profil");
        JButton notificationsBtn = new JButton("Notifications");
        JButton parametresBtn = new JButton("Paramètres");
        headerPanel.add(profilBtn);
        headerPanel.add(notificationsBtn);
        headerPanel.add(parametresBtn);

        // Ajouter le header au haut de la fenêtre
        add(headerPanel, BorderLayout.NORTH);

        // Partie MENU LATÉRAL (à gauche)
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));  // Menu vertical
        menuPanel.setPreferredSize(new Dimension(200, 600));
        menuPanel.setBackground(Color.GRAY);

        // Liste des éléments du menu latéral
        JButton affairesEnCours = new JButton("Affaires en cours");
        JButton rechercheAvancee = new JButton("Recherche avancée");
        JButton analyseLiens = new JButton("Analyse de liens");
        JButton predictionsSuspects = new JButton("Prédictions de suspects");
        JButton historiqueCommentaires = new JButton("Historique des commentaires");
        JButton parametresMenu = new JButton("Paramètres");

        // Ajouter les boutons au menu latéral
        menuPanel.add(affairesEnCours);
        menuPanel.add(rechercheAvancee);
        menuPanel.add(analyseLiens);
        menuPanel.add(predictionsSuspects);
        menuPanel.add(historiqueCommentaires);
        menuPanel.add(parametresMenu);

        // Ajouter le menu latéral à gauche
        add(menuPanel, BorderLayout.WEST);

        // Partie SECTION PRINCIPALE (centre)
        JPanel sectionPrincipale = new JPanel();
        sectionPrincipale.setBackground(Color.WHITE);
        sectionPrincipale.setLayout(new BorderLayout());
        JLabel sectionLabel = new JLabel("Section Principale", JLabel.CENTER);
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        sectionPrincipale.add(sectionLabel, BorderLayout.CENTER);

        // Ajouter la section principale au centre de la fenêtre
        add(sectionPrincipale, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Exécuter l'application dans le thread d'interface utilisateur
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Fenetre fenetre = new Fenetre();
                fenetre.setVisible(true);
            }
        });
    }
}
