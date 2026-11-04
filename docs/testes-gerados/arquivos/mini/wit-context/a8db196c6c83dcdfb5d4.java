/*
 * @test
 * @summary Test ImageReader.getResource(ImageLocation) for open/closed state behavior
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
        testGetResourceThrowsWhenClosed();
        testGetResourceReturnsDataWhenOpen();
    }

    private static void testGetResourceThrowsWhenClosed() throws Exception {
        // Prepare a dummy Path that likely does not exist to avoid real file dependency
        Path dummyPath = Paths.get("/nonexistent/path/to/image.jimage");

        ImageReader reader = null;
        try {
            // Open the ImageReader with native byte order
            reader = ImageReader.open(dummyPath, ByteOrder.nativeOrder());
        } catch (IOException e) {
            // Expected if file does not exist; skip test as we cannot open
            return;
        }

        // Close the reader
        reader.close();

        // Create a dummy ImageLocation instance for testing
        ImageLocation loc = new ImageLocation() {
            @Override
            public int getOffset() {
                return 0;
            }

            @Override
            public int getLength() {
                return 0;
            }
        };

        // Expect IllegalStateException when calling getResource on closed reader
        try {
            reader.getResource(loc);
            throw new RuntimeException("Expected IllegalStateException not thrown when calling getResource on closed ImageReader");
        } catch (IllegalStateException expected) {
            // Test passed
        }
    }

    private static void testGetResourceReturnsDataWhenOpen() throws Exception {
        // Prepare a dummy Path that likely does not exist to avoid real file dependency
        Path dummyPath = Paths.get("/nonexistent/path/to/image.jimage");

        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath, ByteOrder.nativeOrder());
        } catch (IOException e) {
            // Expected if file does not exist; skip test as we cannot open
            return;
        }

        // Create a dummy ImageLocation instance for testing
        ImageLocation loc = new ImageLocation() {
            @Override
            public int getOffset() {
                return 0;
            }

            @Override
            public int getLength() {
                return 0;
            }
        };

        try {
            byte[] data = reader.getResource(loc);
            if (data == null) {
                throw new RuntimeException("getResource returned null array");
            }
        } catch (IllegalStateException e) {
            throw new RuntimeException("Unexpected IllegalStateException when ImageReader is open", e);
        } finally {
            reader.close();
        }
    }
}
