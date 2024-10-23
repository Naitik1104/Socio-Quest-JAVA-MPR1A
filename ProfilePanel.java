import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProfilePanel extends JPanel {

    private JLabel usernameLabel, userIDLabel, questpointsLabel, leaderboardRankLabel;
    private JTextArea tasksArea;  // Declare tasksArea here
    private MprLayout parentLayout;
    private Connection connection;

    public ProfilePanel(MprLayout parentLayout) {
        this.parentLayout = parentLayout;
        this.connection = DatabaseConnection.getConnection(); // Get DB connection

        setLayout(new BorderLayout());

        // North Panel for User Info
        JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        usernameLabel = new JLabel();
        userIDLabel = new JLabel();
        questpointsLabel = new JLabel();
        leaderboardRankLabel = new JLabel();

        infoPanel.add(usernameLabel);
        infoPanel.add(userIDLabel);
        infoPanel.add(questpointsLabel);
        infoPanel.add(leaderboardRankLabel);
        add(infoPanel, BorderLayout.NORTH);

        // Center Panel for Tasks
        tasksArea = new JTextArea();  // Initialize tasksArea
        tasksArea.setEditable(false);
        add(new JScrollPane(tasksArea), BorderLayout.CENTER);

        // Load user data from the database
        loadUserData();
    }

    private void loadUserData() {
        String username = parentLayout.getUsername();
        String userInfoQuery = "SELECT UserID, questpoints, leaderboard_rank FROM userinfo WHERE username = ?";
        String tasksQuery = "SELECT task FROM imagedata WHERE username = ?";

        try (PreparedStatement userInfoStmt = connection.prepareStatement(userInfoQuery);
             PreparedStatement tasksStmt = connection.prepareStatement(tasksQuery)) {

            // Fetch user info
            userInfoStmt.setString(1, username);
            try (ResultSet userInfoRS = userInfoStmt.executeQuery()) {
                if (userInfoRS.next()) {
                    int userID = userInfoRS.getInt("UserID");
                    int questpoints = userInfoRS.getInt("questpoints");
                    int leaderboardRank = userInfoRS.getInt("leaderboard_rank");

                    usernameLabel.setText("Username: " + username);
                    userIDLabel.setText("User ID: " + userID);
                    questpointsLabel.setText("Quest Points: " + questpoints);
                    leaderboardRankLabel.setText("Leaderboard Rank: " + leaderboardRank);
                }
            }

            // Fetch tasks
            tasksStmt.setString(1, username);
            try (ResultSet tasksRS = tasksStmt.executeQuery()) {
                ArrayList<String> tasksList = new ArrayList<>();
                while (tasksRS.next()) {
                    tasksList.add(tasksRS.getString("task"));
                }
                tasksArea.setText(String.join("\n", tasksList));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching profile data: " + e.getMessage());
        }
    }
}
