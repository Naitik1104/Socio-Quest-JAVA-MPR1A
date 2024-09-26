import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TasksPanel extends JPanel {

    private List<JPanel> easyTasks, mediumTasks, hardTasks;
    private JScrollPane tasksScrollPane;
    private JComboBox<String> difficultyDropdown;
    private JPanel taskDisplayPanel;
    private int points = 0;
    private File uploadedImage = null; // To keep track of the uploaded image

    public TasksPanel(MainAppPanel mainAppPanel) {
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

        // Adding 10 dummy tasks for each difficulty level
        
            addTask("Pick up litter in your local park" , "+10 points", "Easy");
            addTask("Use public transport for a week.", "+20 points", "Easy");
            addTask("Use less than 3 litters of water for bathing/washing/cleaning purposes.", "+20 points", "Easy");
            addTask("Reuse your plastic bag/bottle or any other substance.", "+20 points", "Easy");
            addTask("Educate 5 people about recycling.", "+40 points", "Medium");
            addTask("Donate clothes to a local charity", "+40 points", "Medium");
            addTask("Clean a public area.", "+40 points", "Medium");
            addTask("Plant a tree", "+40 points", "Medium");
            addTask("Use public transport for a month.", "+40 points", "Medium");
            addTask("Start a local environment awareness campaign", "+70 points", "Hard");
            addTask("Set up a compost system in your neighbourhood", "+70 points", "Hard");
            addTask("Organize a charity fundraiser for an eco-cause", "+70 points", "Hard");
            addTask("Organize a community cleanup event", "+70 points", "Hard");
            addTask("Zero waste challenge for a wee", "+70 points", "Hard");
        

        showTasksByDifficulty("Easy");
    }

    private void addTask(String taskTitle, String pointsText, String difficulty) {
        JPanel taskCard = new JPanel();
        taskCard.setLayout(new BoxLayout(taskCard, BoxLayout.Y_AXIS));
        taskCard.setBackground(Color.WHITE);
        taskCard.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        taskCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100)); // Ensure consistent height

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

        completeButton.addActionListener(e -> showImageSubmissionDialog(taskCard, pointsText));
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

    private void showImageSubmissionDialog(JPanel taskCard, String pointsText) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Submit Image", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);

        JLabel instructionLabel = new JLabel("Please submit an image to complete the task:");
        instructionLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        dialog.add(instructionLabel, BorderLayout.NORTH);

        JButton uploadButton = new JButton("Upload Image");
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                uploadedImage = fileChooser.getSelectedFile();
                // Handle the file upload logic here
                JOptionPane.showMessageDialog(this, "Image uploaded: " + uploadedImage.getName());
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(uploadButton);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            if (uploadedImage == null) {
                JOptionPane.showMessageDialog(this, "Please upload an image first!");
            } else {
                completeTask(taskCard, pointsText);
                dialog.dispose();
            }
        });
        buttonPanel.add(submitButton);

        dialog.add(buttonPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void completeTask(JPanel taskCard, String pointsText) {
        int pointsToAdd = Integer.parseInt(pointsText.replaceAll("[^0-9]", ""));
        points += pointsToAdd;
        ((MainAppPanel) getParent().getParent().getParent().getParent()).addPoints(pointsToAdd);
        taskCard.setVisible(false);
        JOptionPane.showMessageDialog(this, "Task completed! Points added: " + pointsToAdd);
    }
}