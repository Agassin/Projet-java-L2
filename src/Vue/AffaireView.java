package src.Vue;

import src.Controller.AffaireController;
import src.fonction_supp.LoginView;
import src.Model.Affaire;
import src.Model.Personne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AffaireView extends JFrame {
    private JPanel panelAffaires;
    private JPanel menuLateral;
    private AffaireController controller;
    private boolean menuVisible = true;
    private JTextField searchField;


    public AffaireView(AffaireController controller) {
        this.controller = controller;
        setTitle("Gestion des Affaires");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // HEADER
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        header.setBackground(new Color(240, 240, 240)); // Gris clair


        ImageIcon icon = new ImageIcon(getClass().getResource("/images/Logo_gend.png")); // Chargement de l'image
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Redimensionner l'image
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        JButton toggleMenuButton = new JButton(resizedIcon); // Ajouter l'icône au bouton
        toggleMenuButton.setBorderPainted(false); // Supprimer la bordure
        toggleMenuButton.setContentAreaFilled(false); // Supprimer l’arrière-plan
        toggleMenuButton.setFocusPainted(false); // Supprimer l'effet de focus
        toggleMenuButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Curseur interactif

        // Ajouter un écouteur d'événements
        toggleMenuButton.addActionListener(e -> toggleMenu());

        header.add(toggleMenuButton);
        header.add(new JLabel("GENDARMERIE NATIONALE"));

        JTextField searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        header.add(searchField);

        JButton profilButton = createStyledButton("Profil");
        profilButton.addActionListener(e -> new LoginView(AffaireView.this).setVisible(true));
        header.add(profilButton);

        header.add(createStyledButton("Notifications"));
        header.add(createStyledButton("Paramètres"));
        add(header, BorderLayout.NORTH);

        // Ajouter un écouteur pour détecter les changements dans la barre de recherche
    searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            rechercher();
        }
    
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            rechercher();
        }
    
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            rechercher();
        }
    
        private void rechercher() {
            String searchText = searchField.getText();
            afficherAffaires(searchText);
        }
    });

        // MENU LATÉRAL
        menuLateral = new JPanel();
        menuLateral.setLayout(new GridLayout(6, 1, 5, 5)); // Espacement entre boutons
        menuLateral.setBackground(new Color(230, 230, 230)); // Gris clair

        String[] menuItems = {
                "Affaires en cours", "Recherche avancée", "Analyse de liens",
                "Prédictions de suspects", "Historique des commentaires", "Autre"
        };

        for (String item : menuItems) {
            JButton button = createStyledButton(item);
            button.setPreferredSize(new Dimension(200, 40));
            menuLateral.add(button);
        }

        add(menuLateral, BorderLayout.WEST);

        // SECTION PRINCIPALE
        panelAffaires = new JPanel();
        panelAffaires.setLayout(new GridLayout(0, 3, 10, 10));
        JScrollPane scrollPane = new JScrollPane(panelAffaires);
        add(scrollPane, BorderLayout.CENTER);

        afficherAffaires();
    }

    public void toggleMenu() {
        menuVisible = !menuVisible;
        menuLateral.setVisible(menuVisible);
        revalidate();
        repaint();
    }

    public void afficherAffaires() {
        panelAffaires.removeAll();
        if (controller.getAffaires().isEmpty()) {
            panelAffaires.add(new JLabel("Aucune affaire à afficher."));
        } else {
            for (Affaire affaire : controller.getAffaires()) {
                JPanel carte = new JPanel();
                carte.setLayout(new BorderLayout());
                carte.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                carte.add(new JLabel("Image"), BorderLayout.NORTH);
                carte.add(new JLabel(affaire.toString()), BorderLayout.CENTER);
                carte.add(new JLabel(affaire.getEtat()), BorderLayout.SOUTH);
                panelAffaires.add(carte);
            }
        }
        panelAffaires.revalidate();
        panelAffaires.repaint();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setBackground(new Color(200, 200, 200));
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        return button;
    }


    



    public void afficherAffaires(String searchText) {
        panelAffaires.removeAll();
    
        // Si la recherche est vide
        if (searchText.trim().isEmpty()) {
            panelAffaires.add(new JLabel("Veuillez entrer un terme de recherche."));
        } else {
            boolean found = false;
    
            // Extraire le numéro d'affaire si l'utilisateur a écrit "Affaire X"
            String numeroRecherche = searchText.replaceAll("[^0-9]", ""); // On prend seulement les chiffres
    
            // Recherche des personnes
            for (Personne personne : controller.getPersonnes()) {
                // Vérifier que les propriétés sont des chaînes non nulles avant d'appliquer toLowerCase
                if (personne.getPrenom() != null && personne.getPrenom().toLowerCase().contains(searchText.toLowerCase()) ||
                    personne.getNom() != null && personne.getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                    personne.getQuartier() != null && personne.getQuartier().toLowerCase().contains(searchText.toLowerCase()) ||
                    personne.getProfession() != null && personne.getProfession().toLowerCase().contains(searchText.toLowerCase()) ||
                    personne.getAntecedents() != null && personne.getAntecedents().toLowerCase().contains(searchText.toLowerCase())) {
                    
                    found = true;
                    JPanel cartePersonne = new JPanel();
                    cartePersonne.setLayout(new BorderLayout());
                    cartePersonne.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                    cartePersonne.add(new JLabel("Personne trouvée : " + personne.getNomComplet()), BorderLayout.CENTER);
                    panelAffaires.add(cartePersonne);
                }
            }
    
            // Recherche des affaires par nom "Affaire X"
            for (Affaire affaire : controller.getAffaires()) {
                boolean match = false;
    
                // Si l'utilisateur a entré "Affaire X", on regarde le numéro
                if (searchText != null && searchText.toLowerCase().contains("affaire")) {
                    if (affaire.getNomAffaire() != null && ((String) affaire.getNomAffaire()).toLowerCase().contains("affaire " + numeroRecherche.toLowerCase())) {
                        match = true;
                    }
                } else {
                    // Recherche plus générale, n'importe où dans l'affaire
                    if (affaire.getNomAffaire() != null && ((String) affaire.getNomAffaire()).toLowerCase().contains(numeroRecherche.toLowerCase())) {
                        match = true;
                    }
                }
    
                if (match) {
                    found = true;
                    JPanel carteAffaire = new JPanel();
                    carteAffaire.setLayout(new BorderLayout());
                    carteAffaire.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    carteAffaire.add(new JLabel("Affaire : " + affaire.getNomAffaire()), BorderLayout.CENTER);
                    panelAffaires.add(carteAffaire);
                }
            }
    
            // Si aucune correspondance n'est trouvée
            if (!found) {
                panelAffaires.add(new JLabel("Aucune correspondance trouvée."));
            }
        }
    
        // Revalidate and repaint the panel to reflect changes
        panelAffaires.revalidate();
        panelAffaires.repaint();
    }
    
    
    
    
    


    public void afficherProfil(String username) {
        JPanel profilPanel = new JPanel();
        profilPanel.setLayout(new GridLayout(4, 1));
        profilPanel.add(new JLabel("Nom : " + username));
        profilPanel.add(new JLabel("Email : " + username + "@exemple.com"));
        profilPanel.add(new JLabel("ID utilisateur : " + username.hashCode()));
        JOptionPane.showMessageDialog(this, profilPanel, "Profil utilisateur", JOptionPane.INFORMATION_MESSAGE);
    }
}
