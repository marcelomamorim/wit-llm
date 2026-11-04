/*
 * @test
 * @summary Test sun.security.x509.CertificatePoliciesExtension.set method behavior
 * @run main CertificatePoliciesExtensionWitupTest
 */

import sun.security.x509.CertificatePoliciesExtension;
import sun.security.x509.PolicyInformation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CertificatePoliciesExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidPoliciesList();
        testSetWithInvalidObjectType();
        testSetWithUnrecognizedName();
    }

    private static void testSetWithValidPoliciesList() throws Exception {
        List<PolicyInformation> policies = new ArrayList<>();
        // PolicyInformation has no public constructor visible here, so create a dummy subclass for testing
        PolicyInformation dummyPolicy = new PolicyInformation() {
            public void encode(java.io.OutputStream out) throws IOException {}
        };
        policies.add(dummyPolicy);

        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(policies);
        // Now set with the same list using set method
        ext.set("policies", policies);
        // If no exception, test passes
    }

    private static void testSetWithInvalidObjectType() {
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(new ArrayList<PolicyInformation>());
        try {
            ext.set("policies", "not a list");
            throw new RuntimeException("Expected IOException not thrown for invalid object type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.equals("Attribute value should be of type List.")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testSetWithUnrecognizedName() {
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(new ArrayList<PolicyInformation>());
        try {
            ext.set("unknownName", new ArrayList<PolicyInformation>());
            throw new RuntimeException("Expected IOException not thrown for unrecognized name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.startsWith("Attribute name [unknownName] not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
