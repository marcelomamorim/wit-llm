/*
 * @test
 * @summary Test for ImageReader.findLocation method to ensure it returns correct ImageLocation or null for non-existent names.
 * @run main ImageReaderWitupTest
 */
import java.io.IOException;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.ImageLocation;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws IOException {
        // Setup: create a mock or real ImageReader instance
        // For testing, assuming a real image file path is available at a known location
        // Since no file system interaction is specified, we simulate the behavior
        // Note: In real tests, replace with actual image file path
        java.nio.file.Path imagePath = java.nio.file.Paths.get("/path/to/test/image");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath);
            // Test case: existing name
            String existingName = "existingResource";
            ImageLocation location = reader.findLocation(existingName);
            // Verify that location is not null for existing resource
            if (location == null) {
                System.err.println("Test failed: findLocation returned null for existing resource: " + existingName);
            } else {
                System.out.println("findLocation returned: " + location);
            }
            // Test case: non-existing name
            String nonExistingName = "nonExistingResource";
            ImageLocation nonExistLocation = reader.findLocation(nonExistingName);
            if (nonExistLocation != null) {
                System.err.println("Test failed: findLocation returned non-null for non-existing resource: " + nonExistingName);
            } else {
                System.out.println("findLocation correctly returned null for non-existing resource: " + nonExistingName);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
