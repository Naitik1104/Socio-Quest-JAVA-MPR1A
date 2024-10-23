import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MprLayout extends JFrame {

    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    private MainAppPanel mainAppPanel;
    private Connection connection;
    private String username;
    private int loggedInUserID;

    public MprLayout() {
        connection = DatabaseConnection.getConnection(); 
        initUI(); 
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setSize(800, 600);


        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        add(mainContentPanel, BorderLayout.CENTER);

        // Initialize panels
        LoginPanel loginPanel = new LoginPanel(this);
        RegisterPanel registerPanel = new RegisterPanel(this);
        mainAppPanel = new MainAppPanel(this);
        AdminPanel adminPanel = new AdminPanel();
        HomePage homePage = new HomePage(); 

        // Set the panel manager for HomePage to enable smooth switching
        homePage.setPanelManager(cardLayout, mainContentPanel);
        System.out.println("HomePage panel manager set.");

        // Add all panels to the CardLayout
        mainContentPanel.add(loginPanel, "Login");
        mainContentPanel.add(registerPanel, "Register");
        mainContentPanel.add(mainAppPanel, "MainApp");
        mainContentPanel.add(adminPanel, "AdminPanel");
        mainContentPanel.add(homePage, "HomePage");

        // Start with the HomePage or Login as needed
        cardLayout.show(mainContentPanel, "HomePage");

        // Frame properties
        setTitle("Socio-Quest");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public boolean login(String username, String password) {
        String query = "SELECT * FROM userinfo WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    loggedInUserID = resultSet.getInt("UserID");
                    this.username = resultSet.getString("username");
                    System.out.println("Logged-in Username: " + this.username);  
                    int loadedQuestPoints = resultSet.getInt("questpoints");

                    JOptionPane.showMessageDialog(this, "Logged in successfully!");
                    mainAppPanel.onLoginSuccessful(this.username, loadedQuestPoints);

                    // Update last login time
                    String updateLoginTimeQuery = "UPDATE userinfo SET last_login_time = NOW() WHERE username = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateLoginTimeQuery)) {
                        updateStatement.setString(1, username);
                        updateStatement.executeUpdate();
                    }

                    // Switch to the main application panel
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

        String checkUsernameQuery = "SELECT * FROM userinfo WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkUsernameQuery)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Username already exists! Please choose another.");
                    return false;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            return false;
        }

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

    public void switchPanel(String panelName) {
        cardLayout.show(mainContentPanel, panelName);
    }

    public MainAppPanel getMainAppPanel() {
        return mainAppPanel;
    }

    public String getUsername() {
        return username;
    }

    public int getquestpoints() {
        return mainAppPanel.getquestpoints();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MprLayout ex = new MprLayout();
            ex.setVisible(true);
        });
    }
}
