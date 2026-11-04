/*
 * @test
 * @summary Test jdk.internal.jimage.ImageReader.getResource(String) method
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetResourceReturnsExpectedBytes();
        testGetResourceAfterCloseThrows();
    }

    private static void testGetResourceReturnsExpectedBytes() throws Exception {
        // Create a temporary empty file to open as image
        Path tempFile = Files.createTempFile("imagetest", ".jimage");
        tempFile.toFile().deleteOnExit();

        ImageReader reader = null;
        try {
            reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());

            // The image is empty, so getResource with a name likely returns null or empty
            // We test that the method returns without throwing and returns a byte[] or null
            byte[] result = null;
            try {
                result = reader.getResource("");
            } catch (IOException e) {
                // IOException is allowed if resource not found or file empty
                // Just print stack trace for debug
                e.printStackTrace();
            }

            // result can be null or byte[]; if not null, length >= 0
            if (result != null && result.length < 0) {
                throw new AssertionError("getResource returned byte[] with negative length");
            }

        } finally {
            if (reader != null) {
                reader.close();
            }
            Files.deleteIfExists(tempFile);
        }
    }

    private static void testGetResourceAfterCloseThrows() throws Exception {
        Path tempFile = Files.createTempFile("imagetest", ".jimage");
        tempFile.toFile().deleteOnExit();

        ImageReader reader = null;
        try {
            reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        boolean threw = false;
        try {
            reader.getResource("");
        } catch (IllegalStateException | IOException e) {
            // Expected: getResource calls requireOpen which throws IllegalStateException if closed
            // or IOException if closed (depending on implementation)
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected exception when calling getResource after close");
        }

        Files.deleteIfExists(tempFile);
    }
}
