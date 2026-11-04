/*
 * @test
 * @summary Test sun.security.x509.Extension.encode(OutputStream) for null and normal output
 * @run main ExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.Extension;
import sun.security.util.ObjectIdentifier;

public class ExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeNullOutputStream();
        testEncodeNormal();
    }

    private static void testEncodeNullOutputStream() {
        Extension ext = new Extension();
        try {
            ext.encode(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testEncodeNormal() throws IOException {
        // Prepare a minimal valid Extension
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 3, 4});
        boolean critical = true;
        byte[] value = new byte[] {0x01, 0x02, 0x03};

        Extension ext = new Extension(oid, critical, value);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);

        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }

        // Basic sanity check: encoded output should contain the OID bytes
        // We check that the encoded output contains the OID arc 1.2.3.4 in DER form
        // DER encoding of OID 1.2.3.4 is 06 03 2a 03 04
        // 0x2a = 42 decimal = 1*40 + 2
        boolean foundOid = false;
        for (int i = 0; i < encoded.length - 4; i++) {
            if (encoded[i] == 0x06 && encoded[i+1] == 0x03 &&
                encoded[i+2] == 0x2a && encoded[i+3] == 0x03 && encoded[i+4] == 0x04) {
                foundOid = true;
                break;
            }
        }
        if (!foundOid) {
            throw new RuntimeException("Encoded output does not contain expected OID encoding");
        }
    }
}
