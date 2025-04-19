package src.vue;

import src.controller.AffaireController;
import src.model.Affaire;
import src.model.Personne;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Objects;

public class AffaireView extends JFrame {
    private final AffaireController controller;
    private JPanel mainPanel;
    private JTextField searchField;
    private JPanel panelAffaires;

    public AffaireView(AffaireController controller) {
        this.controller = Objects.requireNonNull(controller, "Controller ne peut pas être null");
        initUI();
        loadData();
    }

    private void initUI() {
        setTitle("Gestion des Affaires - Tableau de bord");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(mainPanel);

        createHeader();
        createDashboard();
    }

    private void createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(240, 240, 240));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Logo
        JLabel logo = new JLabel("GENDARMERIE NATIONALE", SwingConstants.LEFT);
        logo.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(logo, BorderLayout.WEST);

        // Barre de recherche
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(25);
        searchField.setPreferredSize(new Dimension(300, 30));
        searchPanel.add(searchField);
        header.add(searchPanel, BorderLayout.CENTER);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.add(createIconButton("Profil", new Color(0, 120, 215)));
        buttonPanel.add(createIconButton("Notifications", new Color(100, 100, 100)));
        buttonPanel.add(createIconButton("Paramètres", new Color(200, 0, 0)));
        header.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(header, BorderLayout.NORTH);

        // Ecouteur recherche
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { search(); }
            @Override public void removeUpdate(DocumentEvent e) { search(); }
            @Override public void changedUpdate(DocumentEvent e) { search(); }
        });
    }

    private void createDashboard() {
        JPanel dashboard = new JPanel(new BorderLayout());

        // Cartes stats
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        statsPanel.add(createStatCard("Affaires en cours", "5", "62.5%", new Color(76, 175, 80)));
        statsPanel.add(createStatCard("Affaires classées", "2", "25%", new Color(33, 150, 243)));
        statsPanel.add(createStatCard("Affaires urgentes", "1", "12.5%", new Color(244, 67, 54)));
        statsPanel.add(createStatCard("Amendes à verser", "15 000", "€", new Color(255, 193, 7)));
        dashboard.add(statsPanel, BorderLayout.NORTH);

        // Contenu principal
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 15, 0));

        // Panel affaires
        JPanel affairesPanel = new JPanel(new BorderLayout());
        affairesPanel.setBorder(BorderFactory.createTitledBorder("Liste des affaires et personnes"));
        panelAffaires = new JPanel(new GridLayout(0, 3, 10, 10));
        affairesPanel.add(new JScrollPane(panelAffaires), BorderLayout.CENTER);
        contentPanel.add(affairesPanel);

        // Panel notifications
        JPanel notifPanel = new JPanel(new BorderLayout());
        notifPanel.setBorder(BorderFactory.createTitledBorder("Notifications"));
        JPanel notices = new JPanel();
        notices.setLayout(new BoxLayout(notices, BoxLayout.Y_AXIS));

        // Exemples de notifications
        String[] notifs = {
                "Ajout d'un délit mineur|9:00 AM|Un boulanger signale le vol répété de baguettes",
                "Affaire urgente|10:30 AM|Un quartier entier devient silencieux pendant 60 minutes",
                "Pot de départ|12:30 PM|Pot de départ pour le sergent Grey",
                "Affaire urgente|30/03/2025|Bouquets de roses noires sur scènes de crime",
                "Cambrioleur Fantôme|23/03/2025|Cambriolages sans traces via tunnel souterrain",
                "ARRESTATION|04/03/2025|Agent Nolan a arrêté le coupable de l'affaire #75"
        };

        for (String notif : notifs) {
            String[] parts = notif.split("\\|");
            notices.add(createNotification(parts[0], parts[1], parts[2]));
        }

        notifPanel.add(new JScrollPane(notices), BorderLayout.CENTER);
        contentPanel.add(notifPanel);

        dashboard.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(dashboard, BorderLayout.CENTER);
    }

    private void loadData() {
        displayAffaires();
        displayPersonnes();
    }

    private void displayAffaires() {
        controller.getAffaires().forEach(affaire -> {
            JPanel card = createCard(
                    affaire.getNomAffaire(),
                    "Crime: " + affaire.getCrime() + "\nLieu: " + affaire.getLieu(),
                    Color.LIGHT_GRAY
            );
            panelAffaires.add(card);
        });
    }

    private void displayPersonnes() {
        controller.getPersonnes().forEach(personne -> {
            JPanel card = createCard(
                    personne.getNomComplet(),
                    "Profession: " + personne.getProfession() + "\nQuartier: " + personne.getQuartier(),
                    new Color(220, 240, 255)
            );
            panelAffaires.add(card);
        });
    }

    private void search() {
        String query = searchField.getText().toLowerCase();
        panelAffaires.removeAll();

        if (query.isEmpty()) {
            loadData();
            return;
        }

        // Recherche dans les affaires
        controller.getAffaires().stream()
                .filter(a -> a.getNomAffaire().toLowerCase().contains(query))
                .forEach(a -> panelAffaires.add(createCard(a.getNomAffaire(), "Affaire", Color.LIGHT_GRAY)));

        // Recherche dans les personnes
        controller.getPersonnes().stream()
                .filter(p -> p.getNomComplet().toLowerCase().contains(query))
                .forEach(p -> panelAffaires.add(createCard(p.getNomComplet(), "Personne", new Color(220, 240, 255))));

        if (panelAffaires.getComponentCount() == 0) {
            panelAffaires.add(new JLabel("Aucun résultat trouvé", SwingConstants.CENTER));
        }

        panelAffaires.revalidate();
        panelAffaires.repaint();
    }

    // Méthodes utilitaires
    private JButton createIconButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        return btn;
    }

    private JPanel createStatCard(String title, String value, String details, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(color);

        JLabel detailsLabel = new JLabel(details, SwingConstants.CENTER);
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel content = new JPanel(new BorderLayout());
        content.add(titleLabel, BorderLayout.NORTH);
        content.add(valueLabel, BorderLayout.CENTER);
        content.add(detailsLabel, BorderLayout.SOUTH);

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JPanel createNotification(String title, String date, String content) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel dateLabel = new JLabel(date);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        dateLabel.setForeground(Color.GRAY);

        JPanel header = new JPanel(new BorderLayout());
        header.add(titleLabel, BorderLayout.WEST);
        header.add(dateLabel, BorderLayout.EAST);

        JTextArea contentArea = new JTextArea(content);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setBackground(null);

        panel.add(header, BorderLayout.NORTH);
        panel.add(contentArea, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCard(String title, String details, Color bgColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.setBackground(bgColor);
        card.setPreferredSize(new Dimension(200, 120));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JTextArea detailsArea = new JTextArea(details);
        detailsArea.setEditable(false);
        detailsArea.setBackground(bgColor);
        detailsArea.setLineWrap(true);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(detailsArea, BorderLayout.CENTER);
        return card;
    }
}