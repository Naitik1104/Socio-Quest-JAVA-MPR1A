import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaderboardPanel extends JPanel {
    private JTable leaderboardTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private String loggedInUsername;  

    public LeaderboardPanel(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;  

        setLayout(new BorderLayout());
        setBackground(new Color(34, 49, 40)); 
        setBorder(new EmptyBorder(20, 20, 20, 20));

        
        JLabel titleLabel = new JLabel("🏆 Live Leaderboard 🏆");
        titleLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 30)); // Font supporting emojis
        titleLabel.setForeground(new Color(200, 255, 200)); 
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        
        String[] columnNames = {"Rank", "Username", "Quest Points"};
        tableModel = new DefaultTableModel(columnNames, 0);
        leaderboardTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);

                String username = (String) getValueAt(row, 1);  // Get the username from the row

                
                if (username.equals(loggedInUsername)) {
                    comp.setBackground(new Color(255, 102, 102));  
                } 
                
                else if (!isRowSelected(row)) {
                    if (row == 2) comp.setBackground(new Color(144, 255, 144));  
                    else if (row == 1) comp.setBackground(new Color(173, 255, 47));  
                    else if (row == 0) comp.setBackground(new Color(124, 252, 0));  
                    else comp.setBackground(row % 2 == 0 ? new Color(245, 255, 245) : Color.WHITE);
                }

                comp.setFont(new Font("Arial", Font.PLAIN, 18));
                return comp;
            }
        };

        leaderboardTable.setRowHeight(40);  // Increased row height
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 18));
        leaderboardTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        leaderboardTable.getTableHeader().setBackground(new Color(34, 49, 40)); // Header background (dark green)
        leaderboardTable.getTableHeader().setForeground(Color.WHITE); // Header text color
        leaderboardTable.setGridColor(new Color(200, 255, 200)); // Light green grid color

        leaderboardTable.getColumnModel().getColumn(0).setPreferredWidth(25);

        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        refreshButton = new JButton("🔃 Refresh");
        refreshButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18)); // Emoji-support font
        refreshButton.setBackground(new Color(34, 139, 34)); // Forest green
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        refreshButton.addActionListener(e -> updateLeaderboard());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(34, 49, 40)); // Dark green background
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        updateLeaderboard();
    }

    private void updateLeaderboard() {
        tableModel.setRowCount(0);  // Clear existing data

        try (Connection conn = DatabaseConnection.getConnection()) {
            String selectSQL = "SELECT username, questpoints FROM userinfo ORDER BY questpoints DESC";
            String updateSQL = "UPDATE userinfo SET leaderboard_rank = ? WHERE username = ?";

            try (PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
                 PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
                 ResultSet rs = selectStmt.executeQuery()) {

                int rank = 1;  
                while (rs.next()) {
                    String username = rs.getString("username");
                    int questpoints = rs.getInt("questpoints");

                    // Add row to the table
                    tableModel.addRow(new Object[]{rank, username, questpoints});

                    
                    updateStmt.setInt(1, rank);
                    updateStmt.setString(2, username);
                    updateStmt.executeUpdate();  
                    rank++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading leaderboard: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
