import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JPanel {

    private CardLayout layout;
    private JPanel parentPanel;

    public HomePage() {
        setLayout(new BorderLayout(8, 8));

        JPanel contentPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color startColor = new Color(173, 216, 230);
                Color endColor = new Color(255, 255, 255);
                GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new BorderLayout(8, 8));

        
        JPanel headerPanel = new JPanel(new BorderLayout(8, 8));
        headerPanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel(
            "<html><div style='text-align: center;'>" +
            "<span style='font-size: 28px; font-family: Verdana; font-weight: bold; color: #006400;'>" +
            "Welcome to Socio-Quest</span></div></html>", JLabel.CENTER
        );
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        JLabel logoLabel = new JLabel(new ImageIcon("C:\\Users\\Acer\\SQlogom.jpeg"));
        logoLabel.setPreferredSize(new Dimension(80, 80)); // Smaller logo size
        headerPanel.add(logoLabel, BorderLayout.WEST);

        JButton loginButton = createStyledButton("Login", 16); // Reduced button size
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (layout != null && parentPanel != null) {
                    layout.show(parentPanel, "Login");
                } else {
                    JOptionPane.showMessageDialog(null, "Login Panel not configured.");
                }
            }
        });

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        loginPanel.setOpaque(false);
        loginPanel.add(loginButton);
        headerPanel.add(loginPanel, BorderLayout.EAST);

        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // Feature Panel
        JPanel featurePanel = new JPanel();
        featurePanel.setOpaque(false);
        featurePanel.setLayout(new BoxLayout(featurePanel, BoxLayout.Y_AXIS));

        JLabel featuresLabel = new JLabel(
            "<html><span style='font-size: 20px; font-family: Calibri; font-weight: bold; color: #228B22;'>Features:</span></html>"
        );
        featuresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        featurePanel.add(featuresLabel);
        featurePanel.add(Box.createRigidArea(new Dimension(0, 8))); // Smaller spacing

        addStyledFeature(featurePanel, "Tasks:", new Color(0, 100, 0),
            "<html><ul><li>Complete tasks with specified difficulty and earn quest points.</li>" +
            "<li>Upload a pic to prove your social spirit.</li></ul></html>");

        addStyledFeature(featurePanel, "Leaderboard:", new Color(0, 100, 0),
            "<html><ul><li>Compete in a live atmosphere with the leaderboard feature.</li>" +
            "<li>Push yourself up in the leaderboard ahead of your peers.</li></ul></html>");

        addStyledFeature(featurePanel, "Rewards:", new Color(139, 69, 19),
            "<html><ul><li>Earn rewards and certificates based on your quest points and leaderboard standings.</li></ul></html>");

        addStyledFeature(featurePanel, "Tips:", new Color(34, 139, 34),
            "<html><ul><li>Receive simple tips to improve your daily life.</li></ul></html>");

        contentPanel.add(featurePanel, BorderLayout.CENTER);

        // Get Started Button Panel
        JButton getStartedButton = createStyledButton("Get Started", 18);
        getStartedButton.setFont(new Font("Arial", Font.BOLD, 18));
        getStartedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Welcome to your journey with Socio-Quest!");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        buttonPanel.setOpaque(false);
        buttonPanel.add(getStartedButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(contentPanel);
    }

    private JButton createStyledButton(String text, int fontSize) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, fontSize));
        button.setBackground(new Color(34, 139, 34));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(46, 204, 113));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(34, 139, 34));
            }
        });

        return button;
    }

    public void setPanelManager(CardLayout layout, JPanel parentPanel) {
        this.layout = layout;
        this.parentPanel = parentPanel;
    }

    private void addStyledFeature(JPanel panel, String sectionTitle, Color color, String details) {
        JLabel sectionLabel = new JLabel(
            "<html><span style='font-size: 18px; font-family: Calibri; font-weight: bold; color: " +
            toHex(color) + ";'>" + sectionTitle + "</span></html>"
        );
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(sectionLabel);

        JLabel detailLabel = new JLabel(details);
        detailLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
        detailLabel.setForeground(color);
        detailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(detailLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
