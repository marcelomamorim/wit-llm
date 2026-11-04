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
        testEncodeWithValidOID();
        testEncodeWithDerValueConstructor();
    }

    private static void testEncodeWithValidOID() throws IOException {
        // Create ObjectIdentifier with a valid OID string
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        CertificatePolicyId cpid = new CertificatePolicyId(oid);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream derOut = new DerOutputStream(baos);

        // Call encode and verify output is not empty
        cpid.encode(derOut);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }

    private static void testEncodeWithDerValueConstructor() throws IOException {
        // Create a DerValue with a valid OID encoding
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        DerOutputStream tempOut = new DerOutputStream();
        tempOut.putOID(oid);
        byte[] encodedOid = tempOut.toByteArray();

        DerValue derVal = new DerValue(encodedOid);
        CertificatePolicyId cpid = new CertificatePolicyId(derVal);

        // Verify that getIdentifier returns the same OID
        ObjectIdentifier returnedOid = cpid.getIdentifier();
        if (!oid.equals(returnedOid)) {
            throw new RuntimeException("OID mismatch from DerValue constructor");
        }

        // Also test encode produces output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream derOut = new DerOutputStream(baos);
        cpid.encode(derOut);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty after DerValue constructor");
        }
    }
}
