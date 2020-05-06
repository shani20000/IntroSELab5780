package unittests;

import org.junit.jupiter.api.Test;
import renderer.ImageWriter;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void writeToImage() {
        ImageWriter image = new ImageWriter("firstTest", 1600, 1000, 800, 500);
        for (int col = 0; col < 500; col++) {
            for (int row = 0; row < 800; row++) {
                if (col % 50 == 0 || row % 50 == 0) {
                    image.writePixel(row, col, Color.RED);
                }
                else {
                    image.writePixel(row, col, Color.WHITE);
                }
            }
        }
        image.writeToImage();
    }
}