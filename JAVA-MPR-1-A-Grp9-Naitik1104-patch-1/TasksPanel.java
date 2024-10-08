import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TasksPanel extends JPanel {

    private List<JPanel> easyTasks, mediumTasks, hardTasks;
    private JScrollPane tasksScrollPane;
    private JComboBox<String> difficultyDropdown;
    private JPanel taskDisplayPanel;
    private JLabel questpointsTrackerLabel;
    private int questpoints = 0;
    
    private MainAppPanel mainAppPanel;
    private List<File> uploadedImages = new ArrayList<>();
    private int requiredImages; 

    public TasksPanel(MainAppPanel mainAppPanel) {
        this.mainAppPanel = mainAppPanel;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 245));

        tasksScrollPane = new JScrollPane();
        tasksScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tasksScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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

        
        addTask("Pick up litter in your local park", "+20 questpoints", "Easy", 1);
        addTask("Use public transport for a week.", "+20 questpoints", "Easy", 4);
        addTask("Use less than 3 liters of water for bathing/washing/cleaning purposes.", "+30 questpoints", "Easy", 1);
        addTask("Feed local animals in your area", "+30 questpoints", "Easy", 1);
        addTask("Reuse your plastic bag/bottle or any other substance.", "+30 questpoints", "Easy", 2);
        addTask("Educate 5 people about recycling", "+40 questpoints", "Medium", 5);
        addTask("Donate clothes to a local charity", "+40 questpoints", "Medium", 1);
        addTask("Host sustainability workshops", "+50 questpoints", "Medium", 2);
        addTask("Clean a public area.", "+50 questpoints", "Medium", 1);
        addTask("Organize a small tree plantation drive in your area", "+60 questpoints", "Medium", 2);
        addTask("Use public transport for a month", "+40 questpoints", "Medium", 1);
        addTask("Start a local environment awareness campaign", "+70 questpoints", "Hard", 2);
        addTask("Set up a compost system in your neighborhood", "+80 questpoints", "Hard", 2);
        addTask("Organize a charity fundraiser for an eco-cause", "+90 questpoints", "Hard", 3);
        addTask("Organize a community cleanup event", "+80 questpoints", "Hard", 1);
        addTask("Zero waste challenge for a week", "+70 questpoints", "Hard", 1);
        addTask("Renewable Energy Initiative(Setup a solar power plant in your neighborhood)", "+150 questpoints", "Hard", 3);

        showTasksByDifficulty("Easy");

        questpointsTrackerLabel = new JLabel("Total questpoints: " + questpoints);
        questpointsTrackerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questpointsTrackerLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(questpointsTrackerLabel, BorderLayout.SOUTH);
    }

    private void addTask(String taskTitle, String questpointsText, String difficulty, int requiredImages) {
        JPanel taskCard = new JPanel();
        taskCard.setLayout(new BoxLayout(taskCard, BoxLayout.Y_AXIS));
        taskCard.setBackground(Color.WHITE);
        taskCard.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        taskCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel taskLabel = new JLabel(taskTitle);
        taskLabel.setFont(new Font("Arial", Font.BOLD, 16));
        taskLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        taskCard.add(taskLabel);

        JLabel taskquestpointsLabel = new JLabel(questpointsText);
        taskquestpointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        taskquestpointsLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        taskCard.add(taskquestpointsLabel);

        JButton completeButton = new JButton("Complete Task");
        completeButton.setBackground(new Color(60, 179, 113));
        completeButton.setForeground(Color.WHITE);
        completeButton.setFocusPainted(false);
        completeButton.setBorder(new EmptyBorder(10, 10, 10, 10));

        completeButton.addActionListener(e -> showImageSubmissionDialog(taskCard, questpointsText, requiredImages));
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

    private void showImageSubmissionDialog(JPanel taskCard, String questpointsText, int requiredImages) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Submit Image", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        JLabel instructionLabel = new JLabel("Please submit " + requiredImages + " images to complete the task:");
        instructionLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        dialog.add(instructionLabel, BorderLayout.NORTH);

        JButton uploadButton = new JButton("Upload Image");
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true); 
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File[] selectedFiles = fileChooser.getSelectedFiles();
                for (File file : selectedFiles) {
                    uploadedImages.add(file);
                    JOptionPane.showMessageDialog(this, "Image uploaded: " + file.getName());
                }
                
                if (uploadedImages.size() > requiredImages) {
                    JOptionPane.showMessageDialog(this, "You have uploaded more images than required. Please upload only " + requiredImages + " images.");
                    uploadedImages.clear(); 
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(uploadButton);
        dialog.add(buttonPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            if (uploadedImages.size() == requiredImages) {
                int questpointsToAdd = Integer.parseInt(questpointsText.replaceAll("[^0-9]", ""));
                mainAppPanel.addquestpoints(questpointsToAdd);
                questpoints += questpointsToAdd;
                questpointsTrackerLabel.setText("Total questpoints: " + questpoints);
                saveImagesToDatabase(taskCard, questpointsText); 
                taskCard.setEnabled(false);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please upload exactly " + requiredImages + " images before submitting.");
            }
        });

        buttonPanel.add(submitButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void saveImagesToDatabase(JPanel taskCard, String questpointsText) {
    try (Connection conn = DatabaseConnection.getConnection()) {
        String task = ((JLabel) taskCard.getComponent(0)).getText();
        int userID = mainAppPanel.getUserID();  
        String username = mainAppPanel.getUsername();  

        
        String sql = "INSERT INTO imagedata (userID, username, task, image) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (File imageFile : uploadedImages) {
                byte[] imageBytes = new byte[(int) imageFile.length()];
                try (FileInputStream fis = new FileInputStream(imageFile)) {
                    fis.read(imageBytes);
                }
                pstmt.setInt(1, userID);
                pstmt.setString(2, username);
                pstmt.setString(3, task);
                pstmt.setBytes(4, imageBytes);
                pstmt.addBatch(); 
            }
            pstmt.executeBatch(); 
            JOptionPane.showMessageDialog(this, "Images submitted successfully!");
        }
    } catch (SQLException | IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving images: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    public void addQuestpoints(int points) {
        questpoints += points;
        questpointsTrackerLabel.setText("Total questpoints: " + questpoints);
    }
}
