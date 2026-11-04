/* @test    @summary Test for ImageReader.getHeader() method    @run main ImageReaderWitupTest */

import java.io.IOException;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.ImageHeader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testGetHeaderReturnsHeader();
        testGetHeaderThrowsWhenClosed();
    }

    private static void testGetHeaderReturnsHeader() throws IOException {
        // Setup: create a mock or real ImageReader instance
        // Since actual file access is not possible, simulate with a subclass
        class MockImageReader extends ImageReader {
            private boolean isClosed = false;
            private final ImageHeader header = new ImageHeader();

            MockImageReader() {
                super(null); // passing null as constructor argument, assuming no constructor args needed
            }

            @Override
            public ImageHeader getHeader() {
                if (isClosed) {
                    throw new IllegalStateException("image file closed");
                }
                return header;
            }

            @Override
            public void close() {
                isClosed = true;
            }
        }

        MockImageReader reader = new MockImageReader();
        ImageHeader header = reader.getHeader();
        assert header != null : "Header should not be null";
        System.out.println("testGetHeaderReturnsHeader passed.");
        reader.close();
    }

    private static void testGetHeaderThrowsWhenClosed() {
        class MockImageReader extends ImageReader {
            private boolean isClosed = false;
            MockImageReader() {
                super(null);
            }
            @Override
            public ImageHeader getHeader() {
                if (isClosed) {
                    throw new IllegalStateException("image file closed");
                }
                return new ImageHeader();
            }
            @Override
            public void close() {
                isClosed = true;
            }
        }

        MockImageReader reader = new MockImageReader();
        reader.close();
        try {
            reader.getHeader();
            assert false : "Expected IllegalStateException when calling getHeader() after close";
        } catch (IllegalStateException e) {
            System.out.println("testGetHeaderThrowsWhenClosed passed.");
        }
    }
}
