/* @test    @summary Test for CertificatePoliciesExtension.get(String) method behavior.    @run main CertificatePoliciesExtensionWitupTest */

import sun.security.x509.CertificatePoliciesExtension;
import sun.security.x509.PolicyInformation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CertificatePoliciesExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidPolicyName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidPolicyName() throws IOException {
        List<PolicyInformation> policies = new ArrayList<>();
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(policies);
        List<PolicyInformation> result = ext.get("policies");
        if (result != policies) {
            throw new AssertionError("Expected to retrieve the same policies list.");
        }
    }

    private static void testGetWithInvalidName() {
        List<PolicyInformation> policies = new ArrayList<>();
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(policies);
        try {
            ext.get("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name.");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        }
    }
}
