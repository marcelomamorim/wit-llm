/*
 * @test
 * @summary Test CertificatePolicyId constructor with DerValue
 * @run main CertificatePolicyIdWitupTest
 */

import sun.security.x509.CertificatePolicyId;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class CertificatePolicyIdWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testInvalidDerValue();
    }

    private static void testValidDerValue() throws IOException {
        // Create a DerValue with a known OID
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        DerValue val = new DerValue(DerValue.tag_ObjectIdentifier, oid.toByteArray());

        CertificatePolicyId cpid = new CertificatePolicyId(val);
        ObjectIdentifier id = cpid.getIdentifier();

        if (!id.equals(oid)) {
            throw new RuntimeException("Expected OID " + oid + " but got " + id);
        }
    }

    private static void testInvalidDerValue() {
        // Create a DerValue with invalid tag to cause getOID() to throw IOException
        try {
            byte[] invalidData = new byte[] { 0x05, 0x00 }; // NULL tag, not OID
            DerValue val = new DerValue(invalidData);
            new CertificatePolicyId(val);
            throw new RuntimeException("Expected IOException not thrown for invalid DerValue");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
