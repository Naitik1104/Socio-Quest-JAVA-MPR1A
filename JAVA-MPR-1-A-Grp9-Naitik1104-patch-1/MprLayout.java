import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// MprLayout class that manages the layout and panel switching
public class MprLayout extends JFrame {

    private JPanel mainContentPanel;
    private CardLayout cardLayout;

    // Placeholder for registered user data
    private String registeredUsername = null;
    private String registeredPassword = null;

    public MprLayout() {
        initUI();
    }

    private void initUI() {
        // Main layout for the frame
        setLayout(new BorderLayout());
        setSize(800, 600);

        // Initialize CardLayout for switching between login, register, and the main panels
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        add(mainContentPanel, BorderLayout.CENTER);

        // Create login, register, and main panel layout
        LoginPanel loginPanel = new LoginPanel(this);
        RegisterPanel registerPanel = new RegisterPanel(this);
        MainAppPanel mainAppPanel = new MainAppPanel(this);

        mainContentPanel.add(loginPanel, "Login");
        mainContentPanel.add(registerPanel, "Register");
        mainContentPanel.add(mainAppPanel, "MainApp");

        // Start with the login panel
        cardLayout.show(mainContentPanel, "Login");

        setTitle("Socio-Quest");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public boolean login(String username, String password) {
        if (username.equals(registeredUsername) && password.equals(registeredPassword)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            return true;
        }
//        JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
        return false;
    }

    public boolean register(String username, String password, String email, String confirmPassword) {
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty!");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return false;
        }

        // Simulate saving user data
        registeredUsername = username;
        registeredPassword = password;
       // JOptionPane.showMessageDialog(this, "Registration successful!");
        return true;
    }

    public void switchPanel(String name) {
        cardLayout.show(mainContentPanel, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MprLayout ex = new MprLayout();
            ex.setVisible(true);
        });
    }
}