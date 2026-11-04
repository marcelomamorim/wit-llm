/*
 * @test
 * @summary Test ImageReader.close() method behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    // Minimal stub for SharedImageReader to test close behavior
    static class StubSharedImageReader extends ImageReader.SharedImageReader {
        boolean closedCalled = false;

        StubSharedImageReader() {
            super(null, null);
        }

        @Override
        void close(ImageReader ir) throws IOException {
            if (closedCalled) {
                throw new IOException("close called twice");
            }
            closedCalled = true;
        }
    }

    public static void main(String[] args) throws Exception {
        StubSharedImageReader stub = new StubSharedImageReader();
        ImageReader ir = new ImageReader(stub);

        // First close should succeed
        ir.close();

        // Second close should throw IOException with message "image file already closed"
        try {
            ir.close();
            throw new RuntimeException("Expected IOException not thrown on second close");
        } catch (IOException e) {
            if (!"image file already closed".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
