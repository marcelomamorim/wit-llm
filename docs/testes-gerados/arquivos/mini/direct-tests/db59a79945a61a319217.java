/*
 * @test
 * @summary Test ImageStringsReader.stringFromByteBuffer with various ByteBuffer inputs
 * @run main ImageStringsReaderWitupTest
 */

import java.nio.ByteBuffer;
import jdk.internal.jimage.ImageStringsReader;

public class ImageStringsReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptyBuffer();
        testSingleCharBuffer();
        testMultiCharBuffer();
    }

    private static void testEmptyBuffer() {
        // Create a ByteBuffer representing an empty string
        ByteBuffer buffer = ByteBuffer.allocate(0);
        String result = ImageStringsReader.stringFromByteBuffer(buffer);
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string for empty buffer, got: '" + result + "'");
        }
    }

    private static void testSingleCharBuffer() {
        // Prepare a ByteBuffer with one character encoded in modified UTF-8
        // We must encode length and char properly according to charsFromByteBufferLength and charsFromByteBuffer
        // Since we do not have the source of charsFromByteBufferLength and charsFromByteBuffer,
        // we test with a buffer that encodes a single ASCII character 'A' (0x41)

        // The length encoding is not shown, but we assume length=1 for one char
        // We create a buffer with length encoding for 1 char and the char 'A' in MUTF8

        // For test purpose, we create a buffer with one char 'A' encoded as 0x41
        // We must encode length first, but since the length encoding method is not given,
        // we create a buffer with length 1 encoded as 1 byte 0x01 (assuming length encoding is 1 byte)

        // We will create a buffer with 1 byte length = 1, and 1 byte for 'A' (0x41)
        // This is a minimal test to check the method does not fail

        byte[] bytes = new byte[] { 1, 0x41 };
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        String result = ImageStringsReader.stringFromByteBuffer(buffer);

        if (!"A".equals(result)) {
            throw new AssertionError("Expected 'A' for single char buffer, got: '" + result + "'");
        }
    }

    private static void testMultiCharBuffer() {
        // Prepare a ByteBuffer with multiple characters encoded in modified UTF-8
        // For example, string "Hi"
        // We encode length 2 and chars 'H' (0x48), 'i' (0x69)

        // Again, length encoding is unknown, so we assume length=2 encoded as 2 bytes: 0x02
        // Then chars 'H' and 'i' as bytes 0x48 and 0x69

        byte[] bytes = new byte[] { 2, 0x48, 0x69 };
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        String result = ImageStringsReader.stringFromByteBuffer(buffer);

        if (!"Hi".equals(result)) {
            throw new AssertionError("Expected 'Hi' for multi char buffer, got: '" + result + "'");
        }
    }
}
