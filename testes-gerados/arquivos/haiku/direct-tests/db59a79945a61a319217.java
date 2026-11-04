/*
 * @test
 * @summary Verify ImageStringsReader.stringFromByteBuffer decodes modified UTF-8 bytes from the buffer contents
 * @modules java.base/jdk.internal.jimage
 * @run main ImageStringsReaderWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import jdk.internal.jimage.ImageStringsReader;

public class ImageStringsReaderWitupTest {
    public static void main(String[] args) {
        checkAsciiDecoding();
        checkEmptyBuffer();
        checkRewindFromEndOfBuffer();
        checkTwoByteUtf8Character();
    }

    static void checkAsciiDecoding() {
        byte[] bytes = "abc".getBytes(StandardCharsets.US_ASCII);
        String actual = ImageStringsReader.stringFromByteBuffer(ByteBuffer.wrap(bytes));
        check("abc", actual);
    }

    static void checkEmptyBuffer() {
        String actual = ImageStringsReader.stringFromByteBuffer(ByteBuffer.allocate(0));
        check("", actual);
    }

    static void checkRewindFromEndOfBuffer() {
        byte[] bytes = "xyz".getBytes(StandardCharsets.US_ASCII);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.position(buffer.limit());
        String actual = ImageStringsReader.stringFromByteBuffer(buffer);
        check("xyz", actual);
    }

    static void checkTwoByteUtf8Character() {
        byte[] bytes = new byte[] { (byte) 0xC3, (byte) 0xA9 };
        String actual = ImageStringsReader.stringFromByteBuffer(ByteBuffer.wrap(bytes));
        check(String.valueOf((char) 0xE9), actual);
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected [" + expected + "] but was [" + actual + "]");
        }
    }
}
