/*
 * @test
 * @summary Checks DerValue.getT61String tag guard and ISO-8859-1 decoding of T61 data
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        testNonT61TagThrowsIOException();
        testT61Latin1Decoding();
        testEmptyT61ReturnsEmptyString();
    }

    static void testNonT61TagThrowsIOException() {
        DerValue v = new DerValue((byte) 0x04, new byte[] {0x41});
        boolean thrown = false;
        try {
            v.getT61String();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IOException for non-T61 tag");
        }
    }

    static void testT61Latin1Decoding() throws IOException {
        DerValue v = new DerValue((byte) 0x14, new byte[] {0x41, 0x42, (byte) 0xE9});
        String s = v.getT61String();
        if (!"AB\u00E9".equals(s)) {
            throw new RuntimeException("unexpected decoding: " + s);
        }
    }

    static void testEmptyT61ReturnsEmptyString() throws IOException {
        DerValue v = new DerValue((byte) 0x14, new byte[0]);
        String s = v.getT61String();
        if (!"".equals(s)) {
            throw new RuntimeException("expected empty string but got: " + s);
        }
    }
}
