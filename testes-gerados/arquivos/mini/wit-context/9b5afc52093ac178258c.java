/*
 * @test
 * @summary Test CertificatePoliciesExtension.set(String,Object) for valid and invalid inputs
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
        testSetWithInvalidNameThrowsIOException();
        testSetWithInvalidObjectTypeThrowsIOException();
    }

    private static void testSetWithValidPoliciesList() throws Exception {
        List<PolicyInformation> policies = new ArrayList<>();
        // PolicyInformation is abstract or no public constructor visible, so use empty list
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(policies);
        // Should not throw
        ext.set("policies", policies);
    }

    private static void testSetWithInvalidNameThrowsIOException() {
        try {
            CertificatePoliciesExtension ext = new CertificatePoliciesExtension(new ArrayList<PolicyInformation>());
            ext.set("invalidName", new ArrayList<PolicyInformation>());
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    private static void testSetWithInvalidObjectTypeThrowsIOException() {
        try {
            CertificatePoliciesExtension ext = new CertificatePoliciesExtension(new ArrayList<PolicyInformation>());
            ext.set("policies", "not a list");
            throw new RuntimeException("Expected IOException not thrown for invalid object type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("should be of type List")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
