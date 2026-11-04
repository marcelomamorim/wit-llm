/*
 * @test
 * @summary Verify DER encoding of sun.security.x509.Extension.encode
 * @modules java.base/sun.security.x509
 * @modules java.base/sun.security.util
 * @run main ExtensionWitupTest
 */

import java.io.IOException;
import java.util.Arrays;

import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.Extension;

public class ExtensionWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static byte[] encode(Extension ext) throws IOException {
        DerOutputStream out = new DerOutputStream();
        ext.encode(out);
        return out.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        // Null extensionId must be rejected with IOException
        try {
            encode(new Extension());
            throw new RuntimeException("Expected IOException for null OID");
        } catch (IOException expected) {
            // expected
        }

        // Non-critical: SEQUENCE { OID 1.2.3, OCTET STRING 05 00 }
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3");
        byte[] derValue = new byte[] {0x04, 0x02, 0x05, 0x00};
        Extension nonCritical = new Extension(oid, false, derValue);
        byte[] expectedNonCritical = new byte[] {
            0x30, 0x08,
            0x06, 0x02, 0x2A, 0x03,
            0x04, 0x02, 0x05, 0x00
        };
        check(Arrays.equals(expectedNonCritical, encode(nonCritical)),
              "Non-critical encoding mismatch");

        // Critical: BOOLEAN TRUE is emitted before the OCTET STRING
        Extension critical = new Extension(oid, true, derValue);
        byte[] expectedCritical = new byte[] {
            0x30, 0x0B,
            0x06, 0x02, 0x2A, 0x03,
            0x01, 0x01, (byte) 0xFF,
            0x04, 0x02, 0x05, 0x00
        };
        check(Arrays.equals(expectedCritical, encode(critical)),
              "Critical encoding mismatch");

        // Encoding is deterministic across repeated calls
        check(Arrays.equals(encode(critical), encode(critical)),
              "Encoding not deterministic");
    }
}
