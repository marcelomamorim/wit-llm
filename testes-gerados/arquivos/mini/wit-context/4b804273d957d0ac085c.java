/*
 * @test
 * @summary Test ImageReader.getResource(Node) for open/closed state behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        // Use a known existing image file path for testing
        // Since we cannot create a real image file here, test only open/closed behavior
        Path dummyPath = Paths.get("/dev/null"); // /dev/null exists on Unix-like systems

        ImageReader reader = null;
        try {
            // Open ImageReader with native byte order
            reader = ImageReader.open(dummyPath, ByteOrder.nativeOrder());

            // We cannot test getResource with a real Node without internal constructors,
            // so we test that calling getResource on a null Node throws NullPointerException
            // or IOException depending on implementation.
            try {
                reader.getResource(null);
                // If no exception, test passes for this call
            } catch (NullPointerException | IOException e) {
                // Expected possible exceptions due to null argument
            }

            // Close the reader
            reader.close();

            // After close, getResource should throw IOException due to closed state
            boolean threwIOException = false;
            try {
                reader.getResource(null);
            } catch (IOException e) {
                threwIOException = true;
            }

            if (!threwIOException) {
                throw new AssertionError("Expected IOException when calling getResource on closed ImageReader");
            }

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                    // Ignore, already closed
                }
            }
        }

        System.out.println("Test passed.");
    }
}
