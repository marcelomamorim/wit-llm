/*
 * @test
 * @summary Test CertificatePoliciesExtension.delete(String) method behavior
 * @run main CertificatePoliciesExtensionWitupTest
 */

import sun.security.x509.CertificatePoliciesExtension;
import sun.security.x509.PolicyInformation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CertificatePoliciesExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithPoliciesName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithPoliciesName() throws Exception {
        List<PolicyInformation> policies = new ArrayList<>();
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(policies);
        ext.delete("policies");
        // After deletion, certPolicies should be null
        // We check indirectly by calling delete again with "policies" which should not throw
        ext.delete("policies");
    }

    private static void testDeleteWithInvalidName() {
        List<PolicyInformation> policies = new ArrayList<>();
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(policies);
        try {
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
