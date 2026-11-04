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
        testGetPoliciesReturnsSameList();
        testGetWithInvalidNameThrowsIOException();
    }

    private static void testGetPoliciesReturnsSameList() throws IOException {
        List<PolicyInformation> policies = new ArrayList<>();
        // PolicyInformation has no public constructor visible here, so use null list to test behavior
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(policies);

        List<PolicyInformation> result = ext.get("policies");

        if (result != policies) {
            throw new RuntimeException("get(\"policies\") did not return the original list instance");
        }

        // Also test case-insensitivity
        List<PolicyInformation> result2 = ext.get("PoLiCiEs");
        if (result2 != policies) {
            throw new RuntimeException("get(\"PoLiCiEs\") did not return the original list instance");
        }
    }

    private static void testGetWithInvalidNameThrowsIOException() {
        List<PolicyInformation> policies = new ArrayList<>();
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(policies);

        try {
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("invalidName") || !msg.contains("CertificatePoliciesExtension")) {
                throw new RuntimeException("IOException message does not contain expected text: " + msg);
            }
        }
    }
}
