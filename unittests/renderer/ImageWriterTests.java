package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
/**
 * Testing ImageWriter
 * This class contains unit tests for the ImageWriter class.
 * It tests the constructors and the writeToImage method.
 */
class ImageWriterTests {
    /**
     * Test method for {@link renderer.ImageWriter#ImageWriter(String, int, int)}.
     */
    @Test
    void writeImageTest() {

        final int width = 800;
        final int height = 500;
        final int xGrid = width / 16;
        final int yGrid = height / 10;
        // Create an ImageWriter instance with 500x800 resolution
        ImageWriter imageWriter = new ImageWriter("test_yellow_grid", width, height);

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                // If the pixel is in a grid pattern, color it yellow
                if(i % xGrid == 0 || j % xGrid == 0) {
                    imageWriter.writePixel(i, j, new Color(java.awt.Color.RED));
                }
                else {
                    imageWriter.writePixel(i, j, new Color(java.awt.Color.YELLOW));
                }
            }
        }
        // Save the image
        imageWriter.writeToImage();


    }


}