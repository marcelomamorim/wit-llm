/*
 * @test
 * @summary Witup-generated tests for the ImageStream(ByteBuffer) constructor
 * @modules java.base/jdk.internal.jimage
 * @run main ImageStreamWitupTest
 */

import java.nio.ByteBuffer;
import jdk.internal.jimage.ImageStream;

public class ImageStreamWitupTest {
    public static void main(String[] args) {
        testNullBufferThrowsNPE();
        testWrappedBufferIsReadable();
        testExhaustedBufferHasNoBytes();
    }

    static void testNullBufferThrowsNPE() {
        try {
            new ImageStream((ByteBuffer) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null buffer");
    }

    static void testWrappedBufferIsReadable() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[]{(byte) 0xAB, 0x01});
        ImageStream s = new ImageStream(buf);
        if (!s.hasByte()) {
            throw new RuntimeException("hasByte() should be true for non-empty buffer");
        }
        if (s.get() != 0xAB) {
            throw new RuntimeException("first byte mismatch");
        }
        if (s.get() != 0x01) {
            throw new RuntimeException("second byte mismatch");
        }
        if (s.hasByte()) {
            throw new RuntimeException("hasByte() should be false after consuming all bytes");
        }
    }

    static void testExhaustedBufferHasNoBytes() {
        ByteBuffer buf = ByteBuffer.allocate(0);
        ImageStream s = new ImageStream(buf);
        if (s.hasByte()) {
            throw new RuntimeException("hasByte() should be false for empty buffer");
        }
        if (!s.hasBytes(0)) {
            throw new RuntimeException("hasBytes(0) should be true");
        }
        if (s.hasBytes(1)) {
            throw new RuntimeException("hasBytes(1) should be false for empty buffer");
        }
    }
}
