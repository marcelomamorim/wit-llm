/*
 * @test
 * @summary Verify ImageHeader.writeTo(ByteBuffer) serialization layout, defaults, and exceptions
 * @modules java.base/jdk.internal.jimage
 * @run main ImageHeaderWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.BufferOverflowException;
import jdk.internal.jimage.ImageHeader;

public class ImageHeaderWitupTest {

    public static void main(String[] args) {
        testHeaderSize();
        testFullConstructorWritesAllSlots();
        testShortConstructorUsesDefaults();
        testNullBufferRejected();
        testBufferTooSmallRejected();
    }

    static void testHeaderSize() {
        check(ImageHeader.getHeaderSize() == 28, "header size must be 28 bytes");
    }

    static void testFullConstructorWritesAllSlots() {
        ImageHeader h = new ImageHeader(0xCAFEDADA, 2, 3, 0x12, 5, 3, 40, 50);
        ByteBuffer b = ByteBuffer.allocate(ImageHeader.getHeaderSize());
        h.writeTo(b);
        check(b.position() == 28, "position after writeTo");
        check(b.getInt(0) == 0xCAFEDADA, "magic slot");
        check(b.getInt(4) == ((2 << 16) | 3), "version slot");
        check(b.getInt(8) == 0x12, "flags slot");
        check(b.getInt(12) == 5, "resourceCount slot");
        check(b.getInt(16) == 3, "tableLength slot");
        check(b.getInt(20) == 40, "locationsSize slot");
        check(b.getInt(24) == 50, "stringsSize slot");
    }

    static void testShortConstructorUsesDefaults() {
        ImageHeader h = new ImageHeader(7, 4, 60, 70);
        ByteBuffer b = ByteBuffer.allocate(ImageHeader.getHeaderSize());
        h.writeTo(b);
        check(b.getInt(0) == ImageHeader.MAGIC, "default magic");
        check(b.getInt(4) == ((ImageHeader.MAJOR_VERSION << 16) | ImageHeader.MINOR_VERSION), "default version");
        check(b.getInt(8) == 0, "default flags must be zero");
        check(b.getInt(12) == 7, "resourceCount slot");
        check(b.getInt(16) == 4, "tableLength slot");
        check(b.getInt(20) == 60, "locationsSize slot");
        check(b.getInt(24) == 70, "stringsSize slot");
    }

    static void testNullBufferRejected() {
        ImageHeader h = new ImageHeader(1, 1, 1, 1);
        try {
            h.writeTo((ByteBuffer) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testBufferTooSmallRejected() {
        ImageHeader h = new ImageHeader(1, 1, 1, 1);
        ByteBuffer b = ByteBuffer.allocate(ImageHeader.getHeaderSize() - 1);
        try {
            h.writeTo(b);
            throw new RuntimeException("expected BufferOverflowException");
        } catch (BufferOverflowException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
