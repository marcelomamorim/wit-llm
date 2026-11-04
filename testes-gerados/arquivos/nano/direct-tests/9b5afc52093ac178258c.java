/* @test    @summary Test CertificatePoliciesExtension.set method with valid List input    @run main CertificatePoliciesExtensionWitupTest */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sun.security.x509.CertificatePoliciesExtension;
import sun.security.x509.PolicyInformation;

public class CertificatePoliciesExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidList();
        testSetWithInvalidName();
        testSetWithInvalidObjectType();
    }

    static void testSetWithValidList() throws IOException {
        List<PolicyInformation> policies = new ArrayList<>();
        policies.add(new PolicyInformation("1.2.3.4"));
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension();
        ext.set("policies", policies);
        // No exception means success
        System.out.println("testSetWithValidList passed");
    }

    static void testSetWithInvalidName() {
        List<PolicyInformation> policies = new ArrayList<>();
        policies.add(new PolicyInformation("1.2.3.4"));
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension();
        try {
            ext.set("invalidName", policies);
            throw new AssertionError("Expected IOException for invalid name");
        } catch (IOException e) {
            // Expected exception
            System.out.println("testSetWithInvalidName passed");
        }
    }

    static void testSetWithInvalidObjectType() {
        List<PolicyInformation> policies = new ArrayList<>();
        policies.add(new PolicyInformation("1.2.3.4"));
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension();
        try {
            ext.set("policies", "not a list");
            throw new AssertionError("Expected IOException for invalid object type");
        } catch (IOException e) {
            // Expected exception
            System.out.println("testSetWithInvalidObjectType passed");
        }
    }
}
