/*
 * @test
 * @summary Test sun.security.x509.PolicyInformation.delete method behavior
 * @run main PolicyInformationWitupTest
 */

import sun.security.x509.PolicyInformation;
import sun.security.x509.CertificatePolicyId;
import java.security.cert.PolicyQualifierInfo;
import java.util.Collections;
import java.util.HashSet;
import java.io.IOException;

public class PolicyInformationWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteQualifiers();
        testDeleteIdThrows();
        testDeleteUnknownThrows();
    }

    private static void testDeleteQualifiers() throws Exception {
        CertificatePolicyId policyId = new CertificatePolicyId(new int[] {1,2,3});
        HashSet<PolicyQualifierInfo> qualifiers = new HashSet<>();
        PolicyInformation pi = new PolicyInformation(policyId, qualifiers);

        // Initially qualifiers is empty set
        pi.delete("qualifiers");

        // After delete("qualifiers"), qualifiers should be emptySet from Collections
        // We test by calling delete("qualifiers") again to ensure no exception and no change
        pi.delete("QUALIFIERS"); // case insensitive
    }

    private static void testDeleteIdThrows() throws Exception {
        CertificatePolicyId policyId = new CertificatePolicyId(new int[] {1,2,3});
        HashSet<PolicyQualifierInfo> qualifiers = new HashSet<>();
        PolicyInformation pi = new PolicyInformation(policyId, qualifiers);

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

    private static void testDeleteUnknownThrows() throws Exception {
        CertificatePolicyId policyId = new CertificatePolicyId(new int[] {1,2,3});
        HashSet<PolicyQualifierInfo> qualifiers = new HashSet<>();
        PolicyInformation pi = new PolicyInformation(policyId, qualifiers);

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
