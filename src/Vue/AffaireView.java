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
    private JButton btnAffaires, btnPersonnes;
    private boolean showAffaires = true;
    private boolean showPersonnes = true;

    public AffaireView(AffaireController controller) {
        this.controller = Objects.requireNonNull(controller, "Controller ne peut pas être null");
        initUI();
        refreshDisplay();
    }

    private void initUI() {
        setTitle("Gestion des Affaires - Tableau de bord");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
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


    private void toggleFilter(JButton button, Runnable toggleAction) {
        toggleAction.run();
        button.setBackground(button.getBackground() == new Color(0, 120, 215)
                ? new Color(200, 200, 200)
                : new Color(0, 120, 215));
        button.setForeground(button.getForeground() == Color.WHITE ? Color.BLACK : Color.WHITE);
        refreshDisplay();
    }

    private JButton createFilterButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(100, 30));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return btn;
    }

    private void createDashboard() {
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBorder(new EmptyBorder(10, 20, 20, 20));

        // Cartes stats (inchangé)
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        statsPanel.add(createStatCard("Affaires en cours", "5", "62%", new Color(76, 175, 80)));
        statsPanel.add(createStatCard("Affaires classées", "2", "25%", new Color(33, 150, 243)));
        statsPanel.add(createStatCard("Affaires urgentes", "1", "12%", new Color(244, 67, 54)));
        statsPanel.add(createStatCard("Amendes à verser", "15 000", "€", new Color(255, 193, 7)));
        dashboard.add(statsPanel, BorderLayout.NORTH);

        // Contenu principal
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Panel affaires
        JPanel affairesPanel = new JPanel(new BorderLayout());
        affairesPanel.setBorder(BorderFactory.createTitledBorder("Liste des affaires et personnes"));

        // Ajout des boutons de filtre ici
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        btnAffaires = new JButton("Affaires");
        btnPersonnes = new JButton("Personnes");

        // Style initial
        btnAffaires.setBackground(new Color(0, 120, 215));
        btnAffaires.setForeground(Color.WHITE);
        btnPersonnes.setBackground(new Color(0, 120, 215));
        btnPersonnes.setForeground(Color.WHITE);

        // Personnalisation des boutons
        Dimension btnSize = new Dimension(120, 30);
        btnAffaires.setPreferredSize(btnSize);
        btnPersonnes.setPreferredSize(btnSize);

        btnAffaires.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        btnPersonnes.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Gestion des clics
        btnAffaires.addActionListener(e -> {
            showAffaires = !showAffaires;
            btnAffaires.setBackground(showAffaires ? new Color(0, 120, 215) : new Color(200, 200, 200));
            btnAffaires.setForeground(showAffaires ? Color.WHITE : Color.BLACK);
            refreshDisplay();
        });

        btnPersonnes.addActionListener(e -> {
            showPersonnes = !showPersonnes;
            btnPersonnes.setBackground(showPersonnes ? new Color(0, 120, 215) : new Color(200, 200, 200));
            btnPersonnes.setForeground(showPersonnes ? Color.WHITE : Color.BLACK);
            refreshDisplay();
        });

        filterPanel.add(btnAffaires);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(btnPersonnes);

        affairesPanel.add(filterPanel, BorderLayout.NORTH);

        // Panel de contenu principal (inchangé)
        panelAffaires = new JPanel();
        panelAffaires.setLayout(new BoxLayout(panelAffaires, BoxLayout.Y_AXIS));
        panelAffaires.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelAffaires);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        affairesPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel notifications (inchangé)
        JPanel notifPanel = new JPanel(new BorderLayout());
        notifPanel.setBorder(BorderFactory.createTitledBorder("Notifications"));
        JPanel notices = new JPanel();
        notices.setLayout(new BoxLayout(notices, BoxLayout.Y_AXIS));

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

        // Split pane (inchangé)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, affairesPanel, notifPanel);
        splitPane.setResizeWeight(0.7);
        splitPane.setDividerLocation(0.7);

        contentPanel.add(splitPane, BorderLayout.CENTER);
        dashboard.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(dashboard, BorderLayout.CENTER);
    }

    private void refreshDisplay() {
        panelAffaires.removeAll();

        if (showAffaires) displayAffaires();
        if (showPersonnes) displayPersonnes();

        panelAffaires.add(Box.createVerticalGlue());
        panelAffaires.revalidate();
        panelAffaires.repaint();
    }

    private void displayAffaires() {
        controller.getAffaires().forEach(affaire -> {
            String details = String.format(
                    "<html><b>Crime:</b> %s<br><b>Lieu:</b> %s<br><b>État:</b> %s<br><b>Date:</b> %s</html>",
                    affaire.getCrime(), affaire.getLieu(), affaire.getEtat(), affaire.getDate());

            JPanel card = createCard(affaire.getNomAffaire(), details, Color.LIGHT_GRAY);
            card.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelAffaires.add(card);
            panelAffaires.add(Box.createRigidArea(new Dimension(0, 10)));
        });
    }

    private void displayPersonnes() {
        controller.getPersonnes().forEach(personne -> {
            String details = String.format(
                    "<html><b>Profession:</b> %s<br><b>Quartier:</b> %s<br><b>Antécédents:</b> %s</html>",
                    personne.getProfession(), personne.getQuartier(), personne.getAntecedents());

            JPanel card = createCard(personne.getNomComplet(), details, new Color(220, 240, 255));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelAffaires.add(card);
            panelAffaires.add(Box.createRigidArea(new Dimension(0, 10)));
        });
    }

    private void search() {
        String query = searchField.getText().toLowerCase();
        panelAffaires.removeAll();

        if (query.isEmpty()) {
            refreshDisplay();
            return;
        }

        if (showAffaires) {
            controller.getAffaires().stream()
                    .filter(a -> a.getNomAffaire().toLowerCase().contains(query) ||
                            a.getCrime().toLowerCase().contains(query) ||
                            a.getLieu().toLowerCase().contains(query))
                    .forEach(a -> {
                        String details = String.format(
                                "<html><b>Crime:</b> %s<br><b>Lieu:</b> %s<br><b>État:</b> %s</html>",
                                a.getCrime(), a.getLieu(), a.getEtat());
                        JPanel card = createCard(a.getNomAffaire(), details, Color.LIGHT_GRAY);
                        card.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panelAffaires.add(card);
                        panelAffaires.add(Box.createRigidArea(new Dimension(0, 10)));
                    });
        }

        if (showPersonnes) {
            controller.getPersonnes().stream()
                    .filter(p -> p.getNomComplet().toLowerCase().contains(query) ||
                            p.getProfession().toLowerCase().contains(query) ||
                            p.getQuartier().toLowerCase().contains(query))
                    .forEach(p -> {
                        String details = String.format(
                                "<html><b>Profession:</b> %s<br><b>Quartier:</b> %s<br><b>Âge:</b> %s</html>",
                                p.getProfession(), p.getQuartier());
                        JPanel card = createCard(p.getNomComplet(), details, new Color(220, 240, 255));
                        card.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panelAffaires.add(card);
                        panelAffaires.add(Box.createRigidArea(new Dimension(0, 10)));
                    });
        }

        if (panelAffaires.getComponentCount() == 0) {
            JLabel noResult = new JLabel("Aucun résultat trouvé", SwingConstants.CENTER);
            noResult.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelAffaires.add(noResult);
        }

        panelAffaires.revalidate();
        panelAffaires.repaint();
    }

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
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(bgColor);
        card.setPreferredSize(new Dimension(250, 150));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JEditorPane detailsPane = new JEditorPane();
        detailsPane.setContentType("text/html");
        detailsPane.setText(details);
        detailsPane.setEditable(false);
        detailsPane.setBackground(bgColor);
        detailsPane.setBorder(null);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(new JScrollPane(detailsPane), BorderLayout.CENTER);
        return card;
    }
}