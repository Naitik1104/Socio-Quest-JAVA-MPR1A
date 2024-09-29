import javax.swing.*;
import java.awt.*;

public class MainAppPanel extends JPanel {

    private JPanel homePanel, tasksPanel, leaderboardPanel, tipsPanel, rewardsPanel, redeemPanel;
    private JLabel questpointsLabel;
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
        tipsPanel = new tipsPanel(); // Adjusted to correct class name
        rewardsPanel = new Rewards(); // Pass this MainAppPanel instance to Rewards
        redeemPanel = new REDEEM(); // Ensure this is the correct REDEEM class

        JPanel mainContent = new JPanel(new CardLayout());
        add(mainContent, BorderLayout.CENTER);

        mainContent.add(homePanel, "Home");
        mainContent.add(tasksPanel, "Tasks");
        mainContent.add(leaderboardPanel, "Leaderboard");
        mainContent.add(tipsPanel, "Tips");
        mainContent.add(rewardsPanel, "Rewards");
        mainContent.add(redeemPanel, "Redeem"); // Add the REDEEM panel to CardLayout

        navButtons[0].addActionListener(e -> ((CardLayout) mainContent.getLayout()).show(mainContent, "Home"));
        navButtons[1].addActionListener(e -> ((CardLayout) mainContent.getLayout()).show(mainContent, "Tasks"));
        navButtons[2].addActionListener(e -> ((CardLayout) mainContent.getLayout()).show(mainContent, "Leaderboard"));
        navButtons[3].addActionListener(e -> ((CardLayout) mainContent.getLayout()).show(mainContent, "Tips"));
        navButtons[4].addActionListener(e -> ((CardLayout) mainContent.getLayout()).show(mainContent, "Rewards"));
    }

    

    public void addquestpoints(int questpointsToAdd) {
        questpoints += questpointsToAdd;
        questpointsLabel.setText("Total questpoints: " + questpoints);
    }
}
