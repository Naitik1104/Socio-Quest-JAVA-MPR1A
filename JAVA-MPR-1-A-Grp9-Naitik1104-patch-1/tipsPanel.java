import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class tipsPanel extends JPanel {

    private JLabel tipLabel;
    private JButton nextButton, prevButton;
    private String[] tips;
    private int currentTipIndex;

    public tipsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(204, 255, 204));

        tips = new String[]{
            "Tip 1: Save water by taking shorter showers.",
            "Tip 2: Turn off lights when not in use.",
            "Tip 3: Recycle paper, plastic, and glass.",
            "Tip 4: Use reusable bags instead of plastic bags.",
            "Tip 5: Plant a tree to help the environment."
        };
        currentTipIndex = 0;

        tipLabel = new JLabel(tips[currentTipIndex], SwingConstants.CENTER);
        tipLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tipLabel.setForeground(new Color(34, 139, 34));
        tipLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(tipLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 255, 255));

        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");

        styleButton(prevButton);
        styleButton(nextButton);

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPreviousTip();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextTip();
            }
        });

        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);

        updateButtonState();
    }

    private void showNextTip() {
        if (currentTipIndex < tips.length - 1) {
            currentTipIndex++;
            tipLabel.setText(tips[currentTipIndex]);
            updateButtonState();
        }
    }

    private void showPreviousTip() {
        if (currentTipIndex > 0) {
            currentTipIndex--;
            tipLabel.setText(tips[currentTipIndex]);
            updateButtonState();
        }
    }

    private void updateButtonState() {
        prevButton.setEnabled(currentTipIndex > 0);
        nextButton.setEnabled(currentTipIndex < tips.length - 1);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(60, 179, 113));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(34, 139, 34), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
    }
}
