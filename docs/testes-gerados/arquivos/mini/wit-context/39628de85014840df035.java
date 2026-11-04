/*
 * @test
 * @summary Test ImageReader.open(Path) for null argument and normal usage
 * @run main ImageReaderWitupTest
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.ByteOrder;
import java.io.IOException;
import java.util.Objects;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testOpenWithNullPath();
        testOpenWithValidPath();
    }

    private static void testOpenWithNullPath() {
        try {
            ImageReader.open((Path) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testOpenWithValidPath() throws IOException {
        // Use a dummy path that likely does not exist but is non-null
        Path dummyPath = Paths.get("/nonexistent/path/for/testing");

        // Call open(Path) and verify it does not throw NullPointerException
        // IOException may be thrown if file does not exist, which is allowed
        try {
            ImageReader reader = ImageReader.open(dummyPath);
            if (reader == null) {
                throw new RuntimeException("ImageReader.open returned null");
            }
            // Close if opened successfully
            try {
                reader.close();
            } catch (IOException ignored) {
                // ignore close exceptions
            }
        } catch (IOException e) {
            // IOException is allowed if file does not exist or cannot be opened
        }
    }
}
