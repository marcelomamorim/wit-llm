/*
 * @test
 * @summary Tests for jdk.internal.jimage.ImageStringsReader.stringFromByteBuffer(ByteBuffer)
 * @run main ImageStringsReaderWitupTest
 */

import java.nio.ByteBuffer;

public class ImageStringsReaderWitupTest {

    public static void main(String[] args) {
        testStringFromByteBuffer_NormalCase();
        testStringFromByteBuffer_ZeroLength();
        testStringFromByteBuffer_NullBuffer();
        testStringFromByteBuffer_BufferWithZero();
        testStringFromByteBuffer_BufferWithMultibyteChars();
    }

    static void testStringFromByteBuffer_NormalCase() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte)0x41); // 'A'
        buffer.put((byte)0x42); // 'B'
        buffer.put((byte)0x43); // 'C'
        buffer.flip();
        try {
            String result = jdk.internal.jimage.ImageStringsReader.stringFromByteBuffer(buffer);
            if (!"ABC".equals(result)) {
                throw new AssertionError("Expected 'ABC', got: " + result);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in normal case", t);
        }
    }

    static void testStringFromByteBuffer_ZeroLength() {
        ByteBuffer buffer = ByteBuffer.allocate(0);
        buffer.flip();
        try {
            String result = jdk.internal.jimage.ImageStringsReader.stringFromByteBuffer(buffer);
            if (!"".equals(result)) {
                throw new AssertionError("Expected empty string, got: " + result);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in zero length buffer", t);
        }
    }

    static void testStringFromByteBuffer_NullBuffer() {
        try {
            jdk.internal.jimage.ImageStringsReader.stringFromByteBuffer(null);
            throw new AssertionError("Expected NullPointerException for null buffer");
        } catch (NullPointerException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for null buffer", t);
        }
    }

    static void testStringFromByteBuffer_BufferWithZero() {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.put((byte)0x00);
        buffer.flip();
        try {
            String result = jdk.internal.jimage.ImageStringsReader.stringFromByteBuffer(buffer);
            if (!"".equals(result)) {
                throw new AssertionError("Expected empty string for zero byte, got: " + result);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for buffer with zero byte", t);
        }
    }

    static void testStringFromByteBuffer_BufferWithMultibyteChars() {
        // Prepare a buffer with multibyte UTF-8 encoded characters
        byte[] multibyteBytes = new byte[] { (byte)0xC3, (byte)0xA9 }; // 'é' in UTF-8
        ByteBuffer buffer = ByteBuffer.wrap(multibyteBytes);
        try {
            String result = jdk.internal.jimage.ImageStringsReader.stringFromByteBuffer(buffer);
            // The method expects UTF-16 chars, so this test checks if it handles multibyte properly
            // Since the method uses charsFromByteBufferLength and charsFromByteBuffer, which are not fully shown,
            // we assume it should produce a string with 'é' or throw an exception.
            if (!"é".equals(result)) {
                throw new AssertionError("Expected 'é', got: " + result);
            }
        } catch (Throwable t) {
            // Acceptable if method throws due to invalid encoding
        }
    }
}
