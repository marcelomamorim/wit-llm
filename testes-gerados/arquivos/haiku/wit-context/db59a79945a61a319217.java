/*
 * @test
 * @summary Witup generated tests for ImageStringsReader.stringFromByteBuffer happy paths
 * @modules java.base/jdk.internal.jimage
 * @run main ImageStringsReaderWitupTest
 */

import java.nio.ByteBuffer;
import jdk.internal.jimage.ImageStringsReader;

public class ImageStringsReaderWitupTest {

    public static void main(String[] args) {
        check(ImageStringsReader.stringFromByteBuffer(bytes(0)), "");
        check(ImageStringsReader.stringFromByteBuffer(bytes('a', 'b', 'c', 0)), "abc");
        check(ImageStringsReader.stringFromByteBuffer(bytes(0xC3, 0xA9, 0)), "\u00e9");
    }

    static ByteBuffer bytes(int... values) {
        byte[] b = new byte[values.length];
        for (int i = 0; i < values.length; i++) {
            b[i] = (byte) values[i];
        }
        return ByteBuffer.wrap(b);
    }

    static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected [" + expected + "] but was [" + actual + "]");
        }
    }
}
