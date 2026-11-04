/* @test    @summary Test delete method of CertificatePoliciesExtension class    @run main CertificatePoliciesExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.CertificatePoliciesExtension;

public class CertificatePoliciesExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidPolicies();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidPolicies() throws IOException {
        // Create an instance with dummy policies
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension();
        ext.certPolicies = java.util.Arrays.asList();
        ext.encodeThis();
        // Call delete with "policies"
        ext.delete("policies");
        // Verify that certPolicies is null after deletion
        assert ext.certPolicies == null : "certPolicies should be null after delete";
    }

    private static void testDeleteWithInvalidName() {
        // Create an instance
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension();
        ext.certPolicies = java.util.Arrays.asList();
        try {
            ext.delete("invalidName");
            assert false : "Expected IOException for invalid attribute name";
        } catch (IOException e) {
            assert e.getMessage().contains("not recognized") : "Exception message should indicate unrecognized attribute";
        }
    }
}
