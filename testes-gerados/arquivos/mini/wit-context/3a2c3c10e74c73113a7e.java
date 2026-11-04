/*
 * @test
 * @summary Test CertificatePoliciesExtension.get(String) method behavior
 * @run main CertificatePoliciesExtensionWitupTest
 */

import sun.security.x509.CertificatePoliciesExtension;
import sun.security.x509.PolicyInformation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CertificatePoliciesExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        List<PolicyInformation> policies = new ArrayList<>();
        // We do not add any PolicyInformation because construction is unclear,
        // but an empty list is valid and observable.
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(policies);

        List<PolicyInformation> result = ext.get("policies");
        if (result == null) {
            throw new RuntimeException("Expected non-null list for 'policies' name");
        }
        if (result != policies) {
            throw new RuntimeException("Expected the same list instance to be returned");
        }
    }

    private static void testGetWithInvalidName() {
        List<PolicyInformation> policies = new ArrayList<>();
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(policies);

        try {
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
