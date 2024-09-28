import javax.swing.*;
import java.awt.*;

public class REDEEM extends JPanel {

    int points = 1200;

    public REDEEM() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLayout(new BorderLayout());

        jPanel1 = new JPanel();
        jPanel1.setBackground(new Color(240, 248, 255));
        jPanel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        jLabel1 = new JLabel("REDEEM");
        jLabel1.setFont(new Font("Serif", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setForeground(new Color(0, 102, 102));
        
        jPanel1.add(jLabel1);

        JPanel rewardPanel = new JPanel();
        rewardPanel.setLayout(new GridLayout(4, 2, 15, 15));
        rewardPanel.setOpaque(false);

        // Reward Items
        createRewardItem("Collect MUG for earning 500 points", "EARN", 500, rewardPanel);
        createRewardItem("Collect T-SHIRT for earning 1000 points", "EARN", 1000, rewardPanel);
        createRewardItem("Collect Hand-Band for earning 250 points", "EARN", 250, rewardPanel);
        createRewardItem("Collect Flag for earning 100 points", "EARN", 100, rewardPanel);

        add(jLabel1, BorderLayout.NORTH);
        add(rewardPanel, BorderLayout.CENTER);
    }

    private void createRewardItem(String description, String buttonText, int cost, JPanel rewardPanel) {
        JTextPane rewardDescription = new JTextPane();
        rewardDescription.setFont(new Font("Sylfaen", Font.ITALIC, 18));
        rewardDescription.setText(description);
        rewardDescription.setEditable(false);
        rewardDescription.setBackground(new Color(240, 248, 255));
        rewardDescription.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102), 1, true));
        rewardDescription.setPreferredSize(new Dimension(300, 50));
        
        JButton rewardButton = new JButton(buttonText);
        rewardButton.setFont(new Font("SimSun-ExtB", Font.BOLD, 18));
        rewardButton.setBackground(new Color(102, 205, 170));
        rewardButton.setForeground(Color.WHITE);
        rewardButton.addActionListener(evt -> {
            if (points >= cost) {
                points -= cost;
                JOptionPane.showMessageDialog(null, description.split(" ")[1] + " collected! Remaining points: " + points);
            } else {
                JOptionPane.showMessageDialog(null, "Not enough points! You need " + cost + " points to collect this reward.");
            }
        });

        rewardPanel.add(rewardDescription);
        rewardPanel.add(rewardButton);
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("Redeem Rewards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.add(new REDEEM());
        frame.setVisible(true);
    }

    // Variables declaration - do not modify                     
    private JLabel jLabel1;
    private JPanel jPanel1;
    // End of variables declaration                   
}
