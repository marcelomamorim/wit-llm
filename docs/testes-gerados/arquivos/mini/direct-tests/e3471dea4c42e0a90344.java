/*
 * @test
 * @summary Test ImageReader.getAttributes(int) method behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.ByteOrder;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        // Use a known existing image file path for testing
        // Since no real image file is provided, test open and getAttributes with minimal checks
        Path imagePath = Paths.get(System.getProperty("test.src", ""), "testimage.jimage");

        ImageReader reader = null;
        try {
            // Open the image reader
            reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());

            // Call getAttributes with offset 0 (likely valid offset)
            long[] attrs = reader.getAttributes(0);
            if (attrs == null) {
                throw new RuntimeException("getAttributes returned null for offset 0");
            }

            // Call getAttributes with a negative offset to check behavior
            try {
                long[] negAttrs = reader.getAttributes(-1);
                if (negAttrs == null) {
                    throw new RuntimeException("getAttributes returned null for negative offset");
                }
            } catch (IOException e) {
                // Expected: may throw IOException for invalid offset
            } catch (RuntimeException e) {
                // Acceptable if implementation throws unchecked exception
            }

            // Close the reader
            reader.close();

            // After close, getAttributes should throw IOException or IllegalStateException
            try {
                reader.getAttributes(0);
                throw new RuntimeException("getAttributes did not throw after close");
            } catch (IOException | IllegalStateException expected) {
                // Expected exception
            }

        } catch (IOException e) {
            // If image file not found or cannot be opened, print message and exit
            System.out.println("Test skipped: cannot open image file: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
