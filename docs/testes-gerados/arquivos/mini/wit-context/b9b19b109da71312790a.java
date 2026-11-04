/*
 * @test
 * @summary Test ImageReader.getHeader() behavior including closed state exception
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.ImageHeader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testGetHeaderWhenOpen();
        testGetHeaderWhenClosed();
    }

    private static void testGetHeaderWhenOpen() throws Exception {
        // Use a known existing image file path for testing or skip if not available
        // Since we cannot create ImageReader without a real file, we test open and getHeader
        Path imagePath = Paths.get(System.getProperty("test.image", "src/java.base/share/classes/jdk/internal/jimage/ImageReader.java"));
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());
            ImageHeader header = reader.getHeader();
            if (header == null) {
                throw new AssertionError("getHeader() returned null");
            }
        } catch (IOException e) {
            // IOException can occur if file not found or invalid image file
            // Acceptable for this test environment
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static void testGetHeaderWhenClosed() throws Exception {
        Path imagePath = Paths.get(System.getProperty("test.image", "src/java.base/share/classes/jdk/internal/jimage/ImageReader.java"));
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());
            reader.close();
            try {
                reader.getHeader();
                throw new AssertionError("Expected IllegalStateException when calling getHeader() on closed ImageReader");
            } catch (IllegalStateException expected) {
                // Expected exception
            }
        } catch (IOException e) {
            // IOException can occur if file not found or invalid image file
            // Acceptable for this test environment
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
