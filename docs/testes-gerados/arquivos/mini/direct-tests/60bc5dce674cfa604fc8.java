/*
 * @test
 * @summary Test sun.security.x509.CertificatePoliciesExtension.delete method
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
        // PolicyInformation requires a valid OID string; use a dummy valid OID
        policies.add(new PolicyInformation(new sun.security.util.ObjectIdentifier("1.2.3.4")));

        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(policies);

        // Before delete, certPolicies should not be null
        if (ext.get(POLICIES) == null) {
            throw new RuntimeException("Expected policies before delete");
        }

        ext.delete("policies");

        // After delete, certPolicies should be null, so get(POLICIES) returns null
        if (ext.get(POLICIES) != null) {
            throw new RuntimeException("Expected policies to be null after delete");
        }

        // Also extensionValue should be null after delete (encoded value)
        if (ext.getExtensionValue() != null) {
            throw new RuntimeException("Expected extensionValue to be null after delete");
        }
    }

    private static void testDeleteWithInvalidName() {
        List<PolicyInformation> policies = new ArrayList<>();
        policies.add(new PolicyInformation(new sun.security.util.ObjectIdentifier("1.2.3.4")));

        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(policies);

        try {
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static final String POLICIES = "policies";
}
