/*
 * @test
 * @summary Test jdk.internal.jimage.ImageHeader.writeTo(ImageStream) for NPE and normal behavior
 * @run main ImageHeaderWitupTest
 */

import java.nio.ByteBuffer;
import jdk.internal.jimage.ImageHeader;

public class ImageHeaderWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteToNullStream();
        testWriteToValidStream();
    }

    private static void testWriteToNullStream() {
        ImageHeader header = new ImageHeader(1, 2, 3, 4);
        try {
            header.writeTo(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testWriteToValidStream() {
        // Minimal ImageStream implementation for testing
        class TestImageStream extends jdk.internal.jimage.ImageStream {
            private ByteBuffer buffer;
            private int ensuredSize = 0;

            TestImageStream() {
                buffer = ByteBuffer.allocate(ImageHeader.getHeaderSize());
            }

            @Override
            public void ensure(int size) {
                ensuredSize = size;
                if (buffer.capacity() < size) {
                    throw new Error("Buffer too small");
                }
            }

            @Override
            public ByteBuffer getBuffer() {
                return buffer;
            }

            int getEnsuredSize() {
                return ensuredSize;
            }
        }

        ImageHeader header = new ImageHeader(5, 6, 7, 8);
        TestImageStream stream = new TestImageStream();
        header.writeTo(stream);

        if (stream.getEnsuredSize() != ImageHeader.getHeaderSize()) {
            throw new Error("Stream.ensure was not called with correct header size");
        }

        ByteBuffer buf = stream.getBuffer();
        buf.rewind();

        int magic = buf.getInt();
        int version = buf.getInt();
        int flags = buf.getInt();
        int resourceCount = buf.getInt();
        int tableLength = buf.getInt();
        int locationsSize = buf.getInt();
        int stringsSize = buf.getInt();

        if (magic != ImageHeader.MAGIC) {
            throw new Error("Magic number mismatch: expected " + ImageHeader.MAGIC + " but got " + magic);
        }

        int expectedVersion = (ImageHeader.MAJOR_VERSION << 16) | ImageHeader.MINOR_VERSION;
        if (version != expectedVersion) {
            throw new Error("Version mismatch: expected " + expectedVersion + " but got " + version);
        }

        if (flags != 0) {
            throw new Error("Flags mismatch: expected 0 but got " + flags);
        }

        if (resourceCount != 5) {
            throw new Error("Resource count mismatch: expected 5 but got " + resourceCount);
        }

        if (tableLength != 6) {
            throw new Error("Table length mismatch: expected 6 but got " + tableLength);
        }

        if (locationsSize != 7) {
            throw new Error("Locations size mismatch: expected 7 but got " + locationsSize);
        }

        if (stringsSize != 8) {
            throw new Error("Strings size mismatch: expected 8 but got " + stringsSize);
        }
    }
}
