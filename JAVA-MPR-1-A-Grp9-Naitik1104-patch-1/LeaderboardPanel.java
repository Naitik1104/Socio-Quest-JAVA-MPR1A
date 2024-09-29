import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardPanel extends JPanel {

    public LeaderboardPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Leaderboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Sample data
        List<User> users = new ArrayList<>();
        users.add(new User("Alice", 150));
        users.add(new User("Bob", 120));
        users.add(new User("Charlie", 100));
        users.add(new User("David", 90));
        users.add(new User("Eve", 80));

        JPanel leaderboardContent = new JPanel();
        leaderboardContent.setLayout(new BoxLayout(leaderboardContent, BoxLayout.Y_AXIS));
        leaderboardContent.setBackground(new Color(245, 245, 245));

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            JPanel userPanel = new JPanel(new BorderLayout());
            userPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            userPanel.setBackground(i < 3 ? getTopUserColor(i) : Color.WHITE);

            JLabel userLabel = new JLabel((i + 1) + ". " + user.getName() + " - " + user.getPoints() + " points");
            userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            userPanel.add(userLabel, BorderLayout.CENTER);

            leaderboardContent.add(userPanel);
        }

        JScrollPane scrollPane = new JScrollPane(leaderboardContent);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }

    private Color getTopUserColor(int rank) {
        switch (rank) {
            case 0:
                return new Color(255, 215, 0); // Gold
            case 1:
                return new Color(192, 192, 192); // Silver
            case 2:
                return new Color(205, 127, 50); // Bronze
            default:
                return Color.WHITE;
        }
    }

    private static class User {
        private final String name;
        private final int points;

        public User(String name, int points) {
            this.name = name;
            this.points = points;
        }

        public String getName() {
            return name;
        }

        public int getPoints() {
            return points;
        }
    }
}