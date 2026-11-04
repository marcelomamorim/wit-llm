/*
 * @test
 * @summary Test sun.security.x509.AVA encode method
 * @run main AVAWitupTest
 */

import sun.security.x509.AVA;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AVAWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeProducesOutput();
    }

    private static void testEncodeProducesOutput() throws IOException {
        // Create a simple AVA instance with known OID and DerValue
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {2, 5, 4, 3}); // commonName OID
        DerValue val = new DerValue(DerValue.tag_PrintableString, "TestValue");
        AVA ava = new AVA(oid, val);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream derOut = new DerOutputStream(baos);

        ava.encode(derOut);

        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }

        // Basic sanity check: encoded output starts with ASN.1 SEQUENCE tag (0x30)
        if (encoded[0] != 0x30) {
            throw new RuntimeException("Encoded output does not start with ASN.1 SEQUENCE tag");
        }
    }
}
