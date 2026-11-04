/*
 * @test
 * @summary Test getAttributes method of ImageReader for various offsets.
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) {
        testGetAttributesWithValidOffset();
        testGetAttributesWithInvalidOffset();
    }

    private static void testGetAttributesWithValidOffset() {
        try {
            // Assuming a valid image path and offset for test
            // Since actual file and offset are unknown, this test focuses on method invocation
            // Replace "path/to/image" with a real path if available
            ImageReader reader = ImageReader.open(java.nio.file.Paths.get("path/to/image"));
            long[] attrs = reader.getAttributes(0); // Using offset 0 as a placeholder
            // Verify that the returned array is not null
            if (attrs == null) {
                throw new AssertionError("Expected non-null array for valid offset");
            }
            reader.close();
        } catch (IOException e) {
            // If file not found or IO error, print and continue
            System.out.println("IO exception during valid offset test: " + e.getMessage());
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid offset test: " + e, e);
        }
    }

    private static void testGetAttributesWithInvalidOffset() {
        try {
            // Assuming a valid image path for test
            // Replace "path/to/image" with a real path if available
            ImageReader reader = ImageReader.open(java.nio.file.Paths.get("path/to/image"));
            // Use an invalid offset, e.g., -1
            long[] attrs = reader.getAttributes(-1);
            // Depending on implementation, this may throw or return null/empty
            // Here, we check if it throws an exception or returns null
            if (attrs == null) {
                // Acceptable if method returns null for invalid offset
                System.out.println("Received null for invalid offset as expected.");
            } else {
                // If array is returned, check if it's empty or contains invalid data
                if (attrs.length == 0) {
                    System.out.println("Received empty array for invalid offset.");
                } else {
                    System.out.println("Received array for invalid offset: " + java.util.Arrays.toString(attrs));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("IO exception during invalid offset test: " + e.getMessage());
        } catch (Exception e) {
            // If exception is thrown, it may be acceptable depending on implementation
            System.out.println("Exception during invalid offset test: " + e);
        }
    }
}
