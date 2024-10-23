import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterPanel extends JPanel {

    private JTextField registerUsernameField;
    private JPasswordField registerPasswordField;
    private JPasswordField registerConfirmPasswordField;
    private JTextField registerEmailField;

    public RegisterPanel(MprLayout mainFrame) {
        setLayout(new BorderLayout());

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

            if (mainFrame.register(username, password, email, confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Registration successful! Please log in.");
                mainFrame.switchPanel("Login");
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
        add(paddedFields, BorderLayout.CENTER);
    }
}