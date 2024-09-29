import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// MprLayout class that manages the layout and panel switching
public class MprLayout extends JFrame {

    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    private MainAppPanel mainAppPanel;
    private Connection connection;

    public MprLayout() {
        connection = DatabaseConnection.getConnection(); // Initialize database connection
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
        mainAppPanel = new MainAppPanel(this);

        mainContentPanel.add(loginPanel, "Login");
        mainContentPanel.add(registerPanel, "Register");
        mainContentPanel.add(mainAppPanel, "MainApp");

        // Start with the login panel
        cardLayout.show(mainContentPanel, "Login");

        setTitle("Socio-Quest");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public boolean login(String username, String password) {
    // Query the database for user credentials
    String query = "SELECT * FROM userinfo WHERE username = ? AND password = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                
                // Collect data in the database whenever a user logs in
                String insertLoginData = "INSERT INTO login_history (username, login_time) VALUES (?, NOW())";
                

                switchPanel("MainApp");
                return true;
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
    }
    JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
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

        // Insert user data into the database
        String query = "INSERT INTO userinfo (username, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration successful!");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
        return false;
    }

    public void switchPanel(String name) {
        cardLayout.show(mainContentPanel, name);
    }

    public MainAppPanel getMainAppPanel() {
        return mainAppPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MprLayout ex = new MprLayout();
            ex.setVisible(true);
        });
    }
}
