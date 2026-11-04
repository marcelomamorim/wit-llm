/* @test    @summary Test CertificatePolicyId constructor with valid DerValue input    @run main CertificatePolicyIdWitupTest */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.CertificatePolicyId;

public class CertificatePolicyIdWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidDerValue();
        testEqualsAndHashCode();
        testToString();
        testEncodeMethod();
    }

    private static void testConstructorWithValidDerValue() throws IOException {
        // Create a DerValue with a known OID string
        String oidString = "1.2.3.4";
        DerValue derValue = new DerValue(oidString.getBytes());
        // Wrap the bytes as a DER-encoded OID
        derValue = new DerValue(DerValue.tag_OID, oidString.getBytes());
        // Instantiate CertificatePolicyId with DerValue
        CertificatePolicyId cpid = new CertificatePolicyId(derValue);
        assert cpid.getIdentifier().toString().equals(oidString) : "OID mismatch";
    }

    private static void testEqualsAndHashCode() throws IOException {
        String oidString = "1.2.3.4";
        DerValue derValue1 = new DerValue(oidString.getBytes());
        DerValue derValue2 = new DerValue(oidString.getBytes());
        CertificatePolicyId cpid1 = new CertificatePolicyId(derValue1);
        CertificatePolicyId cpid2 = new CertificatePolicyId(derValue2);
        assert cpid1.equals(cpid2) : "Objects should be equal";
        assert cpid1.hashCode() == cpid2.hashCode() : "Hash codes should be equal";
    }

    private static void testToString() throws IOException {
        String oidString = "1.2.3.4";
        DerValue derValue = new DerValue(oidString.getBytes());
        CertificatePolicyId cpid = new CertificatePolicyId(derValue);
        String str = cpid.toString();
        assert str.contains(oidString) : "toString output should contain the OID";
    }

    private static void testEncodeMethod() throws IOException {
        String oidString = "1.2.3.4";
        DerValue derValue = new DerValue(oidString.getBytes());
        CertificatePolicyId cpid = new CertificatePolicyId(derValue);
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        sun.security.util.DerOutputStream dos = new sun.security.util.DerOutputStream(baos);
        cpid.encode(dos);
        byte[] encodedBytes = baos.toByteArray();
        assert encodedBytes != null && encodedBytes.length > 0 : "Encoded bytes should not be null or empty";
    }
}
