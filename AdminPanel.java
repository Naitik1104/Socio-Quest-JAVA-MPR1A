import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.*;

public class AdminPanel extends JPanel {
    private JTable userInfoTable, imageDataTable;
    private DefaultTableModel userInfoModel, imageDataModel;
    private JTabbedPane tabbedPane;

    public AdminPanel() {
        setLayout(new BorderLayout());

        
        JLabel headerLabel = new JLabel("<html><h1 style='text-align:center;color:#2E8B57;'>Admin Dashboard</h1></html>", JLabel.CENTER);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(224, 255, 255)); // Light cyan background
        add(headerLabel, BorderLayout.NORTH);

        
        userInfoModel = new DefaultTableModel();
        imageDataModel = new DefaultTableModel();

        
        userInfoTable = new JTable(userInfoModel);
        imageDataTable = new JTable(imageDataModel);
        styleTable(userInfoTable);
        styleTable(imageDataTable);

        
        imageDataTable.setDefaultRenderer(ImageIcon.class, new ImageRenderer());

        
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("<html><b>User Info</b></html>", new JScrollPane(userInfoTable));
        tabbedPane.addTab("<html><b>Image Data</b></html>", new JScrollPane(imageDataTable));
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 18));
        tabbedPane.setBackground(new Color(245, 245, 245)); // Light gray background

        // Add tabbed pane to the center of the panel
        add(tabbedPane, BorderLayout.CENTER);

        // Load data from the database
        loadUserInfo();
        loadImageData();
    }

    // Method to load user info from the database
    private void loadUserInfo() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM userinfo";
            ResultSet resultSet = statement.executeQuery(query);
            setTableColumns(userInfoModel, resultSet);

            while (resultSet.next()) {
                Object[] rowData = new Object[userInfoModel.getColumnCount()];
                for (int i = 0; i < userInfoModel.getColumnCount(); i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                userInfoModel.addRow(rowData);
            }
        } catch (SQLException e) {
            showError("Error loading user info: " + e.getMessage());
        }
    }

    // Method to load image data with BLOB handling
    private void loadImageData() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM imagedata";
            ResultSet resultSet = statement.executeQuery(query);
            setTableColumns(imageDataModel, resultSet);

            while (resultSet.next()) {
                Object[] rowData = new Object[imageDataModel.getColumnCount()];
                for (int i = 0; i < imageDataModel.getColumnCount(); i++) {
                    if (resultSet.getMetaData().getColumnType(i + 1) == Types.BLOB) {
                        Blob blob = resultSet.getBlob(i + 1);
                        byte[] blobBytes = blob.getBytes(1, (int) blob.length());
                        ImageIcon imageIcon = new ImageIcon(blobBytes);
                        Image scaledImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        rowData[i] = new ImageIcon(scaledImage);
                    } else {
                        rowData[i] = resultSet.getObject(i + 1);
                    }
                }
                imageDataModel.addRow(rowData);
            }
        } catch (SQLException e) {
            showError("Error loading image data: " + e.getMessage());
        }
    }

    
    private void setTableColumns(DefaultTableModel model, ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columns = new String[columnCount];

        for (int i = 1; i <= columnCount; i++) {
            columns[i - 1] = metaData.getColumnName(i);
        }
        model.setColumnIdentifiers(columns);
    }

    
    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(100);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.getTableHeader().setBackground(new Color(0, 102, 153)); // Dark blue background
        table.getTableHeader().setForeground(Color.WHITE);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
    }

   
    private static class ImageRenderer extends JLabel implements TableCellRenderer {
        public ImageRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof ImageIcon) {
                setIcon((ImageIcon) value);
            } else {
                setText(value != null ? value.toString() : "");
            }
            return this;
        }
    }

    // Helper method to show error messages in a dialog
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Main method to test the AdminPanel UI
    public static void main(String[] args) {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new AdminPanel());
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }
}
