/*
 * @test
 * @summary Verify DerValue.getUTF8String decodes UTF-8 content and rejects non-UTF8String tags
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructedWithTagAndData();
        testParsedFromEncodedBytes();
        testMultiByteUtf8();
        testWrongTagThrowsIOException();
    }

    static void testConstructedWithTagAndData() throws Exception {
        byte[] data = "hello".getBytes("UTF-8");
        DerValue v = new DerValue((byte) 0x0C, data);
        String s = v.getUTF8String();
        if (!"hello".equals(s)) {
            throw new RuntimeException("Expected hello but got: " + s);
        }
    }

    static void testParsedFromEncodedBytes() throws Exception {
        byte[] encoded = new byte[] {0x0C, 0x05, 'h', 'e', 'l', 'l', 'o'};
        DerValue v = new DerValue(encoded);
        String s = v.getUTF8String();
        if (!"hello".equals(s)) {
            throw new RuntimeException("Expected hello but got: " + s);
        }
    }

    static void testMultiByteUtf8() throws Exception {
        String expected = "h\u00e9llo \u20ac";
        byte[] data = expected.getBytes("UTF-8");
        DerValue v = new DerValue((byte) 0x0C, data);
        String s = v.getUTF8String();
        if (!expected.equals(s)) {
            throw new RuntimeException("Expected " + expected + " but got: " + s);
        }
    }

    static void testWrongTagThrowsIOException() throws Exception {
        DerValue v = new DerValue((byte) 0x02, new byte[] {0x01});
        try {
            v.getUTF8String();
            throw new RuntimeException("Expected IOException for non-UTF8String tag");
        } catch (IOException expected) {
            // expected
        }
    }
}
