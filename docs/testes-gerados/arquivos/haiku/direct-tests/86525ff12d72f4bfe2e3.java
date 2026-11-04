/*
 * @test
 * @summary CertificatePolicyMap.encode must fail with NullPointerException when policy identifiers are null
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificatePolicyMapWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.x509.CertificatePolicyMap;

public class CertificatePolicyMapWitupTest {
    public static void main(String[] args) throws Exception {
        CertificatePolicyMap map = new CertificatePolicyMap(null, null);

        if (map.getIssuerIdentifier() != null) {
            throw new RuntimeException("issuer identifier should be null");
        }
        if (map.getSubjectIdentifier() != null) {
            throw new RuntimeException("subject identifier should be null");
        }

        boolean npe = false;
        try {
            map.encode(new DerOutputStream());
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("encode() with null identifiers should throw NullPointerException");
        }
    }
}
