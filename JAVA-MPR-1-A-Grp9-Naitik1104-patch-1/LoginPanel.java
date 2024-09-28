import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPanel extends JPanel {

    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;

    public LoginPanel(MprLayout mainFrame) {
        setLayout(new BorderLayout());

        // Custom Image Box - Big Blue Box
        JPanel imageBoxPanel = new JPanel();
        imageBoxPanel.setBackground(Color.BLUE);
        imageBoxPanel.setPreferredSize(new Dimension(200, 200)); // You can add your custom image here
        add(imageBoxPanel, BorderLayout.NORTH);

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
            if (mainFrame.login(username, password)) {
                mainFrame.switchPanel("MainApp");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login. Please try again or register.");
            }
        });

        JButton goToRegisterButton = new JButton("Not registered? Register here");
        goToRegisterButton.setBackground(Color.LIGHT_GRAY);
        goToRegisterButton.setForeground(Color.BLACK);

        goToRegisterButton.addActionListener(e -> {
            mainFrame.switchPanel("Register");
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
        add(paddedFields, BorderLayout.CENTER);
    }
}