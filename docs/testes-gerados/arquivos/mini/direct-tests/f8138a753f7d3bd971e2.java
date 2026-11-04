/*
 * @test
 * @summary Test jdk.internal.jimage.ImageReaderFactory.get(Path) method
 * @run main ImageReaderFactoryWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReaderFactory;
import jdk.internal.jimage.ImageReader;

public class ImageReaderFactoryWitupTest {
    public static void main(String[] args) throws Exception {
        testNullPath();
        testValidPath();
    }

    private static void testNullPath() {
        try {
            ImageReaderFactory.get(null);
            throw new RuntimeException("Expected NullPointerException for null path");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException for null path", ioe);
        }
    }

    private static void testValidPath() throws IOException {
        // Use the known boot modules image path
        Path path = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = ImageReaderFactory.get(path);
        if (reader == null) {
            throw new RuntimeException("ImageReaderFactory.get returned null for valid path");
        }
        // Call again to test caching behavior
        ImageReader reader2 = ImageReaderFactory.get(path);
        if (reader2 == null) {
            throw new RuntimeException("ImageReaderFactory.get returned null on second call");
        }
        if (reader != reader2) {
            throw new RuntimeException("ImageReaderFactory.get did not cache ImageReader instance");
        }
    }
}
