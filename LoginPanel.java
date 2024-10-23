import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPanel extends JPanel {

    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;

    // Admin credentials (for demonstration purposes, use secure handling in real apps)
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public LoginPanel(MprLayout mainFrame) {
        setLayout(new BorderLayout());

        
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());

        
        JLabel decorativeImage = new JLabel(new ImageIcon("C:\\Users\\Acer\\Pictures\\Saved Pictures\\mpr loginpagepic.jpg"));
        decorativeImage.setHorizontalAlignment(JLabel.CENTER);
        decorativeImage.setVerticalAlignment(JLabel.CENTER);
        decorativeImage.setLayout(new GridBagLayout());

        
        imagePanel.add(decorativeImage, BorderLayout.CENTER);

        
        JPanel coloredPanel = new JPanel();
        coloredPanel.setBackground(new Color(255, 94, 77));
        coloredPanel.setPreferredSize(new Dimension(300, 0));
        coloredPanel.setLayout(new BorderLayout());
        coloredPanel.add(imagePanel, BorderLayout.CENTER); 

        add(coloredPanel, BorderLayout.WEST);

        JPanel loginFields = new JPanel(new GridLayout(5, 1, 10, 10));
        loginFields.setBorder(BorderFactory.createTitledBorder("Welcome to Socio-Quest"));

        JLabel usernameLabel = new JLabel("Username or Email:");
        loginUsernameField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        loginPasswordField = new JPasswordField(15);

        JButton loginButton = new JButton("Sign in");
        loginButton.setBackground(new Color(255, 94, 77));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        loginButton.addActionListener(e -> {
            String username = loginUsernameField.getText();
            String password = new String(loginPasswordField.getPassword());

            // Check for admin credentials
            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
               
                mainFrame.switchPanel("AdminPanel"); 
            } else if (mainFrame.login(username, password)) {
                mainFrame.switchPanel("MainApp");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login. Please try again or register.");
            }
        });

        JButton goToRegisterButton = new JButton("Not registered? Register here");
        goToRegisterButton.setBackground(Color.LIGHT_GRAY);
        goToRegisterButton.setForeground(Color.BLACK);
        goToRegisterButton.setFocusPainted(false);
        goToRegisterButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        goToRegisterButton.addActionListener(e -> {
            mainFrame.switchPanel("Register");
        });

        loginFields.add(usernameLabel);
        loginFields.add(loginUsernameField);
        loginFields.add(passwordLabel);
        loginFields.add(loginPasswordField);
        loginFields.add(loginButton);
        loginFields.add(goToRegisterButton);

        JPanel paddedFields = new JPanel();
        paddedFields.setBorder(new EmptyBorder(20, 20, 20, 20));
        paddedFields.add(loginFields);

        add(paddedFields, BorderLayout.CENTER);
    }
}
