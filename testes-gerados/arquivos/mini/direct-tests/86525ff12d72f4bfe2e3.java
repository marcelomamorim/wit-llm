/*
 * @test
 * @summary Test CertificatePolicyMap encode method correctness
 * @run main CertificatePolicyMapWitupTest
 */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.CertificatePolicyId;
import sun.security.x509.CertificatePolicyMap;

public class CertificatePolicyMapWitupTest {
    public static void main(String[] args) throws Exception {
        // Create two CertificatePolicyId instances with known OIDs
        CertificatePolicyId issuer = new CertificatePolicyId("1.2.3.4");
        CertificatePolicyId subject = new CertificatePolicyId("1.2.3.5");

        // Create CertificatePolicyMap with these ids
        CertificatePolicyMap map = new CertificatePolicyMap(issuer, subject);

        // Encode the map
        DerOutputStream out = new DerOutputStream();
        map.encode(out);

        // Decode the encoded bytes to verify correctness
        byte[] encoded = out.toByteArray();
        DerValue val = new DerValue(encoded);

        // The tag must be SEQUENCE
        if (val.tag != DerValue.tag_Sequence) {
            throw new RuntimeException("Encoded data is not a SEQUENCE");
        }

        // Construct a new CertificatePolicyMap from the encoded DerValue
        CertificatePolicyMap decodedMap = new CertificatePolicyMap(val);

        // Check that issuer and subject identifiers match
        if (!decodedMap.getIssuerIdentifier().equals(issuer)) {
            throw new RuntimeException("Issuer identifier mismatch after encode/decode");
        }
        if (!decodedMap.getSubjectIdentifier().equals(subject)) {
            throw new RuntimeException("Subject identifier mismatch after encode/decode");
        }

        // Check toString contains expected substrings
        String str = decodedMap.toString();
        if (!str.contains("IssuerDomain") || !str.contains("SubjectDomain")) {
            throw new RuntimeException("toString output missing expected labels");
        }

        System.out.println("Test passed");
    }
}
