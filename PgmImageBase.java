import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author andreis
 */

public abstract class PgmImageBase implements PgmImage {

    protected byte mImageData[][];
    protected int mWidth;
    protected int mHeight;

    @Override
    public ImageIcon toImageIcon() {
        BufferedImage image = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_BYTE_GRAY);

        for (int line = 0; line < mHeight; line++) {
            image.getRaster().setDataElements(0, line, mWidth, 1, mImageData[line]);
        }
        return new ImageIcon(image);
    }

    @Override
    public Dimension getImageSize() {
        return new Dimension(mWidth, mHeight);
    }

    @Override
    public void applyLowPassFilter(int size) {

    }
}
