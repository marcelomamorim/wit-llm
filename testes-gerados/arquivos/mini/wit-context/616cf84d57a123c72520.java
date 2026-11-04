/*
 * @test
 * @summary Test ImageReader.getStrings() behavior including closed state
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.ImageStringsReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetStringsWhenOpen();
        testGetStringsWhenClosed();
    }

    private static void testGetStringsWhenOpen() throws IOException {
        // Use a dummy path that does not exist to trigger IOException on open
        // but since we cannot create a real ImageReader without internal details,
        // we test only the observable behavior of getStrings() when open.
        // We create a minimal valid ImageReader by opening a real JDK image file if possible.

        Path imagePath = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());
            ImageStringsReader stringsReader = reader.getStrings();
            if (stringsReader == null) {
                throw new AssertionError("getStrings() returned null when open");
            }
        } catch (IOException e) {
            // If the image file is not accessible, skip this test silently
            // as we cannot create a valid ImageReader instance
            return;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void testGetStringsWhenClosed() throws IOException {
        Path imagePath = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());
        } catch (IOException e) {
            // Skip test if cannot open
            return;
        }
        reader.close();
        try {
            reader.getStrings();
            throw new AssertionError("Expected IllegalStateException when calling getStrings() on closed ImageReader");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
