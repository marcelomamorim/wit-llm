/*
 * @test
 * @summary Test sun.security.x509.OIDName.encode(DerOutputStream) method
 * @run main OIDNameWitupTest
 */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.OIDName;

public class OIDNameWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithValidOID();
    }

    private static void testEncodeWithValidOID() throws IOException {
        // Create OIDName from a valid OID string
        OIDName oidName = new OIDName("1.2.3.4");

        // Prepare DerOutputStream to encode into
        DerOutputStream out = new DerOutputStream();

        // Call encode method
        oidName.encode(out);

        // The encoded bytes should start with the OID tag (0x06) and contain the OID
        byte[] encoded = out.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }

        // Check that the first byte is the OID tag (0x06)
        if (encoded[0] != 0x06) {
            throw new RuntimeException("Encoded output does not start with OID tag");
        }

        // Check that the encoded bytes contain the OID arcs (at least one arc)
        // The second byte is length, so total length should be at least 3
        if (encoded.length < 3) {
            throw new RuntimeException("Encoded output too short to contain OID arcs");
        }

        // If no exception thrown, test passed
        System.out.println("testEncodeWithValidOID passed");
    }
}
