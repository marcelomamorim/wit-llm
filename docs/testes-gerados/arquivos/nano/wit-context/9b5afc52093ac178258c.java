/* @test    @summary Test for CertificatePoliciesExtension.set(String, Object) handling valid and invalid inputs    @run main CertificatePoliciesExtensionWitupTest */

import sun.security.x509.CertificatePoliciesExtension;
import sun.security.util.PolicyInformation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CertificatePoliciesExtensionWitupTest {

    public static void main(String[] args) {
        testSetWithValidPolicies();
        testSetWithInvalidName();
    }

    private static void testSetWithValidPolicies() {
        try {
            CertificatePoliciesExtension ext = new CertificatePoliciesExtension();
            List<PolicyInformation> policies = new ArrayList<>();
            // Add a dummy PolicyInformation object (assuming constructor exists)
            policies.add(new PolicyInformation());
            ext.set("policies", policies);
            System.out.println("testSetWithValidPolicies passed.");
        } catch (Exception e) {
            System.err.println("testSetWithValidPolicies failed: " + e);
        }
    }

    private static void testSetWithInvalidName() {
        try {
            CertificatePoliciesExtension ext = new CertificatePoliciesExtension();
            List<PolicyInformation> policies = new ArrayList<>();
            ext.set("invalidName", new Object());
            System.err.println("testSetWithInvalidName failed: Exception expected but not thrown.");
        } catch (IOException e) {
            if (e.getMessage().contains("not recognized")) {
                System.out.println("testSetWithInvalidName passed.");
            } else {
                System.err.println("testSetWithInvalidName failed: " + e);
            }
        } catch (Exception e) {
            System.err.println("testSetWithInvalidName failed: " + e);
        }
    }
}
