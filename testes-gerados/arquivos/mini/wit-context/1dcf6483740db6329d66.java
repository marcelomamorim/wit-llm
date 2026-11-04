/*
 * @test
 * @summary Test ImageReader.close() method for correct exception and state behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        // Use a dummy path that does not exist to test close behavior
        // We expect open to fail if file does not exist, so we create a minimal test
        // Instead, we test close() behavior on a valid ImageReader instance

        // To create a valid ImageReader, we must open a real image file.
        // Since we cannot create one here, we test close() behavior indirectly.
        // We test that calling close() twice throws IOException as per source code.

        // Create a temporary file path for testing open failure
        Path dummyPath = Paths.get("/dev/null");

        ImageReader reader = null;
        try {
            // Attempt to open the image reader with native byte order
            reader = ImageReader.open(dummyPath, ByteOrder.nativeOrder());
        } catch (IOException e) {
            // Expected if /dev/null is not a valid image file
            // We cannot proceed with real ImageReader instance
            // So we skip to test close() on a mock subclass
            testCloseOnMock();
            return;
        }

        // If open succeeded (unlikely), test close behavior
        reader.close();

        boolean threw = false;
        try {
            reader.close();
        } catch (IOException e) {
            threw = true;
            if (!"image file already closed".equals(e.getMessage())) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
        if (!threw) {
            throw new AssertionError("Expected IOException on second close() call");
        }
    }

    // Helper method to test close() behavior on a minimal mock subclass
    private static void testCloseOnMock() throws Exception {
        final class DummySharedImageReader extends jdk.internal.jimage.SharedImageReader {
            DummySharedImageReader() {
                super(null, null);
            }
            @Override
            public void close(ImageReader ir) throws IOException {
                // no-op
            }
        }

        final class DummyImageReader extends ImageReader {
            private DummyImageReader(DummySharedImageReader reader) {
                super(reader);
            }
        }

        DummySharedImageReader shared = new DummySharedImageReader();
        DummyImageReader reader = new DummyImageReader(shared);

        // close first time should call shared.close and set closed
        reader.close();

        boolean threw = false;
        try {
            reader.close();
        } catch (IOException e) {
            threw = true;
            if (!"image file already closed".equals(e.getMessage())) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
        if (!threw) {
            throw new AssertionError("Expected IOException on second close() call");
        }
    }
}
