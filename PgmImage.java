import java.awt.Dimension;
import javax.swing.ImageIcon;

/**
 *
 * @author andreis
 */

public interface PgmImage {

    /**
     * @return un obiect de tip ImageIcon
     */
    ImageIcon toImageIcon();

    /**
     * @return dimensiunea imaginii.
     */
    Dimension getImageSize();

    /**
     * @param size Dimensiunea ferestrei filtrului.
     */
    void applyLowPassFilter(int size);
}