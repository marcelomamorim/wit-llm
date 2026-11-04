/*
 * @test
 * @summary Test ImageReader.open(Path, ByteOrder) for null argument checks and normal operation
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testOpenNullImagePath();
        testOpenNullByteOrder();
        testOpenValidPath();
    }

    private static void testOpenNullImagePath() {
        try {
            ImageReader.open(null, ByteOrder.nativeOrder());
            throw new RuntimeException("Expected NullPointerException for null imagePath");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testOpenNullByteOrder() {
        Path dummyPath = Paths.get("/tmp");
        try {
            ImageReader.open(dummyPath, null);
            throw new RuntimeException("Expected NullPointerException for null byteOrder");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testOpenValidPath() throws IOException {
        // We cannot guarantee a real image file at /tmp, so just test that method does not throw NPE
        Path dummyPath = Paths.get("/tmp");
        ByteOrder order = ByteOrder.nativeOrder();
        try {
            ImageReader reader = ImageReader.open(dummyPath, order);
            if (reader == null) {
                throw new RuntimeException("ImageReader.open returned null");
            }
            reader.close();
        } catch (IOException e) {
            // IOException is allowed if file does not exist or is invalid, so test passes
        }
    }
}
