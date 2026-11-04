/*
 * @test
 * @summary Test sun.security.x509.PolicyInformation.delete(String) method behavior
 * @run main PolicyInformationWitupTest
 */

import sun.security.x509.PolicyInformation;
import sun.security.x509.CertificatePolicyId;
import java.security.cert.PolicyQualifierInfo;
import java.util.Collections;
import java.io.IOException;

public class PolicyInformationWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteQualifiers();
        testDeleteIdThrows();
        testDeleteUnknownNameThrows();
    }

    private static void testDeleteQualifiers() throws Exception {
        CertificatePolicyId policyId = new CertificatePolicyId(new byte[] {0x55, 0x04, 0x03});
        PolicyInformation pi = new PolicyInformation(policyId, Collections.emptySet());

        // delete("qualifiers") should clear policyQualifiers without exception
        pi.delete("qualifiers");
        // No direct getter for policyQualifiers, so no assert on internal state
        // Just ensure no exception thrown
    }

    private static void testDeleteIdThrows() throws Exception {
        CertificatePolicyId policyId = new CertificatePolicyId(new byte[] {0x55, 0x04, 0x03});
        PolicyInformation pi = new PolicyInformation(policyId, Collections.emptySet());

        try {
            pi.delete("id");
            throw new RuntimeException("Expected IOException not thrown for delete(\"id\")");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("ID may not be deleted")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testDeleteUnknownNameThrows() throws Exception {
        CertificatePolicyId policyId = new CertificatePolicyId(new byte[] {0x55, 0x04, 0x03});
        PolicyInformation pi = new PolicyInformation(policyId, Collections.emptySet());

        try {
            pi.delete("unknownAttribute");
            throw new RuntimeException("Expected IOException not thrown for delete(\"unknownAttribute\")");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
