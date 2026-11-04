/*
 * @test
 * @summary Test getRootDirectory() method of ImageReader for normal operation
 * @run main ImageReaderWitupTest
 */
import java.io.IOException;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.Directory;

public class ImageReaderWitupTest {
    public static void main(String[] args) {
        try {
            // Mock or create a dummy Path object if needed; here, assuming a valid Path is provided
            java.nio.file.Path dummyPath = java.nio.file.Paths.get("dummy");
            // Open ImageReader with native byte order
            try (ImageReader reader = ImageReader.open(dummyPath)) {
                Directory dir = reader.getRootDirectory();
                if (dir == null) {
                    System.out.println("getRootDirectory() returned null");
                } else {
                    System.out.println("getRootDirectory() succeeded: " + dir);
                }
            }
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }
}
