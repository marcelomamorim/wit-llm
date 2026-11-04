/*
 * @test
 * @summary Test for getModuleNames() method in ImageReader class.
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testGetModuleNamesWhenOpen();
        testGetModuleNamesWhenClosed();
    }

    private static void testGetModuleNamesWhenOpen() throws IOException {
        Path imagePath = Paths.get("/path/to/valid/image"); // Placeholder path
        try (ImageReader reader = ImageReader.open(imagePath)) {
            String[] moduleNames = reader.getModuleNames();
            if (moduleNames == null) {
                throw new AssertionError("Expected non-null module names array");
            }
            // Additional checks can be added here if needed
        } catch (IOException e) {
            // Fail the test if exception occurs during normal operation
            throw new AssertionError("Unexpected IOException during open", e);
        }
    }

    private static void testGetModuleNamesWhenClosed() throws IOException {
        Path imagePath = Paths.get("/path/to/valid/image"); // Placeholder path
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath);
            reader.close();
        } catch (IOException e) {
            // Ignore, as close() may throw if already closed
        }
        try {
            reader.getModuleNames();
            throw new AssertionError("Expected IllegalStateException when calling getModuleNames() after close()");
        } catch (IOException e) {
            if (!e.getMessage().equals("image file closed")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
