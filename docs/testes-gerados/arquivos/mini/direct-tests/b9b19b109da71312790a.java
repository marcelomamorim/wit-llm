/*
 * @test
 * @summary Test ImageReader.getHeader() behavior including closed state
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
        testGetHeaderReturnsNonNull();
        testGetHeaderThrowsWhenClosed();
    }

    private static void testGetHeaderReturnsNonNull() throws IOException {
        // Use a known existing image file path or create a dummy one if possible
        // Since no file creation or mocking is allowed, test only if open succeeds
        Path imagePath = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());
            ImageHeader header = reader.getHeader();
            if (header == null) {
                throw new AssertionError("getHeader() returned null");
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void testGetHeaderThrowsWhenClosed() throws IOException {
        Path imagePath = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath);
            reader.close();
            try {
                reader.getHeader();
                throw new AssertionError("getHeader() did not throw after close");
            } catch (IllegalStateException e) {
                // expected
            }
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
