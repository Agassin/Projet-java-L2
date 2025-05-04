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
    private final String username;
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JPanel dashboardPanel;
    private JPanel profilePanel;
    private JPanel notificationsPanel;
    private JPanel settingsPanel;
    private JTextField searchField;
    private JPanel panelAffaires;
    private JButton btnAffaires, btnPersonnes;
    private boolean showAffaires = true;
    private boolean showPersonnes = true;

    public AffaireView(AffaireController controller, String username) {
        this.controller = Objects.requireNonNull(controller, "Controller ne peut pas être null");
        this.username = username;
        initUI();
        showDashboard();
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

        contentPanel = new JPanel(new CardLayout());
        dashboardPanel = createDashboard();
        profilePanel = createProfilePanel();
        notificationsPanel = createNotificationsPanel();
        settingsPanel = createSettingsPanel();

        contentPanel.add(dashboardPanel, "dashboard");
        contentPanel.add(profilePanel, "profile");
        contentPanel.add(notificationsPanel, "notifications");
        contentPanel.add(settingsPanel, "settings");

        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(240, 240, 240));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        ImageIcon icon = new ImageIcon("images/Logo_gend.png");
        Image scaledImage = icon.getImage().getScaledInstance(120, 70, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(scaledImage), SwingConstants.LEFT);
        header.add(logo, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 40));
        searchField = new JTextField(25);
        searchField.setPreferredSize(new Dimension(300, 30));
        searchPanel.add(searchField);
        header.add(searchPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 40));
        JButton profileButton = createIconButton("Profil", new Color(24, 75, 146));
        profileButton.addActionListener(e -> showProfile());
        buttonPanel.add(profileButton);

        JButton notifButton = createIconButton2("Notifications", new Color(211, 211, 211));
        notifButton.addActionListener(e -> showNotifications());
        buttonPanel.add(notifButton);

        JButton settingsButton = createIconButton("Paramètres", new Color(200, 0, 0));
        settingsButton.addActionListener(e -> showSettings());
        buttonPanel.add(settingsButton);

        header.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(header, BorderLayout.NORTH);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { search(); }
            @Override public void removeUpdate(DocumentEvent e) { search(); }
            @Override public void changedUpdate(DocumentEvent e) { search(); }
        });
    }

    private JPanel createDashboard() {
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBorder(new EmptyBorder(10, 20, 20, 20));

        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        statsPanel.add(createStatCard("Affaires en cours", "5 000", "62%", new Color(33, 150, 243)));
        statsPanel.add(createStatCard("Affaires classées", "2 500", "25%", new Color(76, 175, 80)));
        statsPanel.add(createStatCard("Affaires urgentes", "150", "12%", new Color(244, 67, 54)));
        statsPanel.add(createStatCard("Amendes à verser", "15 000", "€", new Color(255, 193, 7)));
        dashboard.add(statsPanel, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());

        JPanel affairesPanel = new JPanel(new BorderLayout());
        affairesPanel.setBorder(BorderFactory.createTitledBorder("Liste des affaires et personnes"));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        btnAffaires = new JButton("Affaires");
        btnPersonnes = new JButton("Personnes");

        // Nouveaux boutons
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");

        // Configuration des couleurs des boutons
        btnAffaires.setBackground(new Color(24, 75, 146));
        btnAffaires.setForeground(Color.WHITE);
        btnPersonnes.setBackground(new Color(24, 75, 146));
        btnPersonnes.setForeground(Color.WHITE);

        btnAjouter.setBackground(new Color(34, 139, 34)); // Vert
        btnAjouter.setForeground(Color.WHITE);
        btnModifier.setBackground(new Color(218, 165, 32)); // Or
        btnModifier.setForeground(Color.WHITE);
        btnSupprimer.setBackground(new Color(220, 20, 60)); // Rouge
        btnSupprimer.setForeground(Color.WHITE);

        Dimension btnSize = new Dimension(120, 30);
        btnAffaires.setPreferredSize(btnSize);
        btnPersonnes.setPreferredSize(btnSize);
        btnAjouter.setPreferredSize(btnSize);
        btnModifier.setPreferredSize(btnSize);
        btnSupprimer.setPreferredSize(btnSize);

        btnAffaires.addActionListener(e -> {
            showAffaires = !showAffaires;
            btnAffaires.setBackground(showAffaires ? new Color(24, 75, 146) : new Color(200, 200, 200));
            btnAffaires.setForeground(showAffaires ? Color.WHITE : Color.BLACK);
            refreshDisplay();
        });

        btnPersonnes.addActionListener(e -> {
            showPersonnes = !showPersonnes;
            btnPersonnes.setBackground(showPersonnes ? new Color(24, 75, 146) : new Color(200, 200, 200));
            btnPersonnes.setForeground(showPersonnes ? Color.WHITE : Color.BLACK);
            refreshDisplay();
        });

        // Ajout des actions pour les nouveaux boutons
        btnAjouter.addActionListener(e -> ajouterElement());
        btnModifier.addActionListener(e -> modifierElement());
        btnSupprimer.addActionListener(e -> supprimerElement());

        filterPanel.add(btnAffaires);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(btnPersonnes);
        filterPanel.add(Box.createHorizontalStrut(20)); // Espacement supplémentaire
        filterPanel.add(btnAjouter);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(btnModifier);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(btnSupprimer);

        affairesPanel.add(filterPanel, BorderLayout.NORTH);

        panelAffaires = new JPanel();
        panelAffaires.setLayout(new BoxLayout(panelAffaires, BoxLayout.Y_AXIS));
        panelAffaires.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelAffaires);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        affairesPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel notifPanel = new JPanel(new BorderLayout());
        notifPanel.setBorder(BorderFactory.createTitledBorder("Actualités"));
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

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, affairesPanel, notifPanel);
        splitPane.setResizeWeight(0.7);
        splitPane.setDividerLocation(0.7);

        content.add(splitPane, BorderLayout.CENTER);
        dashboard.add(content, BorderLayout.CENTER);

        return dashboard;
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel profileInfoPanel = new JPanel();
        profileInfoPanel.setLayout(new BoxLayout(profileInfoPanel, BoxLayout.Y_AXIS));
        profileInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Profil Utilisateur", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel usernameLabel = new JLabel("Identifiant: " + username);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel nameLabel = new JLabel("Nom: ADMINISTRATEUR");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel roleLabel = new JLabel("Rôle: Administrateur système");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lastLoginLabel = new JLabel("Dernière connexion: " + new java.util.Date());
        lastLoginLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        lastLoginLabel.setForeground(Color.GRAY);

        JButton backButton = new JButton("Retour au tableau de bord");
        backButton.setBackground(new Color(0, 120, 215));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> showDashboard());

        profileInfoPanel.add(titleLabel);
        profileInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        profileInfoPanel.add(usernameLabel);
        profileInfoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        profileInfoPanel.add(nameLabel);
        profileInfoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        profileInfoPanel.add(roleLabel);
        profileInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        profileInfoPanel.add(lastLoginLabel);
        profileInfoPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        profileInfoPanel.add(backButton);

        panel.add(profileInfoPanel, BorderLayout.CENTER);
        return panel;
    }

    private void showAddSelectionDialog() {
        JDialog selectionDialog = new JDialog(this, "Ajouter un élément", true);
        selectionDialog.setLayout(new BorderLayout());
        selectionDialog.setSize(300, 150);
        selectionDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton btnAddAffaire = new JButton("Ajouter une affaire");
        btnAddAffaire.addActionListener(e -> {
            selectionDialog.dispose();
            showAddAffaireDialog();
        });

        JButton btnAddPersonne = new JButton("Ajouter une personne");
        btnAddPersonne.addActionListener(e -> {
            selectionDialog.dispose();
            showAddPersonneDialog();
        });

        panel.add(btnAddAffaire);
        panel.add(btnAddPersonne);

        selectionDialog.add(panel, BorderLayout.CENTER);
        selectionDialog.setVisible(true);
    }

    private void showAddAffaireDialog() {
        JDialog addDialog = new JDialog(this, "Ajouter une affaire", true);
        addDialog.setLayout(new BorderLayout());
        addDialog.setSize(500, 500);
        addDialog.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Champs pour une affaire
        JTextField nomAffaireField = new JTextField();
        JTextField crimeField = new JTextField();
        JTextField suspectField = new JTextField();
        JTextField coupableField = new JTextField();
        JTextField lieuField = new JTextField();
        JTextField etatField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField descriptionField = new JTextField();

        formPanel.add(new JLabel("Nom de l'affaire:"));
        formPanel.add(nomAffaireField);
        formPanel.add(new JLabel("Crime:"));
        formPanel.add(crimeField);
        formPanel.add(new JLabel("Suspect:"));
        formPanel.add(suspectField);
        formPanel.add(new JLabel("Coupable:"));
        formPanel.add(coupableField);
        formPanel.add(new JLabel("Lieu:"));
        formPanel.add(lieuField);
        formPanel.add(new JLabel("État:"));
        formPanel.add(etatField);
        formPanel.add(new JLabel("Date (AAAA-MM-JJ):"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descriptionField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Annuler");
        JButton saveButton = new JButton("Enregistrer");

        cancelButton.addActionListener(e -> addDialog.dispose());
        saveButton.addActionListener(e -> {
            // Créer et ajouter la nouvelle affaire
            Personne suspect = controller.trouverPersonneParNom(controller.getPersonnes(), suspectField.getText());
            Personne coupable = controller.trouverPersonneParNom(controller.getPersonnes(), coupableField.getText());

            Affaire nouvelleAffaire = new Affaire(
                    nomAffaireField.getText(),
                    crimeField.getText(),
                    suspect,
                    coupable,
                    lieuField.getText(),
                    etatField.getText(),
                    dateField.getText(),
                    descriptionField.getText()
            );

            controller.ajouterAffaire(nouvelleAffaire);
            addDialog.dispose();
            refreshDisplay();
            JOptionPane.showMessageDialog(this, "Affaire ajoutée avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);
        addDialog.setVisible(true);
    }

    private void showAddPersonneDialog() {
        JDialog addDialog = new JDialog(this, "Ajouter une personne", true);
        addDialog.setLayout(new BorderLayout());
        addDialog.setSize(500, 500);
        addDialog.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Champs pour une personne
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField quartierField = new JTextField();
        JTextField professionField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField antecedentsField = new JTextField();
        JTextField descriptionField = new JTextField();

        formPanel.add(new JLabel("Nom:"));
        formPanel.add(nomField);
        formPanel.add(new JLabel("Prénom:"));
        formPanel.add(prenomField);
        formPanel.add(new JLabel("Âge:"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("Quartier:"));
        formPanel.add(quartierField);
        formPanel.add(new JLabel("Profession:"));
        formPanel.add(professionField);
        formPanel.add(new JLabel("Genre:"));
        formPanel.add(genreField);
        formPanel.add(new JLabel("Antécédents:"));
        formPanel.add(antecedentsField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descriptionField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Annuler");
        JButton saveButton = new JButton("Enregistrer");

        cancelButton.addActionListener(e -> addDialog.dispose());
        saveButton.addActionListener(e -> {
            // Créer et ajouter la nouvelle personne
            Personne nouvellePersonne = new Personne(
                    nomField.getText(),
                    prenomField.getText(),
                    ageField.getText(),
                    quartierField.getText(),
                    professionField.getText(),
                    genreField.getText(),
                    antecedentsField.getText(),
                    descriptionField.getText()
            );

            controller.getPersonnes().add(nouvellePersonne);
            addDialog.dispose();
            refreshDisplay();
            JOptionPane.showMessageDialog(this, "Personne ajoutée avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);
        addDialog.setVisible(true);
    }

    private void ajouterElement() {
        showAddSelectionDialog();
    }

    private void modifierElement() {
        if (showAffaires) {
            // Logique pour modifier une affaire existante
            JOptionPane.showMessageDialog(this,
                    "Fonctionnalité de modification d'affaire à implémenter",
                    "Modifier", JOptionPane.INFORMATION_MESSAGE);
        } else if (showPersonnes) {
            // Logique pour modifier une personne existante
            JOptionPane.showMessageDialog(this,
                    "Fonctionnalité de modification de personne à implémenter",
                    "Modifier", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void supprimerElement() {
        if (showAffaires) {
            // Logique pour supprimer une affaire
            JOptionPane.showMessageDialog(this,
                    "Fonctionnalité de suppression d'affaire à implémenter",
                    "Supprimer", JOptionPane.INFORMATION_MESSAGE);
        } else if (showPersonnes) {
            // Logique pour supprimer une personne
            JOptionPane.showMessageDialog(this,
                    "Fonctionnalité de suppression de personne à implémenter",
                    "Supprimer", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JPanel createNotificationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel notifContentPanel = new JPanel();
        notifContentPanel.setLayout(new BoxLayout(notifContentPanel, BoxLayout.Y_AXIS));
        notifContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Notifications", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel noNotifLabel = new JLabel("Aucune nouvelle notification", SwingConstants.CENTER);
        noNotifLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        noNotifLabel.setForeground(Color.GRAY);

        JButton backButton = new JButton("Retour au tableau de bord");
        backButton.setBackground(new Color(0, 120, 215));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> showDashboard());

        notifContentPanel.add(titleLabel);
        notifContentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        notifContentPanel.add(noNotifLabel);
        notifContentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        notifContentPanel.add(backButton);

        panel.add(notifContentPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel settingsContent = new JPanel();
        settingsContent.setLayout(new BoxLayout(settingsContent, BoxLayout.Y_AXIS));
        settingsContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Paramètres", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Section Thème
        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        themePanel.setBorder(BorderFactory.createTitledBorder("Apparence"));

        ButtonGroup themeGroup = new ButtonGroup();
        JRadioButton lightTheme = new JRadioButton("Thème clair", true);
        JRadioButton darkTheme = new JRadioButton("Thème sombre");
        themeGroup.add(lightTheme);
        themeGroup.add(darkTheme);

        themePanel.add(lightTheme);
        themePanel.add(darkTheme);

        // Section Notifications
        JPanel notifPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        notifPanel.setBorder(BorderFactory.createTitledBorder("Notifications"));

        JCheckBox newCaseNotif = new JCheckBox("Alertes nouvelles affaires", true);
        JCheckBox urgentCaseNotif = new JCheckBox("Alertes affaires urgentes", true);
        JCheckBox systemNotif = new JCheckBox("Notifications système", true);

        notifPanel.add(newCaseNotif);
        notifPanel.add(urgentCaseNotif);
        notifPanel.add(systemNotif);

        // Section Données
        JPanel dataPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        dataPanel.setBorder(BorderFactory.createTitledBorder("Gestion des données"));

        JButton exportBtn = new JButton("Exporter les données");
        exportBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Fonctionnalité d'export à implémenter",
                "Export", JOptionPane.INFORMATION_MESSAGE));

        JButton importBtn = new JButton("Importer des données");
        importBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Fonctionnalité d'import à implémenter",
                "Import", JOptionPane.INFORMATION_MESSAGE));

        dataPanel.add(exportBtn);
        dataPanel.add(importBtn);

        // Bouton Retour
        JButton backButton = new JButton("Retour au tableau de bord");
        backButton.setBackground(new Color(0, 120, 215));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> showDashboard());

        // Assemblage des composants
        settingsContent.add(titleLabel);
        settingsContent.add(Box.createRigidArea(new Dimension(0, 20)));
        settingsContent.add(themePanel);
        settingsContent.add(Box.createRigidArea(new Dimension(0, 20)));
        settingsContent.add(notifPanel);
        settingsContent.add(Box.createRigidArea(new Dimension(0, 20)));
        settingsContent.add(dataPanel);
        settingsContent.add(Box.createRigidArea(new Dimension(0, 30)));
        settingsContent.add(backButton);

        panel.add(settingsContent, BorderLayout.CENTER);
        return panel;
    }

    private void showDashboard() {
        CardLayout cl = (CardLayout)(contentPanel.getLayout());
        cl.show(contentPanel, "dashboard");
    }

    private void showProfile() {
        CardLayout cl = (CardLayout)(contentPanel.getLayout());
        cl.show(contentPanel, "profile");
    }

    private void showNotifications() {
        CardLayout cl = (CardLayout)(contentPanel.getLayout());
        cl.show(contentPanel, "notifications");
    }

    private void showSettings() {
        CardLayout cl = (CardLayout)(contentPanel.getLayout());
        cl.show(contentPanel, "settings");
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
        String searchText = searchField.getText().trim().toLowerCase();
        panelAffaires.removeAll();

        if (searchText.isEmpty()) {
            refreshDisplay(); // Affichage normal
            return;
        }

        if (showAffaires) {
            for (Affaire affaire : controller.getAffaires()) {
                if (affaire.getNomAffaire().toLowerCase().contains(searchText) ||
                        affaire.getCrime().toLowerCase().contains(searchText) ||
                        affaire.getLieu().toLowerCase().contains(searchText)) {

                    String details = String.format(
                            "<html><b>Crime:</b> %s<br><b>Lieu:</b> %s<br><b>État:</b> %s<br><b>Date:</b> %s</html>",
                            affaire.getCrime(), affaire.getLieu(), affaire.getEtat(), affaire.getDate());

                    JPanel card = createCard(affaire.getNomAffaire(), details, Color.LIGHT_GRAY);
                    card.setAlignmentX(Component.LEFT_ALIGNMENT);
                    panelAffaires.add(card);
                    panelAffaires.add(Box.createRigidArea(new Dimension(0, 10)));
                }
            }
        }

        if (showPersonnes) {
            for (Personne personne : controller.getPersonnes()) {
                if (personne.getNomComplet().toLowerCase().contains(searchText) ||
                        personne.getProfession().toLowerCase().contains(searchText) ||
                        personne.getQuartier().toLowerCase().contains(searchText)) {

                    String details = String.format(
                            "<html><b>Profession:</b> %s<br><b>Quartier:</b> %s<br><b>Antécédents:</b> %s</html>",
                            personne.getProfession(), personne.getQuartier(), personne.getAntecedents());

                    JPanel card = createCard(personne.getNomComplet(), details, new Color(255, 255, 204));
                    card.setAlignmentX(Component.LEFT_ALIGNMENT);
                    panelAffaires.add(card);
                    panelAffaires.add(Box.createRigidArea(new Dimension(0, 10)));
                }
            }
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

    private JButton createIconButton2(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(new Color(24, 75, 146));
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