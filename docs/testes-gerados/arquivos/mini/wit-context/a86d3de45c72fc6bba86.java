/*
 * @test
 * @summary Test ImageReader.getString(int) for open and closed states
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetStringWhenOpen();
        testGetStringWhenClosed();
    }

    private static void testGetStringWhenOpen() throws Exception {
        // Use a known existing image file path or create a dummy one if possible
        // Since we cannot create a real image file here, test only that getString does not throw when open
        Path imagePath = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());
            // Call getString with offset 0 or any int, expecting no IllegalStateException
            // We do not assert exact string because it depends on image content
            String s = reader.getString(0);
            if (s == null) {
                throw new AssertionError("getString returned null");
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void testGetStringWhenClosed() throws Exception {
        Path imagePath = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());
        reader.close();
        try {
            reader.getString(0);
            throw new AssertionError("Expected IllegalStateException when calling getString on closed ImageReader");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }
}
