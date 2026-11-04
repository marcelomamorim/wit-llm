/*
 * @test
 * @summary Verifies the public ImageStream(ByteBuffer) constructor behavior
 * @modules java.base/jdk.internal.jimage
 * @run main ImageStreamWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import jdk.internal.jimage.ImageStream;

public class ImageStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullBufferRejected();
        testWrappedBufferIsUsed();
        testBufferByteOrderPreserved();
    }

    static void testNullBufferRejected() {
        try {
            new ImageStream((ByteBuffer) null);
            throw new RuntimeException("Expected NullPointerException for null buffer");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(buffer)
        }
    }

    static void testWrappedBufferIsUsed() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {(byte) 0x01, (byte) 0xFF});
        ImageStream stream = new ImageStream(buf);
        if (!stream.hasByte()) {
            throw new RuntimeException("Expected hasByte() true for buffer with remaining bytes");
        }
        if (!stream.hasBytes(2)) {
            throw new RuntimeException("Expected hasBytes(2) true");
        }
        int first = stream.get();
        int second = stream.get();
        if (first != 1) {
            throw new RuntimeException("Expected first byte 1 but was " + first);
        }
        if (second != 255) {
            throw new RuntimeException("Expected second byte 255 but was " + second);
        }
        if (stream.hasByte()) {
            throw new RuntimeException("Expected no remaining bytes");
        }
    }

    static void testBufferByteOrderPreserved() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {(byte) 0x01, (byte) 0x02});
        buf.order(ByteOrder.LITTLE_ENDIAN);
        ImageStream stream = new ImageStream(buf);
        int value = stream.getShort();
        if (value != 0x0201) {
            throw new RuntimeException("Expected little-endian short 0x0201 but was " + value);
        }
    }
}
