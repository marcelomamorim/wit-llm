/*
 * @test
 * @summary Test sun.security.x509.AlgorithmId.encode() method for correct encoding and exception safety
 * @run main AlgorithmIdWitupTest
 */

import sun.security.x509.AlgorithmId;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.security.AlgorithmParameters;

public class AlgorithmIdWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithValidOid();
        testEncodeWithNullOid();
    }

    private static void testEncodeWithValidOid() throws IOException {
        // Use a known OID (e.g., 1.2.840.113549) which is a valid OID prefix
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 840, 113549});
        AlgorithmId algId = new AlgorithmId(oid);
        byte[] encoded = algId.encode();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoded byte array is null or empty");
        }
        // Basic sanity check: first byte should be DER tag for SEQUENCE (0x30)
        if (encoded[0] != 0x30) {
            throw new AssertionError("Encoded data does not start with DER SEQUENCE tag");
        }
    }

    private static void testEncodeWithNullOid() {
        try {
            AlgorithmId algId = new AlgorithmId((ObjectIdentifier) null);
            algId.encode();
            throw new AssertionError("Expected NullPointerException when encoding with null OID");
        } catch (NullPointerException | IOException e) {
            // Expected: NullPointerException or IOException due to null OID
        }
    }
}
