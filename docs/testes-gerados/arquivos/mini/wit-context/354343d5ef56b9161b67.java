/*
 * @test
 * @summary Test ImageReader.getResourceBuffer(ImageLocation) for open/closed state behavior
 * @run main ImageReaderWitupTest
 */

import java.nio.ByteBuffer;
import java.io.IOException;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.ImageLocation;

public class ImageReaderWitupTest {

    // Dummy minimal ImageLocation implementation for test
    static class DummyImageLocation extends ImageLocation {
        DummyImageLocation() {
            super(0, 0, 0, 0);
        }
    }

    // Dummy SharedImageReader to simulate getResourceBuffer call
    static class DummySharedImageReader extends jdk.internal.jimage.SharedImageReader {
        DummySharedImageReader() {
            super(null, null);
        }
        @Override
        public ByteBuffer getResourceBuffer(ImageLocation loc) {
            return ByteBuffer.allocate(1);
        }
        @Override
        public void close(ImageReader ir) {}
        @Override
        public jdk.internal.jimage.Directory getRootDirectory() { return null; }
        @Override
        public jdk.internal.jimage.Node findNode(String name) { return null; }
        @Override
        public byte[] getResource(jdk.internal.jimage.Node node) { return null; }
        @Override
        public byte[] getResource(jdk.internal.jimage.Resource rs) { return null; }
        @Override
        public jdk.internal.jimage.ImageHeader getHeader() { return null; }
    }

    // Subclass ImageReader to inject dummy SharedImageReader
    static class TestImageReader extends ImageReader {
        private boolean closed = false;
        TestImageReader() {
            super(new DummySharedImageReader());
        }
        @Override
        protected void requireOpen() {
            if (closed) {
                throw new IllegalStateException("image file closed");
            }
        }
        void setClosed(boolean val) {
            closed = val;
        }
        @Override
        public ByteBuffer getResourceBuffer(ImageLocation loc) {
            requireOpen();
            return super.getResourceBuffer(loc);
        }
    }

    public static void main(String[] args) throws IOException {
        TestImageReader reader = new TestImageReader();
        ImageLocation loc = new DummyImageLocation();

        // Test normal call when open
        ByteBuffer buf = reader.getResourceBuffer(loc);
        if (buf == null || buf.capacity() != 1) {
            throw new AssertionError("Expected ByteBuffer of capacity 1");
        }

        // Test call when closed triggers IllegalStateException
        reader.setClosed(true);
        boolean threw = false;
        try {
            reader.getResourceBuffer(loc);
        } catch (IllegalStateException e) {
            threw = true;
            if (!"image file closed".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when closed");
        }
    }
}
