/*
 * @test
 * @summary Test ImageHeader.writeTo(ImageStream) method behavior
 * @run main ImageHeaderWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

public class ImageHeaderWitupTest {

    static class DummyImageStream extends ImageStream {
        private ByteBuffer buffer;
        private int ensuredSize = -1;

        DummyImageStream() {
            super();
            buffer = ByteBuffer.allocate(ImageHeader.getHeaderSize());
        }

        @Override
        public void ensure(int size) {
            ensuredSize = size;
            if (buffer.capacity() < size) {
                ByteBuffer newBuffer = ByteBuffer.allocate(size);
                buffer.position(0);
                newBuffer.put(buffer);
                buffer = newBuffer;
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

    public static void main(String[] args) {
        // Create ImageHeader with known values
        int resourceCount = 10;
        int tableCount = 20;
        int locationsSize = 30;
        int stringsSize = 40;

        ImageHeader header = new ImageHeader(resourceCount, tableCount, locationsSize, stringsSize);

        DummyImageStream stream = new DummyImageStream();

        // Call writeTo and verify no exceptions
        header.writeTo(stream);

        // Verify that ensure was called with correct header size
        int expectedSize = ImageHeader.getHeaderSize();
        if (stream.getEnsuredSize() != expectedSize) {
            throw new AssertionError("Expected ensure size " + expectedSize + ", but got " + stream.getEnsuredSize());
        }

        // Verify buffer content matches header fields
        ByteBuffer buf = stream.getBuffer();
        buf.position(0);

        int magic = buf.getInt();
        int version = buf.getInt();
        int flags = buf.getInt();
        int resCount = buf.getInt();
        int tblLength = buf.getInt();
        int locSize = buf.getInt();
        int strSize = buf.getInt();

        if (magic != ImageHeader.MAGIC) {
            throw new AssertionError("Magic mismatch: expected " + ImageHeader.MAGIC + ", got " + magic);
        }

        int expectedVersion = (ImageHeader.MAJOR_VERSION << 16) | ImageHeader.MINOR_VERSION;
        if (version != expectedVersion) {
            throw new AssertionError("Version mismatch: expected " + expectedVersion + ", got " + version);
        }

        if (flags != 0) {
            throw new AssertionError("Flags mismatch: expected 0, got " + flags);
        }

        if (resCount != resourceCount) {
            throw new AssertionError("ResourceCount mismatch: expected " + resourceCount + ", got " + resCount);
        }

        if (tblLength != tableCount) {
            throw new AssertionError("TableLength mismatch: expected " + tableCount + ", got " + tblLength);
        }

        if (locSize != locationsSize) {
            throw new AssertionError("LocationsSize mismatch: expected " + locationsSize + ", got " + locSize);
        }

        if (strSize != stringsSize) {
            throw new AssertionError("StringsSize mismatch: expected " + stringsSize + ", got " + strSize);
        }
    }
}

// Minimal stub for ImageStream to allow compilation
abstract class ImageStream {
    public abstract void ensure(int size);
    public abstract ByteBuffer getBuffer();
}
