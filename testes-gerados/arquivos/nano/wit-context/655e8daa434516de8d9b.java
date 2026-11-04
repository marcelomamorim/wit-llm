/* @test    @summary Test for sun.security.x509.CertificatePolicyId.encode(DerOutputStream out)    @run main CertificatePolicyIdWitupTest */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.CertificatePolicyId;

public class CertificatePolicyIdWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithValidId();
        testEncodeWithNullId();
        testEncodeWithEmptyId();
    }

    private static void testEncodeWithValidId() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        CertificatePolicyId cpid = new CertificatePolicyId(oid);
        DerOutputStream out = new DerOutputStream();
        cpid.encode(out);
        byte[] encoded = out.toByteArray();
        // Basic check: encoded should contain the OID bytes
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding failed: output is null or empty");
        }
        // Optional: further validation of encoding structure can be added
    }

    private static void testEncodeWithNullId() {
        try {
            CertificatePolicyId cpid = new CertificatePolicyId((ObjectIdentifier) null);
            DerOutputStream out = new DerOutputStream();
            cpid.encode(out);
            throw new AssertionError("Expected NullPointerException or IOException for null id");
        } catch (NullPointerException | IOException e) {
            // Expected exception
        }
    }

    private static void testEncodeWithEmptyId() throws IOException {
        ObjectIdentifier emptyOid = new ObjectIdentifier(new byte[0]);
        CertificatePolicyId cpid = new CertificatePolicyId(emptyOid);
        DerOutputStream out = new DerOutputStream();
        cpid.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding failed for empty OID");
        }
    }
}
