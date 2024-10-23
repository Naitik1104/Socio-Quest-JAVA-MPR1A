
import javax.swing.*;
import java.awt.*;

public class Rewards1 extends JPanel {

    /**
     * Creates new form Rewards1
     */
    public Rewards1() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {


        jPanel1 = new JPanel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jPanel2 = new JPanel();
        jLabel2 = new JLabel();

        jPanel1.setBackground(new Color(240, 248, 255)); // Light blue background for main panel
        jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 3, true)); // Adding rounded border with blue color

        jButton1.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        jButton1.setText("REDEEM");
        jButton1.setBackground(new Color(50, 205, 50)); // Green background
        jButton1.setForeground(Color.WHITE); // White text
        jButton1.setFocusPainted(false); // Remove focus border
        jButton1.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2, true)); // Rounded border

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        jButton2.setText("PRINT CERTIFICATE");
        jButton2.setBackground(new Color(255, 140, 0)); // Orange background
        jButton2.setForeground(Color.WHITE); // White text
        jButton2.setFocusPainted(false); // Remove focus border
        jButton2.setBorder(BorderFactory.createLineBorder(new Color(255, 69, 0), 2, true)); // Rounded border

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new Color(255, 255, 224)); // Light yellow background for the title panel
        jPanel2.setBorder(BorderFactory.createDashedBorder(new Color(218, 165, 32), 3, 5)); // Dashed border

        jLabel2.setBackground(new Color(255, 204, 204));
        jLabel2.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 24)); // Larger and bold italic font
        jLabel2.setForeground(new Color(128, 0, 0)); // Maroon text
        jLabel2.setText("REWARDS");

        jLabel2.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jLabel2ComponentAdded(evt);
            }
        });

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(jButton2)
                .addGap(58, 58, 58))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(176, 176, 176)
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(149, Short.MAX_VALUE))
        );

        this.setLayout(new BorderLayout());
        this.add(jPanel1, BorderLayout.CENTER);
    }// </editor-fold>                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String userName = "nm" ;
        int questpoints = 270;
    
        Certificate certificate = new Certificate(userName);
        certificate.printCertificate();
    }                                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        System.out.println("Register button clicked");
        REDEEM register = new REDEEM();
        JFrame redeemFrame = new JFrame();
        redeemFrame.add(register);
        redeemFrame.pack();
        redeemFrame.setLocationRelativeTo(null);
        redeemFrame.setVisible(true);
    }                                        

    private void jLabel2ComponentAdded(java.awt.event.ContainerEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the frame */
        JFrame frame = new JFrame("Rewards1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Rewards1());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Variables declaration - do not modify                     
    private JButton jButton1;
    private JButton jButton2;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    // End of variables declaration                   
}
