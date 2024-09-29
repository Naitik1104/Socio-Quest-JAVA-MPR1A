

import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import java.awt.*;
import java.awt.print.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Certificate implements Printable {

    private String userName;
    private int questquestpoints;
    private String dateOfIssue;

    public Certificate(String userName, int questquestpoints) {
        this.userName = userName;
        this.questquestpoints = questquestpoints;
        this.dateOfIssue = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        g2d.setColor(new Color(240, 255, 255));
        g2d.fillRect(0, 0, (int) pf.getWidth(), (int) pf.getHeight());

        //g2d.setColor(Color.BLACK);
        //g2d.drawRect(50, 50, (int) pf.getWidth() - 100, (int) pf.getHeight() - 100);

        g2d.setFont(new Font("Curlz MT", Font.BOLD, 48));
        g2d.setColor(new Color(0, 51, 102));
        g2d.drawString("Congratulations!", 100, 100);

        g2d.setFont(new Font("Serif", Font.PLAIN, 36));
        g2d.setColor(Color.BLACK);
        g2d.drawString("This Certificate is awarded to:", 100, 250);
        g2d.setFont(new Font("Serif", Font.BOLD, 42));
        g2d.drawString(userName, 100, 320);

        g2d.setFont(new Font("Serif", Font.PLAIN, 36));
        g2d.drawString("Quest questpoints Earned: " + questquestpoints, 100, 400);
        
        g2d.drawString("Date of Issue: " + dateOfIssue, 100, 470);

        g2d.setFont(new Font("Serif", Font.ITALIC, 24));
        g2d.drawString("Keep up the great work and continue your journey!", 100, 550);

        g2d.setFont(new Font("Arial", Font.PLAIN, 70));
        g2d.setColor(new Color(0, 0, 175, 75));  
        g2d.rotate(Math.toRadians(-30), pf.getWidth() / 2, pf.getHeight() / 2); 
        g2d.drawString("Socio-Quest", 200, 300);
        g2d.rotate(Math.toRadians(30), pf.getWidth() / 2, pf.getHeight() / 2); 

        return PAGE_EXISTS;
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

    public static void main(String[] args) {
        String userName = "Naitik Mehta";
        int questquestpoints = 150;

        new Certificate(userName, questquestpoints).printCertificate();
    }
}