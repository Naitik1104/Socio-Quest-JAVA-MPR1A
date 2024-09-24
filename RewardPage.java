import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RewardPage extends JFrame {

    private JLabel titleLabel, pointsLabel, rewardLabel;
    private JButton claimButton;
    private JPanel certificatePanel;
    private int userPoints;

    public RewardPage() {
        this.userPoints = 300;

        setTitle("Reward Page");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());  
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);  

        titleLabel = new JLabel("User Reward Page", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(34, 139, 34));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        pointsLabel = new JLabel("You have: " + userPoints + " points", SwingConstants.CENTER);
        pointsLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        pointsLabel.setForeground(new Color(70, 130, 180));
        gbc.gridy = 1;
        add(pointsLabel, gbc);

        rewardLabel = new JLabel("", SwingConstants.CENTER);
        rewardLabel.setFont(new Font("Arial", Font.BOLD, 20));
        rewardLabel.setForeground(new Color(255, 69, 0));
        gbc.gridy = 2;
        add(rewardLabel, gbc);

        
        certificatePanel = new JPanel(new BorderLayout());
        certificatePanel.setPreferredSize(new Dimension(350, 200));  // Manual size respected now
        certificatePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        certificatePanel.setBackground(new Color(255, 255, 200));

        JLabel congratulationsLabel = new JLabel("Congratulations", SwingConstants.CENTER);
        congratulationsLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 24));
        congratulationsLabel.setForeground(new Color(128, 0, 128));
        certificatePanel.add(congratulationsLabel, BorderLayout.NORTH);

        JLabel recipientName = new JLabel("Recipient: [User Name]", SwingConstants.CENTER);
        recipientName.setFont(new Font("Arial", Font.PLAIN, 16));
        certificatePanel.add(recipientName, BorderLayout.CENTER);

        JLabel pointsEarned = new JLabel("Points Earned: " + userPoints, SwingConstants.CENTER);
        pointsEarned.setFont(new Font("Arial", Font.PLAIN, 16));
        certificatePanel.add(pointsEarned, BorderLayout.SOUTH);

        JLabel dateIssued = new JLabel("Date Issued: " + getCurrentDate(), SwingConstants.CENTER);
        dateIssued.setFont(new Font("Arial", Font.PLAIN, 14));
        certificatePanel.add(dateIssued, BorderLayout.SOUTH);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(certificatePanel, gbc);

        claimButton = new JButton("Claim Your Reward");
        claimButton.setFont(new Font("Arial", Font.BOLD, 14));
        claimButton.setBackground(new Color(50, 205, 50));
        claimButton.setForeground(Color.WHITE);
        claimButton.setFocusPainted(false);
        claimButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        claimButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        claimButton.setPreferredSize(new Dimension(150, 40));

        claimButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkReward(pointsEarned, recipientName);
            }
        });

        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(claimButton, gbc);

        setVisible(true);
    }

    private String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        return formatter.format(new Date());
    }

    private void checkReward(JLabel pointsEarned, JLabel recipientName) {
        if (userPoints >= 250 && userPoints < 300) {
            rewardLabel.setText("Congratulations! You've unlocked a 250 Points Reward!");
            pointsEarned.setText("Points Earned: 250");
            recipientName.setText("Recipient: [User Name]");
        } else if (userPoints >= 300 && userPoints < 350) {
            rewardLabel.setText("Awesome! You've unlocked a 300 Points Reward!");
            pointsEarned.setText("Points Earned: 300");
            recipientName.setText("Recipient: [User Name]");
        } else if (userPoints >= 350 && userPoints < 400) {
            rewardLabel.setText("Great! You've unlocked a 350 Points Reward!");
            pointsEarned.setText("Points Earned: 350");
            recipientName.setText("Recipient: [User Name]");
        } else if (userPoints >= 400 && userPoints < 450) {
            rewardLabel.setText("Fantastic! You've unlocked a 400 Points Reward!");
            pointsEarned.setText("Points Earned: 400");
            recipientName.setText("Recipient: [User Name]");
        } else if (userPoints >= 450 && userPoints < 500) {
            rewardLabel.setText("You're a Champion! You've unlocked a 450 Points Reward!");
            pointsEarned.setText("Points Earned: 450");
            recipientName.setText("Recipient: [User Name]");
        } else if (userPoints >= 500) {
            rewardLabel.setText("Legendary! You've unlocked the 500 Points Reward!");
            pointsEarned.setText("Points Earned: 500");
            recipientName.setText("Recipient: [User Name]");
        } else {
            rewardLabel.setText("Keep going! You need more points to unlock a reward.");
            pointsEarned.setText("Points Earned: 0");
            recipientName.setText("Recipient: [User Name]");
        }
    }

    public static void main(String[] args) {
        new RewardPage();
    }
}
