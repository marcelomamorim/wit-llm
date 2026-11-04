/*
 * @test
 * @summary Test sun.security.x509.CertificatePolicyMap.encode(DerOutputStream) method
 * @run main CertificatePolicyMapWitupTest
 */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.x509.CertificatePolicyId;
import sun.security.x509.CertificatePolicyMap;

public class CertificatePolicyMapWitupTest {
    public static void main(String[] args) throws Exception {
        // Create dummy CertificatePolicyId instances with minimal valid OID strings
        CertificatePolicyId issuer = new CertificatePolicyId("1.2.3.4");
        CertificatePolicyId subject = new CertificatePolicyId("1.2.3.5");

        CertificatePolicyMap map = new CertificatePolicyMap(issuer, subject);

        DerOutputStream out = new DerOutputStream();

        // Call encode and verify it completes without throwing IOException
        map.encode(out);

        // Verify that output is not empty
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }

        // Verify toString contains issuer and subject OIDs
        String str = map.toString();
        if (!str.contains("1.2.3.4") || !str.contains("1.2.3.5")) {
            throw new RuntimeException("toString() does not contain expected OIDs");
        }

        // Verify getIssuerIdentifier and getSubjectIdentifier return the same objects
        if (map.getIssuerIdentifier() != issuer) {
            throw new RuntimeException("getIssuerIdentifier() does not return expected object");
        }
        if (map.getSubjectIdentifier() != subject) {
            throw new RuntimeException("getSubjectIdentifier() does not return expected object");
        }

        System.out.println("Test passed");
    }
}
