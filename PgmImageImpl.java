import java.io.File;
import java.util.Scanner;
import java.io.IOException;

/**
 *
 * @author andreis
 */

public class PgmImageImpl extends PgmImageBase implements PgmImage {

	private int maxValue;
	private int rows;
	private int cols;
	private byte[][] bordedMatrix;

	public PgmImageImpl(String fileName) throws IOException, IllegalArgumentException {
		File file = new File(fileName);
		if (!file.isFile()) {
			throw new IOException("Fisierul specificat nu a fost gasit");
		}
		else {
			Scanner scan = new Scanner(file);
			String format = scan.nextLine();
			if (!format.equals("P2")) {
				throw new IllegalArgumentException("Formatul fisierului nu este de tip PGM ASCII");
			}
			else {
				scan.nextLine();
				mWidth = scan.nextInt();
				mHeight = scan.nextInt();
				mImageData = new byte[mHeight][mWidth];
				maxValue = scan.nextInt();
				for (int i = 0; i < mHeight; i++) {
					for (int j = 0; j < mWidth; j++) {
						mImageData[i][j] = toByte(scan.nextInt());
					}
				}
				scan.close();
			}
		}
	}

	public static byte toByte(float i) {
		return (byte) i;
	}

	public static int toInt(byte b) {
		return (b & 0xFF);
	}

	public void initialBorder(int size) {
		rows = mHeight + size - 1;
		cols = mWidth + size - 1;
		bordedMatrix = new byte[rows][cols];

		//colt stanga-sus
		for (int i = 0; i < size / 2; i++) {
			for (int j = 0; j < size / 2; j++) {
				bordedMatrix[i][j] = mImageData[0][0];
			}
		}
		//margine sus
		for (int i = 0; i < size / 2; i++) {
			for (int j = size / 2; j < mWidth + size / 2; j++) {
				bordedMatrix[i][j] = mImageData[0][j - size / 2];
			}
		}
		//colt dreapta sus
		for (int i = 0; i < size / 2; i++) {
			for (int j = mWidth + size / 2; j < mWidth + size - 1; j++) {
				bordedMatrix[i][j] = bordedMatrix[size / 2][mWidth + size / 2 - 1];
			}
		}
		//margine dreapta
		for (int i = size / 2; i < mHeight + size / 2; i++) {
			for (int j = mWidth; j < mWidth + size / 2; j++) {
				bordedMatrix[i][j + size / 2] = mImageData[i - size / 2][mWidth - 1];
			}
		}
		//colt dreapta jos
		for (int i = mHeight; i < mHeight + size / 2; i++) {
			for (int j = mWidth; j < mWidth + size / 2; j++) {
				bordedMatrix[i + size / 2][j + size / 2] = mImageData[mHeight - 1][mWidth - 1];
			}
		}
		//margine jos
		for (int i = mHeight; i < mHeight + size / 2; i++) {
			for (int j = size / 2; j < mWidth + size / 2; j++) {
				bordedMatrix[i + size / 2][j] = mImageData[mHeight - 1][j - size / 2];
			}
		}
		//colt stanga jos
		for (int i = mHeight; i < mHeight + size / 2; i++) {
			for (int j = 0; j < size / 2; j++) {
				bordedMatrix[i + size / 2][j] = mImageData[mHeight - 1][0];
			}
		}
		//margine stanga
		for (int i = size / 2; i < mHeight + size / 2; i++) {
			for (int j = 0; j < size / 2; j++) {
				bordedMatrix[i][j] = mImageData[i - size / 2][0];
			}
		}
		//copiem si mijlocul
		for (int i = size / 2; i < mHeight + size / 2; i++) {
			for (int j = size / 2; j < mWidth + size / 2; j++) {
				bordedMatrix[i][j] = mImageData[i - size / 2][j - size / 2];
			}
		}
	}

	public void updateBorder(int size, int rows, int cols) {
		//colt stanga-sus
		for (int i = 0; i < size / 2; i++) {
			for (int j = 0; j < size / 2; j++) {
				bordedMatrix[i][j] = bordedMatrix[size / 2][size / 2];
			}
		}
		//margine sus
		for (int i = 0; i < size / 2; i++) {
			for (int j = size / 2; j < mWidth + size / 2; j++) {
				bordedMatrix[i][j] = bordedMatrix[size / 2][j];
			}
		}
		//colt dreapta sus
		for (int i = 0; i < size / 2; i++) {
			for (int j = mWidth + size / 2; j < cols; j++) {
				bordedMatrix[i][j] = bordedMatrix[size / 2][mWidth + size / 2 - 1];
			}
		}
		//margine dreapta
		for (int i = size / 2; i < mHeight + size / 2; i++) {
			for (int j = mWidth + size / 2; j < cols; j++) {
				bordedMatrix[i][j] = bordedMatrix[i][mWidth + size / 2 - 1];
			}
		}
		//colt dreapta jos
		for (int i = mHeight + size / 2; i < rows; i++) {
			for (int j = mWidth + size / 2; j < cols; j++) {
				bordedMatrix[i][j] = bordedMatrix[mHeight + size / 2 - 1][mWidth + size / 2 - 1];
			}
		}
		//margine jos
		for (int i = mHeight + size / 2; i < rows; i++) {
			for (int j = size / 2; j < mWidth + size / 2; j++) {
				bordedMatrix[i][j] = bordedMatrix[mHeight + size / 2 - 1][j];
			}
		}
		//colt stanga jos
		for (int i = mHeight + size / 2; i < rows; i++) {
			for (int j = 0; j < size / 2; j++) {
				bordedMatrix[i][j] = bordedMatrix[mHeight + size / 2 - 1][size / 2];
			}
		}
		//margine stanga
		for (int i = size / 2; i < mHeight + size / 2; i++) {
			for (int j = 0; j < size / 2; j++) {
				bordedMatrix[i][j] = bordedMatrix[i][size / 2];
			}
		}
	}

	@Override
	public void applyLowPassFilter(int size) {
		float sum;
		int number = size * size;

		initialBorder(size);
		for (int i = size / 2; i < mHeight + size / 2; i++) {
			for (int j = size / 2; j < mWidth + size / 2; j++) {
				sum = 0;
				for (int k = i - size / 2; k < i + size / 2 + 1; k++) {
					for (int l = j - size / 2; l < j + size / 2 + 1; l++) {
						sum = sum + toInt(bordedMatrix[k][l]);
					}
				}
				bordedMatrix[i][j] = (byte) (sum / number);
				updateBorder(size, rows, cols);
			}
		}
		for (int i = 0; i < mHeight; i++) {
			for (int j = 0; j < mWidth; j++) {
				mImageData[i][j] = bordedMatrix[i + size / 2][j + size / 2];
			}
		}
	}
}