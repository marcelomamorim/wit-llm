/*
 * @test
 * @summary Deterministic tests for ImageStringsReader.stringFromByteBuffer(ByteBuffer)
 * @run main ImageStringsReaderWitupTest
 */

import java.nio.ByteBuffer;

public class ImageStringsReaderWitupTest {

    public static void main(String[] args) {
        testEmptyBuffer();
        testBufferWithValidAsciiString();
        testBufferWithInvalidUtf8Sequence();
        testBufferWithZeroLengthString();
    }

    private static void testEmptyBuffer() {
        // A ByteBuffer with no remaining bytes should produce empty string or throw InternalError
        ByteBuffer empty = ByteBuffer.allocate(0);
        try {
            String result = jdk.internal.jimage.ImageStringsReader.stringFromByteBuffer(empty);
            if (result == null) {
                throw new AssertionError("Returned null for empty buffer");
            }
            if (!"".equals(result)) {
                throw new AssertionError("Expected empty string for empty buffer but got: " + result);
            }
        } catch (InternalError e) {
            // Acceptable if InternalError is thrown due to limit <= position
        }
    }

    private static void testBufferWithValidAsciiString() {
        // Prepare a ByteBuffer containing ASCII characters encoded in MUTF-8 compatible format
        // For simplicity, encode "abc" as bytes: 0x61 0x62 0x63 0x00 (null terminator)
        byte[] bytes = new byte[] {0x61, 0x62, 0x63, 0x00};
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        String result = jdk.internal.jimage.ImageStringsReader.stringFromByteBuffer(buffer);
        if (!"abc".equals(result)) {
            throw new AssertionError("Expected 'abc' but got: " + result);
        }
    }

    private static void testBufferWithInvalidUtf8Sequence() {
        // Prepare a ByteBuffer with invalid UTF-8 sequence to trigger InternalError
        // According to the WIT expaths, invalid continuation bytes or malformed sequences cause InternalError
        // Example: 0xC0 0x20 (invalid continuation byte 0x20)
        byte[] bytes = new byte[] {(byte)0xC0, 0x20, 0x00};
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        try {
            jdk.internal.jimage.ImageStringsReader.stringFromByteBuffer(buffer);
            throw new AssertionError("Expected InternalError for invalid UTF-8 sequence");
        } catch (InternalError e) {
            // Expected
        }
    }

    private static void testBufferWithZeroLengthString() {
        // Buffer with only null terminator should produce empty string
        byte[] bytes = new byte[] {0x00};
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        String result = jdk.internal.jimage.ImageStringsReader.stringFromByteBuffer(buffer);
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string for buffer with only null terminator but got: " + result);
        }
    }
}
