/*
 * @test
 * @summary Test ImageReader.getAttributes(int) for open and closed states
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
        testGetAttributesOpen();
        testGetAttributesClosed();
    }

    private static void testGetAttributesOpen() throws Exception {
        // Create a temporary empty file to open as imagePath
        Path tempFile = Files.createTempFile("imagereaderwitup", ".jimage");
        try {
            // Open ImageReader with native byte order
            ImageReader reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());
            try {
                // Call getAttributes with offset 0 (arbitrary)
                // We expect no IllegalStateException since reader is open
                // The returned array may be null or empty depending on implementation
                long[] attrs = reader.getAttributes(0);
                if (attrs == null) {
                    throw new AssertionError("getAttributes returned null array");
                }
            } finally {
                reader.close();
            }
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    private static void testGetAttributesClosed() throws Exception {
        Path tempFile = Files.createTempFile("imagereaderwitup", ".jimage");
        try {
            ImageReader reader = ImageReader.open(tempFile, ByteOrder.nativeOrder());
            reader.close();
            try {
                reader.getAttributes(0);
                throw new AssertionError("Expected IllegalStateException when calling getAttributes on closed ImageReader");
            } catch (IllegalStateException expected) {
                // Expected exception
            }
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
