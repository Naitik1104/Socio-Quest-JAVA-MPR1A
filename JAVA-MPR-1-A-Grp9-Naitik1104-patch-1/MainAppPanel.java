import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MainAppPanel extends JPanel {

    private JPanel homePanel, tasksPanel, leaderboardPanel, tipsPanel, rewardsPanel, redeemPanel;
    private JLabel questpointsLabel;
    private String username;
private int userId;	
    private int questpoints = 0;
    private MprLayout parentLayout;

    public MainAppPanel(MprLayout parentLayout) {
        this.parentLayout = parentLayout;
        setLayout(new BorderLayout());

        JPanel sideNavPanel = new JPanel(new GridLayout(5, 1));
        sideNavPanel.setBackground(new Color(30, 70, 30));
        sideNavPanel.setPreferredSize(new Dimension(120, getHeight()));
        add(sideNavPanel, BorderLayout.WEST);

        String[] navOptions = {"Home", "Tasks", "Leaderboard", "Tips", "Rewards"};
        JButton[] navButtons = new JButton[navOptions.length];
        for (int i = 0; i < navOptions.length; i++) {
            navButtons[i] = new JButton(navOptions[i]);
            navButtons[i].setForeground(Color.WHITE);
            navButtons[i].setBackground(new Color(70, 70, 70));
            navButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            navButtons[i].setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            navButtons[i].setFocusPainted(false);
            sideNavPanel.add(navButtons[i]);
        }

        questpointsLabel = new JLabel("Total questpoints: 0");
        questpointsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questpointsLabel, BorderLayout.NORTH);

        homePanel = new HomePage();
        tasksPanel = new TasksPanel(this);
        leaderboardPanel = new LeaderboardPanel();
        tipsPanel = new tipsPanel(); 
        rewardsPanel = new Rewards();
        redeemPanel = new REDEEM();

        JPanel mainContent = new JPanel(new CardLayout());
        add(mainContent, BorderLayout.CENTER);

        mainContent.add(homePanel, "Home");
        mainContent.add(tasksPanel, "Tasks");
        mainContent.add(leaderboardPanel, "Leaderboard");
        mainContent.add(tipsPanel, "Tips");
        mainContent.add(rewardsPanel, "Rewards");
        mainContent.add(redeemPanel, "Redeem");

        navButtons[0].addActionListener(e -> ((CardLayout) mainContent.getLayout()).show(mainContent, "Home"));
        navButtons[1].addActionListener(e -> ((CardLayout) mainContent.getLayout()).show(mainContent, "Tasks"));
        navButtons[2].addActionListener(e -> ((CardLayout) mainContent.getLayout()).show(mainContent, "Leaderboard"));
        navButtons[3].addActionListener(e -> ((CardLayout) mainContent.getLayout()).show(mainContent, "Tips"));
        navButtons[4].addActionListener(e -> ((CardLayout) mainContent.getLayout()).show(mainContent, "Rewards"));
    }
	public void onLoginSuccessful(String username, int loadedQuestpoints) {
    this.username = username; 
    this.questpoints = loadedQuestpoints; 
    questpointsLabel.setText("Total questpoints: " + questpoints);
}


   public void addquestpoints(int questpointsToAdd) {
    if (questpointsToAdd < 0) {
        JOptionPane.showMessageDialog(this, "Cannot add negative questpoints!");
        return; // Prevent adding negative questpoints
    }

    questpoints += questpointsToAdd;
    questpointsLabel.setText("Total questpoints: " + questpoints);
    System.out.println("Updating questpoints for username: " + username + " with value: " + questpoints);
    updateQuestPointsInDatabase(username);
}
public String getUsername() {
    return this.username; 
}
 public int getUserID() {
        return userId;
    }


private void updateQuestPointsInDatabase(String username) {
    Connection connection = DatabaseConnection.getConnection();
    if (connection != null) {
        String updateQuery = "UPDATE userinfo SET questpoints = ? WHERE Username = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setInt(1, questpoints);
            statement.setString(2, username); 
            statement.executeUpdate();
            System.out.println("Questpoints updated for username: " + username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

}
