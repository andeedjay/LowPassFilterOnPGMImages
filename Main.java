import java.awt.FlowLayout;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import seriaf.poo.tema2.images.PgmImage;
import seriaf.poo.tema2.images.PgmImageImpl;

/**
 *
 * @author andreis
 */

public class Main {

    public static void main(String[] args) {
        try {
            PgmImage image = new PgmImageImpl("input.pgm");
            JFrame frame = new JFrame("Image Display");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            frame.add(new JLabel(image.toImageIcon()));

            image.applyLowPassFilter(7);
            frame.add(new JLabel(image.toImageIcon()));

            frame.pack();
            frame.setVisible(true);
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}