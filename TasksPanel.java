import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class TasksPanel extends JPanel {
    
    private List<JPanel> easyTasks, mediumTasks, hardTasks;
    private JScrollPane tasksScrollPane;
    private JComboBox<String> difficultyDropdown;
    private JPanel taskDisplayPanel;
    private JLabel questpointsTrackerLabel;
    private int questpoints = 0;

    private MainAppPanel mainAppPanel;
    private Map<String, List<File>> taskImageMap = new HashMap<>(); 
    private Map<String, JProgressBar> progressBarMap = new HashMap<>();

    public TasksPanel(MainAppPanel mainAppPanel) {
        this.mainAppPanel = mainAppPanel;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 245));

        tasksScrollPane = new JScrollPane();
        tasksScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tasksScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        taskDisplayPanel = new JPanel();
        taskDisplayPanel.setLayout(new BoxLayout(taskDisplayPanel, BoxLayout.Y_AXIS));
        tasksScrollPane.setViewportView(taskDisplayPanel);
        add(tasksScrollPane, BorderLayout.CENTER);

        JPanel taskFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taskFilterPanel.setBackground(new Color(220, 220, 220));
        JLabel filterLabel = new JLabel("Filter by Difficulty:");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 14));
        taskFilterPanel.add(filterLabel);

        difficultyDropdown = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        difficultyDropdown.addActionListener(e -> showTasksByDifficulty((String) difficultyDropdown.getSelectedItem()));
        taskFilterPanel.add(difficultyDropdown);
        add(taskFilterPanel, BorderLayout.NORTH);

        easyTasks = new ArrayList<>();
        mediumTasks = new ArrayList<>();
        hardTasks = new ArrayList<>();

        // Add tasks with descriptions
        addTask("Pick up litter in your local park", "+20 questpoints", "Easy", 1, 
                new String[]{"Image 1: Show the cleaned park area."});
        addTask("Use public transport for a week.", "+20 questpoints", "Easy", 4,
                new String[]{"Image 1-4: Daily transport tickets or passes."});
        addTask("Use less than 3 liters of water for bathing/washing/cleaning.", "+30 questpoints", "Easy", 1,
                new String[]{"Image 1: Evidence of restricted water usage."});
        addTask("Feed local animals in your area", "+30 questpoints", "Easy", 1,
                new String[]{"Image 1: Feeding stray animals."});
        addTask("Reuse your plastic bag/bottle.", "+30 questpoints", "Easy", 2,
                new String[]{"Image 1: Before reuse", "Image 2: After reuse"});
        addTask("Educate 5 people about recycling.", "+40 questpoints", "Medium", 5,
                new String[]{"Image 1-5: Pictures of the awareness session."});
        addTask("Donate clothes to a local charity", "+40 questpoints", "Medium", 1,
                new String[]{"Image 1: Clothes packed and ready for donation."});
        addTask("Host sustainability workshops", "+50 questpoints", "Medium", 2,
                new String[]{"Image 1: Workshop setup", "Image 2: Workshop in progress."});
        addTask("Clean a public area.", "+50 questpoints", "Medium", 1,
                new String[]{"Image 1: Public area after cleaning."});
        addTask("Organize a tree plantation drive.", "+60 questpoints", "Medium", 2,
                new String[]{"Image 1: Planting trees", "Image 2: Final setup."});
        addTask("Use public transport for a month.", "+40 questpoints", "Medium", 1,
                new String[]{"Image 1: Summary of monthly tickets."});
        addTask("Start a local environment awareness campaign.", "+70 questpoints", "Hard", 2,
                new String[]{"Image 1-2: Campaign highlights."});
        addTask("Set up a compost system.", "+80 questpoints", "Hard", 2,
                new String[]{"Image 1: Compost setup", "Image 2: Working compost system."});
        addTask("Organize a charity fundraiser.", "+90 questpoints", "Hard", 3,
                new String[]{"Image 1-3: Fundraising event highlights."});
        addTask("Organize a community cleanup event.", "+80 questpoints", "Hard", 1,
                new String[]{"Image 1: Before and after cleanup comparison."});
        addTask("Zero waste challenge for a week.", "+70 questpoints", "Hard", 1,
                new String[]{"Image 1: Evidence of zero-waste practices."});
        addTask("Renewable Energy Initiative.", "+150 questpoints", "Hard", 3,
                new String[]{"Image 1-3: Solar power system setup."});

        showTasksByDifficulty("Easy");

        questpointsTrackerLabel = new JLabel("Total questpoints: " + questpoints);
        questpointsTrackerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questpointsTrackerLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(questpointsTrackerLabel, BorderLayout.SOUTH);
    }

    private void addTask(String taskTitle, String questpointsText, String difficulty, int requiredImages, String[] descriptions) {
    int points = Integer.parseInt(questpointsText.replaceAll("\\D+", ""));  // Extract points from text

    JPanel taskCard = new JPanel();
    taskCard.setLayout(new BoxLayout(taskCard, BoxLayout.Y_AXIS));
    taskCard.setBackground(Color.WHITE);
    taskCard.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
    taskCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

    JLabel taskLabel = new JLabel(taskTitle);
    taskLabel.setFont(new Font("Arial", Font.BOLD, 16));
    taskLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
    taskCard.add(taskLabel);

    JLabel questpointsLabel = new JLabel(questpointsText);
    questpointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    taskCard.add(questpointsLabel);

    JProgressBar progressBar = new JProgressBar(0, requiredImages);
    progressBar.setValue(loadUploadedImagesCount(taskTitle));
    progressBar.setStringPainted(true);
    progressBar.setPreferredSize(new Dimension(150, 20));
    progressBarMap.put(taskTitle, progressBar);
    taskCard.add(progressBar);

    JButton completeButton = new JButton("Complete Task");
    completeButton.setBackground(new Color(0, 128, 0));
    completeButton.setForeground(Color.WHITE);
    completeButton.setFocusPainted(false);
    completeButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    completeButton.addActionListener(e -> showImageSubmissionDialog(taskTitle, requiredImages, descriptions, points));
    taskCard.add(completeButton);

    switch (difficulty) {
        case "Easy": easyTasks.add(taskCard); break;
        case "Medium": mediumTasks.add(taskCard); break;
        case "Hard": hardTasks.add(taskCard); break;
    }
}


    private void showTasksByDifficulty(String difficulty) {
        taskDisplayPanel.removeAll();
        List<JPanel> tasks = switch (difficulty) {
            case "Easy" -> easyTasks;
            case "Medium" -> mediumTasks;
            case "Hard" -> hardTasks;
            default -> new ArrayList<>();
        };
        tasks.forEach(taskDisplayPanel::add);
        taskDisplayPanel.revalidate();
        taskDisplayPanel.repaint();
    }

    private void showImageSubmissionDialog(String taskTitle, int requiredImages, String[] descriptions, int points) {
    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Submit Images", true);
    dialog.setLayout(new BorderLayout());
    dialog.setSize(400, 300);
    dialog.setLocationRelativeTo(this);

    JTextArea descriptionArea = new JTextArea(String.join("\n", descriptions));
    descriptionArea.setEditable(false);
    dialog.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

    JButton uploadButton = new JButton("Upload Images");
    uploadButton.addActionListener(e -> {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            saveImagesToDatabase(taskTitle, files);
            updateProgressBar(taskTitle, files.length, requiredImages, points);
        }
    });

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(uploadButton);
    dialog.add(buttonPanel, BorderLayout.SOUTH);
    dialog.setVisible(true);
}


        

    private void saveImagesToDatabase(String taskTitle, File[] files) {
        try (Connection conn = DatabaseConnection.getConnection()) {
			String username = mainAppPanel.getUsername(); 
            String sql = "INSERT INTO imagedata (username,task, image) VALUES (?,?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (File file : files) {
					pstmt.setString(1,username);
                    pstmt.setString(2, taskTitle);
                    pstmt.setBytes(3, new FileInputStream(file).readAllBytes());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private int loadUploadedImagesCount(String taskTitle) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM imagedata WHERE task = ?")) {
            pstmt.setString(1, taskTitle);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void updateProgressBar(String taskTitle, int uploadedCount, int requiredImages, int points) {
        int progress = (uploadedCount * 100) / requiredImages;
		JProgressBar progressBar = progressBarMap.get(taskTitle);
        progressBar.setValue(progressBar.getValue() + uploadedCount);
		if (progress >= 100) {
        
        addQuestpoints(points); 
    }
    }

    public void addQuestpoints(int points) {
        questpoints += points;
        questpointsTrackerLabel.setText("Total questpoints: " + questpoints);
		mainAppPanel.addquestpoints(points); 
    }
}
