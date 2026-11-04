/*
 * @test
 * @summary Test ImageReader.getImagePath() behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetImagePathReturnsExpectedPath();
        testGetImagePathThrowsWhenClosed();
    }

    private static void testGetImagePathReturnsExpectedPath() throws IOException {
        // Open an ImageReader on a known path
        Path testPath = Paths.get(System.getProperty("java.home"));
        ImageReader reader = ImageReader.open(testPath);
        try {
            Path imagePath = reader.getImagePath();
            if (!imagePath.equals(testPath)) {
                throw new AssertionError("getImagePath() returned unexpected path: " + imagePath);
            }
        } finally {
            reader.close();
        }
    }

    private static void testGetImagePathThrowsWhenClosed() throws IOException {
        Path testPath = Paths.get(System.getProperty("java.home"));
        ImageReader reader = ImageReader.open(testPath);
        reader.close();
        try {
            reader.getImagePath();
            throw new AssertionError("Expected IllegalStateException when calling getImagePath() after close");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
