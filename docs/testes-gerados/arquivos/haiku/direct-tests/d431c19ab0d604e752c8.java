/*
 * @test
 * @summary Verify Extension.encode null handling and DER round-trip output
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main ExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import sun.security.util.DerValue;
import sun.security.x509.Extension;

public class ExtensionWitupTest {

    private static final byte[] NON_CRITICAL_DER = {
        0x30, 0x09,
        0x06, 0x03, 0x55, 0x1D, 0x13,
        0x04, 0x02, 0x01, 0x02
    };

    private static final byte[] CRITICAL_DER = {
        0x30, 0x0C,
        0x06, 0x03, 0x55, 0x1D, 0x13,
        0x01, 0x01, (byte) 0xFF,
        0x04, 0x02, 0x01, 0x02
    };

    public static void main(String[] args) throws IOException {
        testEncodeNullOutputThrowsNPE();
        testNonCriticalRoundTrip();
        testCriticalRoundTrip();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static byte[] encodeToBytes(Extension ext) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ext.encode(bos);
        return bos.toByteArray();
    }

    static void testEncodeNullOutputThrowsNPE() throws IOException {
        Extension ext = new Extension(new DerValue(NON_CRITICAL_DER));
        boolean thrown = false;
        try {
            ext.encode(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "encode(null) must throw NullPointerException");
    }

    static void testNonCriticalRoundTrip() throws IOException {
        Extension ext = new Extension(new DerValue(NON_CRITICAL_DER));
        byte[] out = encodeToBytes(ext);
        check(Arrays.equals(NON_CRITICAL_DER, out),
              "non-critical extension must re-encode identically");
    }

    static void testCriticalRoundTrip() throws IOException {
        Extension ext = new Extension(new DerValue(CRITICAL_DER));
        byte[] out = encodeToBytes(ext);
        check(Arrays.equals(CRITICAL_DER, out),
              "critical extension must re-encode identically");
    }
}
