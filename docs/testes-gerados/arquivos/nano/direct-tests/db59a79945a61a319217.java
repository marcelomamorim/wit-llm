/*
 * @test
 * @summary Test stringFromByteBuffer method for various ByteBuffer inputs
 * @run main ImageStringsReaderWitupTest
 */

import java.nio.ByteBuffer;
import jdk.internal.jimage.ImageStringsReader;

public class ImageStringsReaderWitupTest {

    public static void main(String[] args) {
        testEmptyBuffer();
        testSingleAsciiChar();
        testMultipleAsciiChars();
        testUtf8Encoding();
        testZeroByte();
    }

    static void testEmptyBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(0);
        String result = ImageStringsReader.stringFromByteBuffer(buffer);
        assert result.equals("") : "Empty buffer should return empty string";
    }

    static void testSingleAsciiChar() {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {65}); // 'A'
        String result = ImageStringsReader.stringFromByteBuffer(buffer);
        assert result.equals("A") : "Single ASCII byte should return corresponding char";
    }

    static void testMultipleAsciiChars() {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {72, 101, 108, 108, 111}); // "Hello"
        String result = ImageStringsReader.stringFromByteBuffer(buffer);
        assert result.equals("Hello") : "Multiple ASCII bytes should form string";
    }

    static void testUtf8Encoding() {
        // UTF-8 encoding for '€' (Euro sign)
        byte[] euroBytes = {(byte)0xE2, (byte)0x82, (byte)0xAC};
        ByteBuffer buffer = ByteBuffer.wrap(euroBytes);
        String result = ImageStringsReader.stringFromByteBuffer(buffer);
        // Since the method decodes UTF-8, expect the Euro sign
        assert result.equals("€") : "UTF-8 encoded Euro sign should decode correctly";
    }

    static void testZeroByte() {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {0});
        String result = ImageStringsReader.stringFromByteBuffer(buffer);
        assert result.equals("") : "Zero byte should result in empty string";
    }
}
