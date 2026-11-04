/*
 * @test
 * @summary ContentInfo.encode re-encodes DER parsed through public constructors without changes
 * @modules java.base/sun.security.pkcs
 * @modules java.base/sun.security.util
 * @run main ContentInfoWitupTest
 */

import java.util.Arrays;

import sun.security.pkcs.ContentInfo;
import sun.security.util.DerOutputStream;

public class ContentInfoWitupTest {

    // SEQUENCE { OID 1.2.840.113549.1.7.1 } (no optional content)
    static final byte[] DATA_NO_CONTENT = {
        0x30, 0x0B,
        0x06, 0x09, 0x2A, (byte)0x86, 0x48, (byte)0x86, (byte)0xF7, 0x0D, 0x01, 0x07, 0x01
    };

    // SEQUENCE { OID 1.2.840.113549.1.7.1, [0] EXPLICIT OCTET STRING "AB" }
    static final byte[] DATA_WITH_CONTENT = {
        0x30, 0x11,
        0x06, 0x09, 0x2A, (byte)0x86, 0x48, (byte)0x86, (byte)0xF7, 0x0D, 0x01, 0x07, 0x01,
        (byte)0xA0, 0x04, 0x04, 0x02, 0x41, 0x42
    };

    // SEQUENCE { OID 1.2.840.113549.1.7 } (pkcs7 OID, no content)
    static final byte[] PKCS7_NO_CONTENT = {
        0x30, 0x0A,
        0x06, 0x08, 0x2A, (byte)0x86, 0x48, (byte)0x86, (byte)0xF7, 0x0D, 0x01, 0x07
    };

    public static void main(String[] args) throws Exception {
        checkRoundTrip(DATA_NO_CONTENT);
        checkRoundTrip(DATA_WITH_CONTENT);
        checkRoundTrip(PKCS7_NO_CONTENT);
    }

    private static void checkRoundTrip(byte[] der) throws Exception {
        ContentInfo ci = new ContentInfo(der);
        DerOutputStream out = new DerOutputStream();
        ci.encode(out);
        byte[] encoded = out.toByteArray();
        if (!Arrays.equals(der, encoded)) {
            throw new RuntimeException("Round-trip mismatch: expected "
                + Arrays.toString(der) + " but got " + Arrays.toString(encoded));
        }
    }
}
