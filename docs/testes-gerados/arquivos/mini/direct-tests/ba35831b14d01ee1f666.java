/*
 * @test
 * @summary Test ImageReader.getName() method behavior
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
        testGetNameReturnsNonNullString();
        testGetNameThrowsAfterClose();
    }

    private static void testGetNameReturnsNonNullString() throws IOException {
        // Create a temporary empty file to open as image
        Path tempFile = Files.createTempFile("imagereader-test", ".jimage");
        tempFile.toFile().deleteOnExit();

        ImageReader reader = null;
        try {
            reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());
            String name = reader.getName();
            if (name == null) {
                throw new AssertionError("getName() returned null");
            }
            // name should be a non-empty string
            if (name.isEmpty()) {
                throw new AssertionError("getName() returned empty string");
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void testGetNameThrowsAfterClose() throws IOException {
        Path tempFile = Files.createTempFile("imagereader-test", ".jimage");
        tempFile.toFile().deleteOnExit();

        ImageReader reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());
        reader.close();

        boolean threw = false;
        try {
            reader.getName();
        } catch (IllegalStateException e) {
            // Expected exception because reader is closed
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("getName() did not throw IllegalStateException after close");
        }
    }
}
