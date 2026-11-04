/*
 * @test
 * @summary WITUP-generated tests for CertificatePoliciesExtension.get(String)
 * @modules java.base/sun.security.x509
 * @run main CertificatePoliciesExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sun.security.x509.CertificatePoliciesExtension;
import sun.security.x509.PolicyInformation;

public class CertificatePoliciesExtensionWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        List<PolicyInformation> policies = new ArrayList<PolicyInformation>();
        CertificatePoliciesExtension ext =
                new CertificatePoliciesExtension(Boolean.FALSE, policies);

        List<PolicyInformation> result = ext.get(CertificatePoliciesExtension.POLICIES);
        check(result != null, "get(policies) returned null");
        check(result.isEmpty(), "get(policies) should return empty list");
        check(result.equals(policies), "get(policies) should match supplied list");

        result = ext.get("POLICIES");
        check(result != null, "get(POLICIES) returned null");
        check(result.equals(policies), "get(POLICIES) should ignore case");

        checkIOException(ext, "unknown");
        checkIOException(ext, "");
        checkIOException(ext, "x509.info.extensions.CertificatePolicies");

        boolean npe = false;
        try {
            ext.get(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "get(null) should throw NullPointerException");
    }

    static void checkIOException(CertificatePoliciesExtension ext, String name) {
        boolean thrown = false;
        try {
            ext.get(name);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "get(\"" + name + "\") should throw IOException");
    }
}
