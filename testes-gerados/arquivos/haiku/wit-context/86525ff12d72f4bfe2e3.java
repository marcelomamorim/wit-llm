/*
 * @test
 * @summary CertificatePolicyMap.encode must fail with NullPointerException when the issuer or subject identifier is null
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificatePolicyMapWitupTest
 */
import java.io.IOException;

import sun.security.util.DerOutputStream;
import sun.security.x509.CertificatePolicyId;
import sun.security.x509.CertificatePolicyMap;

public class CertificatePolicyMapWitupTest {

    public static void main(String[] args) throws Exception {
        DerOutputStream out = new DerOutputStream();

        CertificatePolicyMap bothNull = new CertificatePolicyMap(
                (CertificatePolicyId) null, (CertificatePolicyId) null);
        expectNullPointerException(bothNull, out);

        CertificatePolicyMap subjectNull = new CertificatePolicyMap(
                null, (CertificatePolicyId) null);
        expectNullPointerException(subjectNull, out);
    }

    private static void expectNullPointerException(CertificatePolicyMap map,
                                                   DerOutputStream out) {
        try {
            map.encode(out);
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
        throw new RuntimeException("Expected NullPointerException was not thrown");
    }
}
