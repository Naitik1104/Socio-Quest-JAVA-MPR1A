import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JPanel {

    public HomePage() {
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color startColor = new Color(204, 255, 204);  
                Color endColor = new Color(255, 255, 255);    
                GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);  

        JLabel welcomeLabel = new JLabel("Welcome to Socio-Quest", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 44));
        welcomeLabel.setForeground(new Color(34, 139, 34));  
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon("C:\\Users\\Acer\\SQlogom.jpeg")); 		
        logoLabel.setPreferredSize(new Dimension(120, 120));  
        headerPanel.add(logoLabel, BorderLayout.WEST);

        contentPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel featurePanel = new JPanel();
        featurePanel.setOpaque(false);  
        featurePanel.setLayout(new BoxLayout(featurePanel, BoxLayout.Y_AXIS));

        JLabel featuresLabel = new JLabel("Features:");
        featuresLabel.setFont(new Font("Calibri", Font.BOLD, 22));
        featuresLabel.setForeground(new Color(34, 139, 34));  
        featuresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
        featurePanel.add(featuresLabel);
        featurePanel.add(Box.createRigidArea(new Dimension(0, 10))); 

        addStyledFeature(featurePanel, "Tasks:", new Color(0, 100, 0),
                "• Complete tasks with specified difficulty and earn quest points.",
                "• Upload a pic to prove your social spirit."
        );

        addStyledFeature(featurePanel, "Leaderboard:", new Color(0, 100, 0),
                "• Compete in a live atmosphere with the leaderboard feature.",
                "• Push yourself up in the leaderboard ahead of your peers."
        );

        addStyledFeature(featurePanel, "Rewards:", new Color(139, 69, 19),  
                "• Earn rewards and certificates based on your quest points and leaderboard standings."
        );

        addStyledFeature(featurePanel, "Tips:", new Color(34, 139, 34),
                "• Receive simple tips to improve your daily life."
        );

        contentPanel.add(featurePanel, BorderLayout.CENTER);

        JButton getStartedButton = new JButton("Get Started");
        getStartedButton.setFont(new Font("Arial", Font.BOLD, 20));
        getStartedButton.setBackground(new Color(34, 139, 34));
        getStartedButton.setForeground(Color.WHITE);
        getStartedButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        getStartedButton.setFocusPainted(false);
        getStartedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Welcome to your journey with Socio-Quest!");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);  
        buttonPanel.add(getStartedButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(contentPanel);
    }

    private void addStyledFeature(JPanel panel, String sectionTitle, Color color, String... details) {
        JLabel sectionLabel = new JLabel(sectionTitle);
        sectionLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        sectionLabel.setForeground(color);
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  
        panel.add(sectionLabel);

        for (String detail : details) {
            JLabel detailLabel = new JLabel(detail);
            detailLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
            detailLabel.setForeground(color);
            detailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(detailLabel);
        }

        panel.add(Box.createRigidArea(new Dimension(0, 15)));  
    }
}
