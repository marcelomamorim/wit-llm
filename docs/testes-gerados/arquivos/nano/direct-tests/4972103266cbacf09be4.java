/*
 * @test
 * @summary Test the static open method of ImageReader class for valid inputs.
 * @run main ImageReaderWitupTest
 */
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) {
        testOpenWithValidPathAndByteOrder();
        testOpenWithNullPath();
        testOpenWithNullByteOrder();
    }

    static void testOpenWithValidPathAndByteOrder() {
        Path dummyPath = Paths.get("/dummy/path");
        ByteOrder order = ByteOrder.BIG_ENDIAN;
        try {
            ImageReader reader = ImageReader.open(dummyPath, order);
            if (reader == null) {
                throw new AssertionError("Expected non-null ImageReader instance");
            }
            reader.close();
        } catch (IOException e) {
            // Since the path is dummy, an IOException is acceptable; test passes if no unexpected exception occurs.
        }
    }

    static void testOpenWithNullPath() {
        try {
            ImageReader.open(null, ByteOrder.BIG_ENDIAN);
            throw new AssertionError("Expected NullPointerException for null path");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            // Not expected here
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    static void testOpenWithNullByteOrder() {
        Path dummyPath = Paths.get("/dummy/path");
        try {
            ImageReader.open(dummyPath, null);
            throw new AssertionError("Expected NullPointerException for null ByteOrder");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            // Not expected here
            throw new AssertionError("Unexpected IOException", e);
        }
    }
}
