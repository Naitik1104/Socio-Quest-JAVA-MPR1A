import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MprLayout extends JFrame {

    private JPanel mainContentPanel;
    private JPanel homePanel, leaderboardPanel, tipsPanel, tasksPanel, loginPanel, registerPanel;
    private JLabel pointsLabel;
    private int points = 0;
    private List<JPanel> easyTasks, mediumTasks, hardTasks;
    private JScrollPane tasksScrollPane;
    private JComboBox<String> difficultyDropdown;
    private JPanel taskDisplayPanel;
    private CardLayout cardLayout;

    // Fields for login and register components
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    private JTextField registerUsernameField;
    private JPasswordField registerPasswordField;
    private JPasswordField registerConfirmPasswordField;
    private JTextField registerEmailField;

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
        createLoginPanel();
        createRegisterPanel();
        createMainAppPanel();

        // Start with the login panel
        cardLayout.show(mainContentPanel, "Login");

        setTitle("Socio-Quest");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createLoginPanel() {
        loginPanel = new JPanel(new BorderLayout());

        // Custom Image Box - Big Blue Box
        JPanel imageBoxPanel = new JPanel();
        imageBoxPanel.setBackground(Color.BLUE);
        imageBoxPanel.setPreferredSize(new Dimension(200, 200)); // You can add your custom image here
        loginPanel.add(imageBoxPanel, BorderLayout.NORTH);

        JPanel loginFields = new JPanel(new GridLayout(3, 2, 10, 10));
        loginFields.setBorder(BorderFactory.createTitledBorder("Login"));

        JLabel usernameLabel = new JLabel("Username:");
        loginUsernameField = new JTextField(15);  // Reduced field size

        JLabel passwordLabel = new JLabel("Password:");
        loginPasswordField = new JPasswordField(15);  // Reduced field size

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.BLUE);
        loginButton.setForeground(Color.WHITE);

        loginButton.addActionListener(e -> {
            String username = loginUsernameField.getText();
            String password = new String(loginPasswordField.getPassword());
            if (login(username, password)) {
                cardLayout.show(mainContentPanel, "MainApp");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login. Please try again or register.");
            }
        });

        JButton goToRegisterButton = new JButton("Not registered? Register here");
        goToRegisterButton.setBackground(Color.LIGHT_GRAY);
        goToRegisterButton.setForeground(Color.BLACK);

        goToRegisterButton.addActionListener(e -> {
            cardLayout.show(mainContentPanel, "Register");
        });

        loginFields.add(usernameLabel);
        loginFields.add(loginUsernameField);
        loginFields.add(passwordLabel);
        loginFields.add(loginPasswordField);
        loginFields.add(loginButton);
        loginFields.add(goToRegisterButton);

        // Add some padding to the login fields panel
        JPanel paddedFields = new JPanel();
        paddedFields.setBorder(new EmptyBorder(20, 20, 20, 20));
        paddedFields.add(loginFields);
        loginPanel.add(paddedFields, BorderLayout.CENTER);

        mainContentPanel.add(loginPanel, "Login");
    }

    private void createRegisterPanel() {
        registerPanel = new JPanel(new BorderLayout());

        JPanel registerFields = new JPanel(new GridLayout(5, 2, 10, 10));
        registerFields.setBorder(BorderFactory.createTitledBorder("Register"));

        JLabel registerUsernameLabel = new JLabel("Username:");
        registerUsernameField = new JTextField(15);  // Reduced field size

        JLabel registerEmailLabel = new JLabel("Email:");
        registerEmailField = new JTextField(15);  // Reduced field size

        JLabel registerPasswordLabel = new JLabel("Password:");
        registerPasswordField = new JPasswordField(15);  // Reduced field size

        JLabel registerConfirmPasswordLabel = new JLabel("Confirm Password:");
        registerConfirmPasswordField = new JPasswordField(15);  // Reduced field size

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(Color.GREEN);
        registerButton.setForeground(Color.BLACK);

        registerButton.addActionListener(e -> {
            String username = registerUsernameField.getText();
            String email = registerEmailField.getText();
            String password = new String(registerPasswordField.getPassword());
            String confirmPassword = new String(registerConfirmPasswordField.getPassword());

            if (register(username, password, email, confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Registration successful! Please log in.");
                cardLayout.show(mainContentPanel, "Login");
            }
        });

        registerFields.add(registerUsernameLabel);
        registerFields.add(registerUsernameField);
        registerFields.add(registerEmailLabel);
        registerFields.add(registerEmailField);
        registerFields.add(registerPasswordLabel);
        registerFields.add(registerPasswordField);
        registerFields.add(registerConfirmPasswordLabel);
        registerFields.add(registerConfirmPasswordField);
        registerFields.add(registerButton);

        // Add some padding to the register fields panel
        JPanel paddedFields = new JPanel();
        paddedFields.setBorder(new EmptyBorder(20, 20, 20, 20));
        paddedFields.add(registerFields);
        registerPanel.add(paddedFields, BorderLayout.CENTER);

        mainContentPanel.add(registerPanel, "Register");
    }

    private void createMainAppPanel() {
        JPanel mainAppPanel = new JPanel(new BorderLayout());

        // Side navigation
        JPanel sideNavPanel = new JPanel(new GridLayout(4, 1));
        sideNavPanel.setBackground(new Color(30, 70, 30));
        sideNavPanel.setBorder(new EmptyBorder(7, 7, 7, 7));
        sideNavPanel.setPreferredSize(new Dimension(120, getHeight()));
        mainAppPanel.add(sideNavPanel, BorderLayout.WEST);

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

        // Panels for the different sections
        homePanel = new JPanel();
        homePanel.add(new JLabel("Welcome to the Home Page"));
        homePanel.setBackground(Color.LIGHT_GRAY);

        leaderboardPanel = new JPanel();
        leaderboardPanel.add(new JLabel("Leaderboard Coming Soon"));
        leaderboardPanel.setBackground(Color.YELLOW);

        tipsPanel = new JPanel();
        tipsPanel.add(new JLabel("Tips Page"));
        tipsPanel.setBackground(Color.ORANGE);

        initTasksPanel();

        // Main content panel (CardLayout for different sections)
        JPanel mainContent = new JPanel(new CardLayout());
        mainAppPanel.add(mainContent, BorderLayout.CENTER);

        mainContent.add(homePanel, "Home");
        mainContent.add(tasksPanel, "Tasks");
        mainContent.add(leaderboardPanel, "Leaderboard");
        mainContent.add(tipsPanel, "Tips");

        // Button listeners for switching between sections
        navButtons[0].addActionListener(e -> switchPanel(mainContent, "Home"));
        navButtons[1].addActionListener(e -> switchPanel(mainContent, "Tasks"));
        navButtons[2].addActionListener(e -> switchPanel(mainContent, "Leaderboard"));
        navButtons[3].addActionListener(e -> switchPanel(mainContent, "Tips"));

        mainContentPanel.add(mainAppPanel, "MainApp");
    }

    private void initTasksPanel() {
        tasksPanel = new JPanel(new BorderLayout());
        tasksPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        tasksPanel.setBackground(new Color(245, 245, 245));

        tasksScrollPane = new JScrollPane();
        tasksScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tasksScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tasksScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        taskDisplayPanel = new JPanel();
        taskDisplayPanel.setLayout(new BoxLayout(taskDisplayPanel, BoxLayout.Y_AXIS));
        tasksScrollPane.setViewportView(taskDisplayPanel);

        tasksPanel.add(tasksScrollPane, BorderLayout.CENTER);

        JPanel taskFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taskFilterPanel.setBackground(new Color(220, 220, 220));

        JLabel filterLabel = new JLabel("Filter by Difficulty:");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 14));
        taskFilterPanel.add(filterLabel);

        difficultyDropdown = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        difficultyDropdown.addActionListener(e -> showTasksByDifficulty((String) difficultyDropdown.getSelectedItem()));
        taskFilterPanel.add(difficultyDropdown);

        tasksPanel.add(taskFilterPanel, BorderLayout.NORTH);

        easyTasks = new ArrayList<>();
        mediumTasks = new ArrayList<>();
        hardTasks = new ArrayList<>();

        // Sample tasks
        addTask("Use less water for a week", "+20 points", "Easy");
        addTask("Plant a tree", "+50 points", "Medium");
        addTask("Walk 8,000 steps daily", "+50 points", "Hard");
        addTask("Help an animal", "+50 points", "Hard");

        showTasksByDifficulty("Easy");
    }

    private boolean login(String username, String password) {
        return username.equals(registeredUsername) && password.equals(registeredPassword);
    }

    private boolean register(String username, String password, String email, String confirmPassword) {
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
        return true;
    }

    private void switchPanel(JPanel panel, String name) {
        CardLayout cl = (CardLayout) (panel.getLayout());
        cl.show(panel, name);
    }

    private void addTask(String taskTitle, String pointsText, String difficulty) {
        JPanel taskCard = new JPanel();
        taskCard.setLayout(new BoxLayout(taskCard, BoxLayout.Y_AXIS));
        taskCard.setBackground(Color.WHITE);
        taskCard.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));

        JLabel taskLabel = new JLabel(taskTitle);
        taskLabel.setFont(new Font("Arial", Font.BOLD, 16));
        taskLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        taskCard.add(taskLabel);

        JLabel taskPointsLabel = new JLabel(pointsText);
        taskPointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        taskPointsLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        taskCard.add(taskPointsLabel);

        JButton completeButton = new JButton("Complete Task");
        completeButton.setBackground(new Color(60, 179, 113));
        completeButton.setForeground(Color.WHITE);
        completeButton.setFocusPainted(false);
        completeButton.setBorder(new EmptyBorder(10, 10, 10, 10));

        completeButton.addActionListener(e -> completeTask(taskCard, pointsText));
        taskCard.add(completeButton);

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
        taskDisplayPanel.removeAll();
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
