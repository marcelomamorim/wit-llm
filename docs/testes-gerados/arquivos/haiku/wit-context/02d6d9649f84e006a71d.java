/*
 * @test
 * @summary WITUP-derived observable checks for CertificatePolicyId using the ObjectIdentifier constructor
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificatePolicyIdWitupTest
 */

import sun.security.x509.CertificatePolicyId;
import sun.security.util.ObjectIdentifier;

public class CertificatePolicyIdWitupTest {

    public static void main(String[] args) throws Exception {
        CertificatePolicyId id = new CertificatePolicyId((ObjectIdentifier) null);
        check(id.getIdentifier() == null, "getIdentifier should return the stored null identifier");
        check(!id.equals("not a policy id"), "equals must return false for non-CertificatePolicyId objects");
        check(!id.equals(null), "equals must return false for null");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
