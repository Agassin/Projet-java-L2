package src.Vue;

import src.Controller.AffaireController;
import src.fonction_supp.LoginView;
import src.Model.Affaire;
import src.Model.Personne;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AffaireView extends JFrame {
    private JPanel mainPanel;
    private JPanel dashboardPanel;
    private AffaireController controller;
    private JTextField searchField;
    private JPanel cardsPanel;
    private JPanel panelAffaires;

    public AffaireView(AffaireController controller) {
        this.controller = controller;
        setTitle("Gestion des Affaires - Tableau de bord");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal avec BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(mainPanel);

        // Création du header
        createHeader();

        // Création du dashboard
        createDashboard();

        setVisible(true);
    }

    private void createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(240, 240, 240));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Logo à gauche
        JLabel logoLabel = new JLabel("GENDARMERIE NATIONALE");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(logoLabel, BorderLayout.WEST);

        // Barre de recherche au centre
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(300, 30));
        searchPanel.add(searchField);
        header.add(searchPanel, BorderLayout.CENTER);

        // Boutons à droite
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(createIconButton("Profil", Color.BLUE));
        buttonPanel.add(createIconButton("Notifications", Color.ORANGE));
        buttonPanel.add(createIconButton("Paramètres", Color.GRAY));
        header.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(header, BorderLayout.NORTH);

        // Ajout du DocumentListener pour la recherche en temps réel
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { rechercher(); }
            public void removeUpdate(DocumentEvent e) { rechercher(); }
            public void changedUpdate(DocumentEvent e) { rechercher(); }
        });
    }

    private void createDashboard() {
        dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Section des cartes statistiques
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        statsPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        statsPanel.add(createStatCard("Affaires en cours", "5,100", "20%", Color.decode("#4CAF50")));
        statsPanel.add(createStatCard("Revenus", "2,000", "60%", Color.decode("#2196F3")));
        statsPanel.add(createStatCard("Dépenses", "3,000", "60%", Color.decode("#F44336")));
        statsPanel.add(createStatCard("Autres revenus", "550", "95%", Color.decode("#FFC107")));

        dashboardPanel.add(statsPanel, BorderLayout.NORTH);

        // Section principale avec tableau et notifications
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 15, 0));

        // Panel des affaires (version modifiée avec les cartes)
        JPanel affairesPanel = new JPanel(new BorderLayout());
        affairesPanel.setBorder(BorderFactory.createTitledBorder("Liste des Affaires"));

        // Remplacement du tableau par le système de cartes
        panelAffaires = new JPanel(new GridLayout(0, 3, 10, 10));
        JScrollPane affairesScrollPane = new JScrollPane(panelAffaires);
        affairesPanel.add(affairesScrollPane, BorderLayout.CENTER);

        contentPanel.add(affairesPanel);

        // Panel des notifications (conservé de la première version)
        JPanel noticePanel = new JPanel(new BorderLayout());
        noticePanel.setBorder(BorderFactory.createTitledBorder("Tableau de notifications"));

        JPanel notices = new JPanel();
        notices.setLayout(new BoxLayout(notices, BoxLayout.Y_AXIS));

        notices.add(createNoticeItem("Simple Mplayout API Doc", "04/10/2021", "Nouvelle documentation API disponible"));
        notices.add(createNoticeItem("Nélemode", "03/10/2021", "Sets the hide mode for the component..."));
        notices.add(createNoticeItem("Tag", "03/10/2021", "Tags the component with metadata name..."));
        notices.add(createNoticeItem("Further Reading", "12:30 PM", "There are more information to digest..."));
        notices.add(createNoticeItem("Span", "10:30 AM", "Spans the current cell over a number of cells..."));
        notices.add(createNoticeItem("Skip", "9:00 AM", "Steps a number of cells in the flow..."));

        JScrollPane noticeScroll = new JScrollPane(notices);
        noticePanel.add(noticeScroll, BorderLayout.CENTER);

        contentPanel.add(noticePanel);

        dashboardPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(dashboardPanel, BorderLayout.CENTER);

        // Afficher les affaires initiales
        afficherAffaires();
    }

    private JPanel createStatCard(String title, String value, String percentage, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(color);

        JLabel percentageLabel = new JLabel(percentage);
        percentageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        percentageLabel.setForeground(Color.GRAY);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(titleLabel);
        textPanel.add(valueLabel);
        textPanel.add(percentageLabel);

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createNoticeItem(String title, String date, String content) {
        JPanel noticeItem = new JPanel(new BorderLayout());
        noticeItem.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
        noticeItem.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel dateLabel = new JLabel(date);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        dateLabel.setForeground(Color.GRAY);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(dateLabel, BorderLayout.EAST);

        JTextArea contentArea = new JTextArea(content);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setBackground(Color.WHITE);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 12));

        noticeItem.add(headerPanel, BorderLayout.NORTH);
        noticeItem.add(contentArea, BorderLayout.CENTER);

        return noticeItem;
    }

    private JButton createIconButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    public void rechercher() {
        String searchText = searchField.getText();
        afficherAffaires(searchText);
    }

    public void afficherAffaires() {
        afficherAffaires("");
    }

    public void afficherAffaires(String searchText) {
        panelAffaires.removeAll();
        boolean found = false;

        for (Affaire affaire : controller.getAffaires()) {
            if (searchText.isEmpty() || affaire.getNomAffaire().toLowerCase().contains(searchText.toLowerCase())) {
                JPanel carte = new JPanel(new BorderLayout());
                carte.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                carte.setBackground(Color.WHITE);
                carte.setPreferredSize(new Dimension(200, 100));

                JLabel nomLabel = new JLabel("Affaire: " + affaire.getNomAffaire());
                nomLabel.setFont(new Font("Arial", Font.BOLD, 12));

                JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                contentPanel.add(nomLabel);

                // Vous pouvez ajouter d'autres informations sur l'affaire ici

                carte.add(contentPanel, BorderLayout.CENTER);
                panelAffaires.add(carte);
                found = true;
            }
        }

        for (Personne personne : controller.getPersonnes()) {
            if (searchText.isEmpty() || personne.getNomComplet().toLowerCase().contains(searchText.toLowerCase())) {
                JPanel cartePersonne = new JPanel(new BorderLayout());
                cartePersonne.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                cartePersonne.setBackground(Color.WHITE);
                cartePersonne.setPreferredSize(new Dimension(200, 100));

                JLabel nomLabel = new JLabel("Personne: " + personne.getNomComplet());
                nomLabel.setFont(new Font("Arial", Font.BOLD, 12));

                JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                contentPanel.add(nomLabel);

                // Vous pouvez ajouter d'autres informations sur la personne ici

                cartePersonne.add(contentPanel, BorderLayout.CENTER);
                panelAffaires.add(cartePersonne);
                found = true;
            }
        }

        if (!found) {
            JLabel noResults = new JLabel("Aucune correspondance trouvée.", SwingConstants.CENTER);
            noResults.setFont(new Font("Arial", Font.ITALIC, 14));
            panelAffaires.add(noResults);
        }

        panelAffaires.revalidate();
        panelAffaires.repaint();
    }

    public void afficherProfil(String username) {
        // Implémentation existante ou nouvelle implémentation
    }
}