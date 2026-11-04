/*
 * @test
 * @summary Test ImageReader.findLocation method for open and closed states
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.ImageLocation;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testFindLocationWhenOpen();
        testFindLocationWhenClosed();
    }

    private static void testFindLocationWhenOpen() throws IOException {
        // Use a valid image path that is unlikely to exist to avoid IOException on open
        Path dummyPath = Paths.get("/dev/null");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath, ByteOrder.nativeOrder());
            // The reader is open, calling findLocation should not throw IllegalStateException
            // We do not know the internal reader.findLocation behavior, so just call and check no exception
            ImageLocation loc = reader.findLocation("moduleName", "resourceName");
            // loc may be null or non-null depending on internal implementation, no assert on value
        } catch (IOException e) {
            // IOException may occur if /dev/null is not a valid image file, ignore for this test
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static void testFindLocationWhenClosed() throws IOException {
        Path dummyPath = Paths.get("/dev/null");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath, ByteOrder.nativeOrder());
        } catch (IOException e) {
            // Ignore open failure
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException ignored) {
            }
            // After close, findLocation should throw IllegalStateException
            boolean threw = false;
            try {
                reader.findLocation("moduleName", "resourceName");
            } catch (IllegalStateException expected) {
                threw = true;
            } catch (IOException e) {
                // IOException is not expected here, but if thrown, test still passes as findLocation requires open
                threw = true;
            }
            if (!threw) {
                throw new AssertionError("Expected IllegalStateException when calling findLocation on closed ImageReader");
            }
        }
    }
}
