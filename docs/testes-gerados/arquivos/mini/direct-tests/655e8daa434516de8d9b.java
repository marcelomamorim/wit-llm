/*
 * @test
 * @summary Test sun.security.x509.CertificatePolicyId.encode(DerOutputStream) method
 * @run main CertificatePolicyIdWitupTest
 */

import sun.security.x509.CertificatePolicyId;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class CertificatePolicyIdWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeProducesExpectedOutput();
        testEncodeWithEmptyOID();
    }

    private static void testEncodeProducesExpectedOutput() throws IOException {
        // Create ObjectIdentifier for a known OID
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        CertificatePolicyId cpid = new CertificatePolicyId(oid);

        // Prepare DerOutputStream to encode
        DerOutputStream out = new DerOutputStream();
        cpid.encode(out);

        // Get encoded bytes
        byte[] encoded = out.toByteArray();

        // The encoding should start with the OID tag (0x06)
        if (encoded.length == 0 || encoded[0] != 0x06) {
            throw new RuntimeException("Encoded output does not start with OID tag");
        }

        // Decode back to DerValue and check OID equality
        DerValue val = new DerValue(encoded);
        ObjectIdentifier decodedOid = val.getOID();

        if (!oid.equals(decodedOid)) {
            throw new RuntimeException("Decoded OID does not match original OID");
        }
    }

    private static void testEncodeWithEmptyOID() throws IOException {
        // Create ObjectIdentifier with minimal OID (0.0)
        ObjectIdentifier oid = new ObjectIdentifier("0.0");
        CertificatePolicyId cpid = new CertificatePolicyId(oid);

        DerOutputStream out = new DerOutputStream();
        cpid.encode(out);

        byte[] encoded = out.toByteArray();

        if (encoded.length == 0 || encoded[0] != 0x06) {
            throw new RuntimeException("Encoded output does not start with OID tag for empty OID");
        }

        DerValue val = new DerValue(encoded);
        ObjectIdentifier decodedOid = val.getOID();

        if (!oid.equals(decodedOid)) {
            throw new RuntimeException("Decoded OID does not match original empty OID");
        }
    }
}
