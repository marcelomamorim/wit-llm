/* @test    @summary Test get method of CertificatePoliciesExtension for valid and invalid attribute names    @run main CertificatePoliciesExtensionWitupTest */

import java.io.IOException;
import java.util.*;
import sun.security.x509.CertificatePoliciesExtension;
import sun.security.x509.PolicyInformation;

public class CertificatePoliciesExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        List<PolicyInformation> policies = new ArrayList<>();
        // Dummy PolicyInformation for testing
        PolicyInformation policyInfo = new PolicyInformation("1.2.3.4");
        policies.add(policyInfo);
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(policies);
        List<PolicyInformation> result = ext.get("policies");
        assert result != null : "Expected non-null result";
        assert result.size() == 1 : "Expected list size 1";
        assert result.get(0).equals(policyInfo) : "PolicyInformation does not match";
    }

    private static void testGetWithInvalidName() {
        List<PolicyInformation> policies = new ArrayList<>();
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(policies);
        try {
            ext.get("invalidName");
            assert false : "Expected IOException for invalid attribute name";
        } catch (IOException e) {
            String msg = e.getMessage();
            assert msg.contains("Attribute name") : "Exception message should mention attribute name";
            assert msg.contains("not recognized") : "Exception message should mention not recognized";
        }
    }
}
