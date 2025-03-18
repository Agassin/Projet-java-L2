import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AffaireView extends JFrame {
    private JPanel panelAffaires;
    private JPanel menuLateral;
    private AffaireController controller;
    private boolean menuVisible = true;

    public AffaireView(AffaireController controller) {
        this.controller = controller;
        setTitle("Gestion des Affaires");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // HEADER
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout());
        JButton toggleMenuButton = new JButton("☰");
        toggleMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMenu();
            }
        });
        header.add(toggleMenuButton);
        header.add(new JLabel("[Logo]"));
        header.add(new JTextField(20));

        JButton profilButton = new JButton("Profil");
        profilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginView(AffaireView.this).setVisible(true);
            }
        });
        header.add(profilButton);

        header.add(new JButton("Notifications"));
        header.add(new JButton("Paramètres"));
        add(header, BorderLayout.NORTH);

        // MENU LATÉRAL
        menuLateral = new JPanel();
        menuLateral.setLayout(new GridLayout(6, 1));
        menuLateral.add(new JButton("Affaires en cours"));
        menuLateral.add(new JButton("Recherche avancée"));
        menuLateral.add(new JButton("Analyse de liens"));
        menuLateral.add(new JButton("Prédictions de suspects"));
        menuLateral.add(new JButton("Historique des commentaires"));
        menuLateral.add(new JButton("Autre"));
        add(menuLateral, BorderLayout.WEST);

        // SECTION PRINCIPALE
        panelAffaires = new JPanel();
        panelAffaires.setLayout(new GridLayout(0, 3));
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
        for (Affaire affaire : controller.getAffaires()) {
            JPanel carte = new JPanel();
            carte.setLayout(new BorderLayout());
            carte.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            carte.add(new JLabel("Image"), BorderLayout.NORTH);
            carte.add(new JLabel(affaire.getTitre()), BorderLayout.CENTER);
            carte.add(new JLabel(affaire.getStatut()), BorderLayout.SOUTH);
            panelAffaires.add(carte);
        }
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
