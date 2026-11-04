/*
 * @test
 * @summary Test for getByteOrder() method in ImageReader class.
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testGetByteOrderWhenOpen();
        testGetByteOrderWhenClosed();
    }

    private static void testGetByteOrderWhenOpen() throws IOException {
        // Setup: open a dummy Path (assuming a valid Path object, replace with actual if needed)
        java.nio.file.Path dummyPath = java.nio.file.Paths.get("/dev/null");
        // Open ImageReader with native byte order
        try (jdk.internal.jimage.ImageReader reader = jdk.internal.jimage.ImageReader.open(dummyPath)) {
            ByteOrder order = reader.getByteOrder();
            // Verify that the returned ByteOrder is not null
            if (order == null) {
                throw new AssertionError("Expected non-null ByteOrder when open");
            }
        }
    }

    private static void testGetByteOrderWhenClosed() {
        java.nio.file.Path dummyPath = java.nio.file.Paths.get("/dev/null");
        jdk.internal.jimage.ImageReader reader = null;
        try {
            reader = jdk.internal.jimage.ImageReader.open(dummyPath);
        } catch (IOException e) {
            // If opening fails, skip test
            return;
        }
        try {
            reader.close();
        } catch (IOException e) {
            // Ignore close exception
        }
        boolean exceptionThrown = false;
        try {
            reader.getByteOrder();
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException after closing the reader");
        }
    }
}
