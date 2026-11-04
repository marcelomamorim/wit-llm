/*
 * @test
 * @summary Test ImageReader.getResource(Resource) method behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        // Since ImageReader requires a valid Path and Resource, and no Resource
        // implementation is provided, test basic open and close behavior and
        // that getResource(Resource) throws IOException if called after close.

        // We create a dummy Path to open the ImageReader
        // but since we cannot create a real Resource, we test only open/close and
        // ensureOpen behavior indirectly.

        // Use a try-with-resources to open and close the ImageReader
        java.nio.file.Path dummyPath = java.nio.file.Paths.get(System.getProperty("java.io.tmpdir"));

        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath, ByteOrder.nativeOrder());

            // We cannot create a Resource instance, so we test that getResource
            // throws NullPointerException if null is passed (since no null check in code)
            try {
                reader.getResource((jdk.internal.jimage.Resource) null);
                throw new RuntimeException("Expected NullPointerException not thrown");
            } catch (NullPointerException npe) {
                // expected
            } catch (IOException ioe) {
                // IOException is declared, but NPE expected due to null deref
                throw new RuntimeException("Unexpected IOException instead of NPE", ioe);
            }

            // Test getResource with a dummy Resource implementation that throws IOException
            // Since we cannot instantiate Resource, skip this.

        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        // After close, getResource should throw IOException due to ensureOpen
        try {
            reader.getResource((jdk.internal.jimage.Resource) null);
            throw new RuntimeException("Expected IOException not thrown after close");
        } catch (IOException ioe) {
            // expected
        }
    }
}
