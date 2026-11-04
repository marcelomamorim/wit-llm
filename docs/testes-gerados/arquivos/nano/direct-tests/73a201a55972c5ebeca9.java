/* @test    @summary Test the writeTo method of ImageHeader for normal operation    @run main ImageHeaderWitupTest */

import jdk.internal.jimage.ImageHeader;
import java.nio.ByteBuffer;

public class ImageHeaderWitupTest {

    public static void main(String[] args) {
        testWriteToWithValidStream();
        testWriteToWithNullStream();
    }

    static void testWriteToWithValidStream() {
        class DummyStream {
            private ByteBuffer buffer;
            private int ensuredSize = 0;

            void ensure(int size) {
                buffer = ByteBuffer.allocate(size);
                ensuredSize = size;
            }

            ByteBuffer getBuffer() {
                return buffer;
            }
        }

        DummyStream stream = new DummyStream();
        ImageHeader header = new ImageHeader(10, 1, 0, 0, 5, 3, 20, 30);
        header.writeTo(stream);
        assert stream.ensuredSize == ImageHeader.getHeaderSize() : "Header size mismatch";
        ByteBuffer buf = stream.getBuffer();
        assert buf.capacity() == ImageHeader.getHeaderSize() : "Buffer capacity mismatch";
        assert buf.getInt(0) == ImageHeader.MAGIC : "Magic number mismatch";
        int version = buf.getInt(4);
        int majorVersion = version >>> 16;
        int minorVersion = version & 0xFFFF;
        assert majorVersion == header.majorVersion : "Major version mismatch";
        assert minorVersion == header.minorVersion : "Minor version mismatch";
    }

    static void testWriteToWithNullStream() {
        try {
            ImageHeader header = new ImageHeader(10, 1, 0, 0, 5, 3, 20, 30);
            header.writeTo(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
