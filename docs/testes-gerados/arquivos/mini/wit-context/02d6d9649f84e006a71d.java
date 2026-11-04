/*
 * @test
 * @summary Test CertificatePolicyId constructor with DerValue input for correct behavior and exception
 * @run main CertificatePolicyIdWitupTest
 */

import sun.security.x509.CertificatePolicyId;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class CertificatePolicyIdWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testInvalidDerValueThrowsIOException();
    }

    private static void testValidDerValue() throws IOException {
        // Create a DerValue with tag 6 (OBJECT IDENTIFIER) and a valid OID
        byte[] oidBytes = {0x06, 0x03, 0x2a, 0x03, 0x04}; // OID 1.2.3.4
        DerValue val = new DerValue(oidBytes);

        CertificatePolicyId cpid = new CertificatePolicyId(val);
        ObjectIdentifier oid = cpid.getIdentifier();

        if (!oid.toString().equals("1.2.3.4")) {
            throw new RuntimeException("Expected OID 1.2.3.4 but got " + oid.toString());
        }

        String str = cpid.toString();
        if (!str.contains("1.2.3.4")) {
            throw new RuntimeException("toString() does not contain OID string");
        }

        // Test equals and hashCode
        CertificatePolicyId cpid2 = new CertificatePolicyId(val);
        if (!cpid.equals(cpid2)) {
            throw new RuntimeException("equals() failed for identical OIDs");
        }
        if (cpid.hashCode() != cpid2.hashCode()) {
            throw new RuntimeException("hashCode() inconsistent for identical OIDs");
        }

        // Test encode method writes the OID without exception
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        sun.security.util.DerOutputStream dos = new sun.security.util.DerOutputStream(baos);
        cpid.encode(dos);
        dos.close();
        byte[] encoded = baos.toByteArray();
        if (encoded.length == 0) {
            throw new RuntimeException("encode() produced empty output");
        }
    }

    private static void testInvalidDerValueThrowsIOException() {
        // Create a DerValue with invalid tag (not 6) to trigger IOException in getOID()
        // According to DerValue source, getOID() throws IOException if tag != 6
        byte[] invalidOidBytes = {0x05, 0x00}; // tag 5 (NULL) with zero length
        DerValue invalidVal = null;
        try {
            invalidVal = new DerValue(invalidOidBytes);
        } catch (IOException e) {
            // Should not throw here, DerValue constructor accepts this
            throw new RuntimeException("Unexpected IOException constructing DerValue", e);
        }

        try {
            new CertificatePolicyId(invalidVal);
            throw new RuntimeException("Expected IOException not thrown for invalid DerValue tag");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
