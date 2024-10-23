
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import java.awt.*;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Certificate implements Printable {

    private String username;
    private int questpoints;
    private String dateOfIssue;
    private Image signature;
    private Image logo;

    public Certificate(String username) {
        this.username = username;
        this.questpoints = getQuestpointsFromDatabase(username);
        this.dateOfIssue = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
        
        System.out.println("Username: " + this.username);
        System.out.println("Quest Points: " + this.questpoints);
		//System.out.println(new File("signmpr.png").getAbsolutePath());


        loadImages();
    }

    // Load images for signature and logo
    private void loadImages() {
        try {
            this.signature = ImageIO.read(new File("signmpr.png"));
            this.logo = ImageIO.read(new File("logo.png"));

            
            if (this.signature == null) {
                System.out.println("Error: Signature image could not be loaded.");
            } else {
                System.out.println("Signature image loaded successfully.");
            }

            if (this.logo == null) {
                System.out.println("Error: Logo image could not be loaded.");
            } else {
                System.out.println("Logo image loaded successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE; // Only one page
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        drawCertificateBackground(g2d, pf);
        drawCertificateContent(g2d, pf);
        drawImages(g2d, pf);

        return PAGE_EXISTS;
    }

    // Draw the background of the certificate
    private void drawCertificateBackground(Graphics2D g2d, PageFormat pf) {
        g2d.setColor(new Color(240, 255, 255)); // Light background color
        g2d.fillRect(0, 0, (int) pf.getWidth(), (int) pf.getHeight());
    }

    // Draw the text content of the certificate
    private void drawCertificateContent(Graphics2D g2d, PageFormat pf) {
        // Title
        g2d.setFont(new Font("Curlz MT", Font.BOLD, 48));
        g2d.setColor(new Color(0, 51, 102));
        g2d.drawString("Congratulations!", 100, 100);

        // Main content
        g2d.setFont(new Font("Serif", Font.PLAIN, 36));
        g2d.setColor(Color.BLACK);
        g2d.drawString("This Certificate is awarded to:", 100, 250);

        g2d.setFont(new Font("Serif", Font.BOLD, 42));
        g2d.drawString(this.username, 100, 320);

        g2d.setFont(new Font("Serif", Font.PLAIN, 36));
        g2d.drawString("Quest Points Earned: " + this.questpoints, 100, 400);
        g2d.drawString("Date of Issue: " + dateOfIssue, 100, 470);

        // Encouragement text
        g2d.setFont(new Font("Serif", Font.ITALIC, 24));
        g2d.drawString("Keep up the great work and continue your journey!", 100, 550);

        // Watermark
        drawWatermark(g2d, pf);
    }

    // Draw watermark text
    private void drawWatermark(Graphics2D g2d, PageFormat pf) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 70));
        g2d.setColor(new Color(0, 0, 175, 75));
        g2d.rotate(Math.toRadians(-30), pf.getWidth() / 2, pf.getHeight() / 2);
        g2d.drawString("Socio-Quest", 200, 300);
        g2d.rotate(Math.toRadians(30), pf.getWidth() / 2, pf.getHeight() / 2);
    }

    // Draw images for signature and logo
    private void drawImages(Graphics2D g2d, PageFormat pf) {
        drawLogo(g2d, pf);
        drawSignature(g2d, pf);
    }

    // Draw logo image
    private void drawLogo(Graphics2D g2d, PageFormat pf) {
        if (logo != null) {
            int logoWidth = 100;
            int logoHeight = 100;
            int x = 60;
            int y = (int) pf.getHeight() - logoHeight - 75;
            g2d.drawImage(logo, x, y, logoWidth, logoHeight, null);
        }
    }

    // Draw signature image with label
    private void drawSignature(Graphics2D g2d, PageFormat pf) {
        if (signature != null) {
            int signatureWidth = 100;
            int signatureHeight = 100;
            int x = (int) pf.getWidth() - signatureWidth - 100;
            int y = Math.max(0, (int) pf.getHeight() - signatureHeight - 100);

            g2d.drawImage(signature, x, y, signatureWidth, signatureHeight, null);

            // Label under the signature
            g2d.setFont(new Font("Serif", Font.PLAIN, 11));
            g2d.setColor(Color.BLACK);
            g2d.drawString("Signature of Incharge Authority", x, y + signatureHeight + 20);
        }
    }

    
    public void printCertificate() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);

        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }

    
    private int getQuestpointsFromDatabase(String username) {
        int points = 0;

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT questpoints FROM userinfo WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        points = rs.getInt("questpoints");
                    } else {
                        System.out.println("Username not found in the database: " + username);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return points;
    }

    public static void main(String[] args) {
        
        String loggedInUsername = "ab"; 

        
        Certificate certificate = new Certificate(loggedInUsername);
        certificate.printCertificate();
    }
}
