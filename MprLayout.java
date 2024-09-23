import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MprLayout extends JFrame {

    private JPanel mainContentPanel;
    private JPanel homePanel, leaderboardPanel, tipsPanel, tasksPanel;
    private JLabel pointsLabel;
    private int points = 0;
    private List<JPanel> easyTasks, mediumTasks, hardTasks;
    private JScrollPane tasksScrollPane;
    private JComboBox<String> difficultyDropdown;
    private JPanel taskDisplayPanel;

    public MprLayout() {
        initUI();
    }

    private void initUI() {
        // Main layout for the frame
        setLayout(new BorderLayout());
        setSize(800, 600);

        // Create a side navigation panel
        JPanel sideNavPanel = new JPanel();
        sideNavPanel.setLayout(new GridLayout(4, 1));
        sideNavPanel.setBackground(new Color(30, 70, 30));
        sideNavPanel.setBorder(new EmptyBorder(7, 7, 7, 7));
        sideNavPanel.setPreferredSize(new Dimension(120, getHeight()));
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
        initHomePanel(); // Initialize the home panel with registration fields
        initTasksPanel(); // Initialize the tasks panel with task display
        leaderboardPanel = new JPanel();
        leaderboardPanel.add(new JLabel("Leaderboard Coming Soon"));
        leaderboardPanel.setBackground(Color.YELLOW);

        tipsPanel = new JPanel();
        tipsPanel.add(new JLabel("Tips Page"));
        tipsPanel.setBackground(Color.ORANGE);

        // Add panels to the main content panel (CardLayout)
        mainContentPanel.add(homePanel, "Home");
        mainContentPanel.add(tasksPanel, "Tasks");  // Use the scroll pane for tasks
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
        bottomPanel.setPreferredSize(new Dimension(getWidth(), 30));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

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

        // Initialize task categories
        easyTasks = new ArrayList<>();
        mediumTasks = new ArrayList<>();
        hardTasks = new ArrayList<>();

        // Add tasks
        addTask("Use less water for a week", "+20 points", "Easy");
        addTask("Plant a tree", "+50 points", "Medium");
        addTask("Use public transport", "+15 points", "Easy");
        addTask("Walk 8,000 steps daily", "+50 points", "Hard");
        addTask("Maintain the planted tree", "+30 points", "Medium");
        addTask("Help an animal", "+50 points", "Hard");

        // Add difficulty dropdown
        JPanel difficultyPanel = new JPanel();
        String[] difficultyLevels = {"Easy", "Medium", "Hard"};
        difficultyDropdown = new JComboBox<>(difficultyLevels);
        difficultyPanel.add(new JLabel("Select Difficulty: "));
        difficultyPanel.add(difficultyDropdown);
        tasksPanel.add(difficultyPanel, BorderLayout.NORTH); // Add dropdown at the top of tasksPanel

        // Add action listener for dropdown
        difficultyDropdown.addActionListener(e -> {
            String selectedDifficulty = (String) difficultyDropdown.getSelectedItem();
            showTasksByDifficulty(selectedDifficulty);
        });

        // Show the default (easy) tasks on entering the task panel
        showTasksByDifficulty("Easy");
    }

    private void initHomePanel() {
        homePanel = new JPanel();
        homePanel.setBackground(Color.LIGHT_GRAY);
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField emailField = new JTextField(10);
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel userIdLabel = new JLabel("UserID");
        userIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField userIdField = new JTextField(10);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPasswordField passwordField = new JPasswordField(10);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPasswordField confirmPasswordField = new JPasswordField(10);
        
        emailField.setPreferredSize(new Dimension(100, 3));
        userIdField.setPreferredSize(new Dimension(100, 25));
        passwordField.setPreferredSize(new Dimension(100, 25));
        confirmPasswordField.setPreferredSize(new Dimension(100, 25));
        
        JButton registerButton = new JButton("Register");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        registerButton.addActionListener(e -> {
            String email = emailField.getText();
            String userId = userIdField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            if (validateRegistration(email, userId, password, confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Registration Successful");
            } else {
                JOptionPane.showMessageDialog(this, "Error: Check your inputs");
            }
        });

        homePanel.add(titleLabel);
        homePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        homePanel.add(emailLabel);
        homePanel.add(emailField);
        homePanel.add(userIdLabel);
        homePanel.add(userIdField);
        homePanel.add(passwordLabel);
        homePanel.add(passwordField);
        homePanel.add(confirmPasswordLabel);
        homePanel.add(confirmPasswordField);
        homePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        homePanel.add(registerButton);
    }

    private boolean validateRegistration(String email, String userId, String password, String confirmPassword) {
        // Simple validation logic for registration
        if (email.isEmpty() || userId.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return false;
        }
        if (!password.equals(confirmPassword)) {
            return false;
        }
        return true;
    }

    private void initTasksPanel() {
        tasksPanel = new JPanel(new BorderLayout());
        tasksPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        tasksPanel.setBackground(new Color(245, 245, 245));

        // Create a JScrollPane for the tasks panel
        tasksScrollPane = new JScrollPane();
        tasksScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tasksScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tasksScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Inner task display panel with BoxLayout for vertical task layout
        taskDisplayPanel = new JPanel();
        taskDisplayPanel.setLayout(new BoxLayout(taskDisplayPanel, BoxLayout.Y_AXIS));  // For vertical task layout
        tasksScrollPane.setViewportView(taskDisplayPanel); // Set taskDisplayPanel inside the JScrollPane

        // Add the task panel to the center of the tasksPanel
        tasksPanel.add(tasksScrollPane, BorderLayout.CENTER);
    }

    private void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) (mainContentPanel.getLayout());
        cl.show(mainContentPanel, panelName);
    }

    private void addTask(String taskTitle, String pointsText, String difficulty) {
        JPanel taskCard = new JPanel();
        taskCard.setLayout(new BoxLayout(taskCard, BoxLayout.Y_AXIS)); // Flexible layout
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

        completeButton.addActionListener(e -> completeTask(taskCard, pointsText));
        taskCard.add(completeButton);

        // Categorize task based on difficulty
        switch (difficulty) {
            case "Easy":
                easyTasks.add(taskCard);
                break;
            case "Medium":
                mediumTasks.add(taskCard);
                break;
            case "Hard":
                hardTasks.add(taskCard);
                break;
        }
    }

    private void showTasksByDifficulty(String difficulty) {
        taskDisplayPanel.removeAll();  // Clear the current tasks
        switch (difficulty) {
            case "Easy":
                for (JPanel task : easyTasks) {
                    taskDisplayPanel.add(task);
                }
                break;
            case "Medium":
                for (JPanel task : mediumTasks) {
                    taskDisplayPanel.add(task);
                }
                break;
            case "Hard":
                for (JPanel task : hardTasks) {
                    taskDisplayPanel.add(task);
                }
                break;
        }
        taskDisplayPanel.revalidate();
        taskDisplayPanel.repaint();
    }

    private void completeTask(JPanel taskCard, String pointsText) {
        points += Integer.parseInt(pointsText.replaceAll("[^0-9]", ""));
        pointsLabel.setText("Total Points: " + points);
        taskCard.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MprLayout ex = new MprLayout();
            ex.setVisible(true);
        });
    }
}
