/*
 * @test
 * @summary Verify ImageHeader.writeTo(ByteBuffer) rejects null and serializes header fields in the expected layout
 * @modules java.base/jdk.internal.jimage
 * @run main ImageHeaderWitupTest
 */
import java.nio.ByteBuffer;
import jdk.internal.jimage.ImageHeader;

public class ImageHeaderWitupTest {
    public static void main(String[] args) {
        testNullBufferThrowsNpe();
        testFullConstructorLayout();
        testDefaultConstructorLayout();
    }

    static void testNullBufferThrowsNpe() {
        ImageHeader header = new ImageHeader(1, 1, 1, 1);
        boolean thrown = false;
        try {
            header.writeTo((ByteBuffer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null buffer");
        }
    }

    static void testFullConstructorLayout() {
        ImageHeader header = new ImageHeader(0x11223344, 2, 3, 5, 6, 7, 8, 9);
        ByteBuffer buf = ByteBuffer.allocate(ImageHeader.getHeaderSize());
        header.writeTo(buf);
        if (buf.position() != ImageHeader.getHeaderSize()) {
            throw new RuntimeException("Unexpected position: " + buf.position());
        }
        check(buf, 0, 0x11223344, "magic");
        check(buf, 4, (2 << 16) | 3, "version");
        check(buf, 8, 5, "flags");
        check(buf, 12, 6, "resourceCount");
        check(buf, 16, 7, "tableLength");
        check(buf, 20, 8, "locationsSize");
        check(buf, 24, 9, "stringsSize");
    }

    static void testDefaultConstructorLayout() {
        ImageHeader header = new ImageHeader(10, 11, 12, 13);
        ByteBuffer buf = ByteBuffer.allocate(ImageHeader.getHeaderSize());
        header.writeTo(buf);
        check(buf, 0, ImageHeader.MAGIC, "magic");
        check(buf, 4, (ImageHeader.MAJOR_VERSION << 16) | ImageHeader.MINOR_VERSION, "version");
        check(buf, 8, 0, "flags");
        check(buf, 12, 10, "resourceCount");
        check(buf, 16, 11, "tableLength");
        check(buf, 20, 12, "locationsSize");
        check(buf, 24, 13, "stringsSize");
    }

    static void check(ByteBuffer buf, int index, int expected, String name) {
        int actual = buf.getInt(index);
        if (actual != expected) {
            throw new RuntimeException(name + " at offset " + index
                    + ": expected " + expected + " but was " + actual);
        }
    }
}
