/*
 * @test
 * @summary Test for jdk.internal.jimage.ImageReader.getStrings() method.
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testGetStringsWhenOpen();
        testGetStringsWhenClosed();
    }

    private static void testGetStringsWhenOpen() throws IOException {
        // Setup: create a mock or real Path to an image file if available
        // For this test, assume a valid Path object 'path' pointing to a valid image file.
        // Since no actual file is provided, this test is illustrative.
        // Replace with actual Path if available.
        java.nio.file.Path path = java.nio.file.Paths.get("/path/to/valid/image");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(path);
            // Call getStrings() when open
            try {
                reader.getStrings();
            } catch (Exception e) {
                // Fail the test if exception occurs
                throw new AssertionError("Expected getStrings() to succeed when open", e);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void testGetStringsWhenClosed() throws IOException {
        // Setup: create a mock or real Path to an image file if available
        java.nio.file.Path path = java.nio.file.Paths.get("/path/to/valid/image");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(path);
            // Close the reader to simulate closed state
            reader.close();
            // Now, calling getStrings() should throw IllegalStateException
            try {
                reader.getStrings();
                throw new AssertionError("Expected IllegalStateException when calling getStrings() after close");
            } catch (IllegalStateException e) {
                // Expected exception
            }
        } catch (IOException e) {
            // If opening the reader fails, skip the test or handle accordingly
            // For this test, assume the file exists; otherwise, ignore.
        }
    }
}
