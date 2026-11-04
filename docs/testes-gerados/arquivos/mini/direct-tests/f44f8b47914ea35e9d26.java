/*
 * @test
 * @summary Test ImageReader.getByteOrder() behavior
 * @run main ImageReaderWitupTest
 */

import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testGetByteOrderReturnsExpected();
        testGetByteOrderThrowsWhenClosed();
    }

    private static void testGetByteOrderReturnsExpected() throws IOException {
        // Use the current JDK image file as a known valid image file
        Path imagePath = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath);
            ByteOrder order = reader.getByteOrder();
            if (order == null) {
                throw new AssertionError("getByteOrder() returned null");
            }
            // The byte order should be either BIG_ENDIAN or LITTLE_ENDIAN
            if (!order.equals(ByteOrder.BIG_ENDIAN) && !order.equals(ByteOrder.LITTLE_ENDIAN)) {
                throw new AssertionError("getByteOrder() returned unexpected ByteOrder: " + order);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void testGetByteOrderThrowsWhenClosed() throws IOException {
        Path imagePath = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath);
            reader.close();
            try {
                reader.getByteOrder();
                throw new AssertionError("Expected IllegalStateException when calling getByteOrder() on closed ImageReader");
            } catch (IllegalStateException expected) {
                // expected
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                    // ignore
                }
            }
        }
    }
}
