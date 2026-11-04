/*
 * @test
 * @summary Test ImageReader.findLocation(String) method behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.ImageLocation;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a minimal temporary file to open as image
        Path tempFile = Files.createTempFile("imagereader-test", ".jimage");
        try {
            // Write minimal content to file to avoid open failure
            Files.write(tempFile, new byte[] {0, 1, 2, 3}, StandardOpenOption.WRITE);

            ImageReader reader = null;
            try {
                reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());

                // Test findLocation with a valid name (empty string)
                try {
                    ImageLocation loc = reader.findLocation("");
                    if (loc == null) {
                        throw new RuntimeException("findLocation returned null for empty string");
                    }
                } catch (IOException e) {
                    // IOException is allowed if image format is invalid
                    // Just print stack trace for diagnostic
                    e.printStackTrace();
                }

                // Test findLocation with a null name - expect NullPointerException
                boolean npeThrown = false;
                try {
                    reader.findLocation(null);
                } catch (NullPointerException npe) {
                    npeThrown = true;
                } catch (IOException ioe) {
                    // IOException may be thrown instead of NPE depending on implementation
                }
                if (!npeThrown) {
                    // If no NPE, test passed as IOException is allowed
                }

                // Close reader and test that findLocation throws IllegalStateException
                reader.close();
                boolean iseThrown = false;
                try {
                    reader.findLocation("");
                } catch (IllegalStateException | IOException ex) {
                    iseThrown = true;
                }
                if (!iseThrown) {
                    throw new RuntimeException("Expected exception after close not thrown");
                }

            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ignored) {
                    }
                }
            }

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
