/*
 * @test
 * @summary Verifies DerValue.getGeneralString() returns ASCII content for GeneralString tags and throws IOException otherwise
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;

public class DerValueWitupTest {
    private static final byte TAG_GENERAL_STRING = (byte)0x1B;
    private static final byte TAG_OCTET_STRING = (byte)0x04;
    private static final byte TAG_INTEGER = (byte)0x02;

    public static void main(String[] args) throws Exception {
        testAsciiContent();
        testEmptyContent();
        testNonAsciiBytesAreReplaced();
        testWrongTagThrowsIOException(TAG_OCTET_STRING);
        testWrongTagThrowsIOException(TAG_INTEGER);
    }

    private static void testAsciiContent() throws Exception {
        DerValue v = new DerValue(TAG_GENERAL_STRING, "abc".getBytes("ASCII"));
        String s = v.getGeneralString();
        if (!"abc".equals(s)) {
            throw new RuntimeException("Expected 'abc' but got '" + s + "'");
        }
    }

    private static void testEmptyContent() throws Exception {
        DerValue v = new DerValue(TAG_GENERAL_STRING, new byte[0]);
        String s = v.getGeneralString();
        if (!"".equals(s)) {
            throw new RuntimeException("Expected empty string but got '" + s + "'");
        }
    }

    private static void testNonAsciiBytesAreReplaced() throws Exception {
        DerValue v = new DerValue(TAG_GENERAL_STRING, new byte[]{(byte)0xE9});
        String s = v.getGeneralString();
        if (!"\uFFFD".equals(s)) {
            throw new RuntimeException("Expected replacement char but got '" + s + "'");
        }
    }

    private static void testWrongTagThrowsIOException(byte tag) throws Exception {
        DerValue v = new DerValue(tag, "abc".getBytes("ASCII"));
        try {
            v.getGeneralString();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for tag " + tag);
    }
}
