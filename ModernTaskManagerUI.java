import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModernTaskManagerUI extends JFrame {

    private JPanel mainContentPanel;
    private JPanel homePanel, leaderboardPanel, tipsPanel;
    private JLabel pointsLabel;
    private int points = 0;

    public ModernTaskManagerUI() {
        initUI();
    }

    private void initUI() {
        // Main layout for the frame
        setLayout(new BorderLayout());

        // Create a side navigation panel
        JPanel sideNavPanel = new JPanel();
        sideNavPanel.setLayout(new GridLayout(4, 1));
        sideNavPanel.setBackground(new Color(30, 70, 30));
        sideNavPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(sideNavPanel, BorderLayout.WEST);

        // Side navigation buttons
        String[] navOptions = {"Home", "Tasks", "Leaderboard", "Tips"};
        JButton[] navButtons = new JButton[4];
        for (int i = 0; i < navOptions.length; i++) {
            navButtons[i] = new JButton(navOptions[i]);
            navButtons[i].setForeground(Color.WHITE);
            navButtons[i].setBackground(new Color(70, 70, 70));
            navButtons[i].setFocusPainted(false);
            navButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            navButtons[i].setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            sideNavPanel.add(navButtons[i]);
        }

        // Main content panel (default is Home)
        mainContentPanel = new JPanel(new CardLayout());
        add(mainContentPanel, BorderLayout.CENTER);

        // Initialize panels for each section
        homePanel = new JPanel();
        homePanel.add(new JLabel("Welcome to the Home"));
        homePanel.setBackground(Color.LIGHT_GRAY);

        // Panel to hold tasks
        JPanel tasksPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        tasksPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        tasksPanel.setBackground(new Color(245, 245, 245));

        // Create a JScrollPane for the tasks panel
        JScrollPane tasksScrollPane = new JScrollPane(tasksPanel);
        tasksScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tasksScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tasksScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        

        // Add more tasks
        createTaskCard("Use less water for a week", "+20 points", tasksPanel);
        createTaskCard("Plant a tree", "+50 points", tasksPanel);
        createTaskCard("Use public transport", "+15 points", tasksPanel);
        createTaskCard("Walk 8,000 steps daily", "+50 points", tasksPanel);
        createTaskCard("Maintain the planted tree", "+30 points", tasksPanel);
        createTaskCard("Help an animal", "+50 points", tasksPanel);
        createTaskCard("Clean your area", "+50 points", tasksPanel);
        createTaskCard("Feed someone in need", "+50 points", tasksPanel);
        createTaskCard("Beat a beggar", "+50 points", tasksPanel);
        createTaskCard("Litter your society", "+50 points", tasksPanel);
        createTaskCard("Corruption is the only religion.", "+50 points", tasksPanel);

        leaderboardPanel = new JPanel();
        leaderboardPanel.add(new JLabel("Leaderboard Coming Soon"));
        leaderboardPanel.setBackground(Color.YELLOW);

        tipsPanel = new JPanel();
        tipsPanel.add(new JLabel("Tips Page"));
        tipsPanel.setBackground(Color.ORANGE);

        // Add panels to the main content panel (CardLayout)
        mainContentPanel.add(homePanel, "Home");
        mainContentPanel.add(tasksScrollPane, "Tasks");  // Use the scroll pane for tasks
        mainContentPanel.add(leaderboardPanel, "Leaderboard");
        mainContentPanel.add(tipsPanel, "Tips");

        // Action listeners for navigation buttons
        navButtons[0].addActionListener(e -> switchPanel("Home"));
        navButtons[1].addActionListener(e -> switchPanel("Tasks"));
        navButtons[2].addActionListener(e -> switchPanel("Leaderboard"));
        navButtons[3].addActionListener(e -> switchPanel("Tips"));

        // Bottom panel for points and progress
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(new Color(240, 240, 240));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Points label
        pointsLabel = new JLabel("Total Points: 0");
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bottomPanel.add(pointsLabel, BorderLayout.EAST);

        // Add the bottom panel to the frame
        add(bottomPanel, BorderLayout.SOUTH);

        // Frame settings
        setTitle("Socio-Quest");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) (mainContentPanel.getLayout());
        cl.show(mainContentPanel, panelName);
    }

    private void createTaskCard(String taskTitle, String pointsText, JPanel parentPanel) {
        JPanel taskCard = new JPanel();
        taskCard.setLayout(new BoxLayout(taskCard, BoxLayout.Y_AXIS));
        taskCard.setBackground(Color.WHITE);
        taskCard.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));

        // Task title
        JLabel taskLabel = new JLabel(taskTitle);
        taskLabel.setFont(new Font("Arial", Font.BOLD, 16));
        taskLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        taskCard.add(taskLabel);

        // Points label
        JLabel taskPointsLabel = new JLabel(pointsText);
        taskPointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        taskPointsLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        taskCard.add(taskPointsLabel);

        // Complete button
        JButton completeButton = new JButton("Complete Task");
        completeButton.setBackground(new Color(60, 179, 113));
        completeButton.setForeground(Color.WHITE);
        completeButton.setFocusPainted(false);
        completeButton.setBorder(new EmptyBorder(10, 10, 10, 10));

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                completeTask(taskCard, pointsText);
            }
        });
        taskCard.add(completeButton);

        parentPanel.add(taskCard);
    }

    private void completeTask(JPanel taskCard, String pointsText) {
        points += Integer.parseInt(pointsText.replaceAll("[^0-9]", ""));
        pointsLabel.setText("Total Points: " + points);
        taskCard.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModernTaskManagerUI ex = new ModernTaskManagerUI();
            ex.setVisible(true);
        });
    }
}
