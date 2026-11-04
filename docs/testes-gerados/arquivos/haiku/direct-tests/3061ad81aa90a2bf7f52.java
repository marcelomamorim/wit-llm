/*
 * @test
 * @summary Verify DerValue.getT61String decodes T61 values as ISO-8859-1 and rejects non-T61 tags
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */
import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testAsciiT61();
        testEmptyT61();
        testLatin1T61();
        testOctetStringTagRejected();
        testIntegerTagRejected();
    }

    static void testAsciiT61() throws Exception {
        byte[] data = {'a', 'b', 'c'};
        DerValue v = new DerValue(DerValue.tag_T61String, data);
        check("abc", v.getT61String());
    }

    static void testEmptyT61() throws Exception {
        DerValue v = new DerValue(DerValue.tag_T61String, new byte[0]);
        check("", v.getT61String());
    }

    static void testLatin1T61() throws Exception {
        byte[] data = {(byte) 0xE9};
        DerValue v = new DerValue(DerValue.tag_T61String, data);
        check(String.valueOf((char) 0xE9), v.getT61String());
    }

    static void testOctetStringTagRejected() throws Exception {
        DerValue v = new DerValue(DerValue.tag_OctetString, new byte[] {'x'});
        expectIOException(v);
    }

    static void testIntegerTagRejected() throws Exception {
        DerValue v = new DerValue(DerValue.tag_Integer, new byte[] {1});
        expectIOException(v);
    }

    static void expectIOException(DerValue v) {
        try {
            v.getT61String();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for non-T61 tag");
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }
}
