/*
 * @test
 * @summary Test sun.security.x509.PolicyInformation.get(String) method
 * @run main PolicyInformationWitupTest
 */

import sun.security.x509.PolicyInformation;
import sun.security.x509.CertificatePolicyId;
import java.security.cert.PolicyQualifierInfo;
import java.util.Collections;
import java.util.Set;
import java.io.IOException;

public class PolicyInformationWitupTest {
    public static void main(String[] args) throws Exception {
        testGetIdReturnsPolicyIdentifier();
        testGetQualifiersReturnsPolicyQualifiers();
        testGetWithInvalidNameThrowsIOException();
    }

    private static void testGetIdReturnsPolicyIdentifier() throws IOException {
        CertificatePolicyId id = new CertificatePolicyId(new int[] {1, 2, 3});
        Set<PolicyQualifierInfo> qualifiers = Collections.emptySet();
        PolicyInformation pi = new PolicyInformation(id, qualifiers);

        Object result = pi.get("id");
        if (!(result instanceof CertificatePolicyId)) {
            throw new RuntimeException("Expected CertificatePolicyId instance for 'id'");
        }
        if (!result.equals(id)) {
            throw new RuntimeException("Returned policyIdentifier does not match the one set");
        }

        // Also test case-insensitivity
        Object resultUpper = pi.get("ID");
        if (!resultUpper.equals(id)) {
            throw new RuntimeException("Returned policyIdentifier does not match for uppercase 'ID'");
        }
    }

    private static void testGetQualifiersReturnsPolicyQualifiers() throws IOException {
        CertificatePolicyId id = new CertificatePolicyId(new int[] {1, 2, 3});
        Set<PolicyQualifierInfo> qualifiers = Collections.emptySet();
        PolicyInformation pi = new PolicyInformation(id, qualifiers);

        Object result = pi.get("qualifiers");
        if (!(result instanceof Set)) {
            throw new RuntimeException("Expected Set instance for 'qualifiers'");
        }
        if (!result.equals(qualifiers)) {
            throw new RuntimeException("Returned policyQualifiers does not match the one set");
        }

        // Also test case-insensitivity
        Object resultUpper = pi.get("QUALIFIERS");
        if (!resultUpper.equals(qualifiers)) {
            throw new RuntimeException("Returned policyQualifiers does not match for uppercase 'QUALIFIERS'");
        }
    }

    private static void testGetWithInvalidNameThrowsIOException() {
        try {
            CertificatePolicyId id = new CertificatePolicyId(new int[] {1, 2, 3});
            Set<PolicyQualifierInfo> qualifiers = Collections.emptySet();
            PolicyInformation pi = new PolicyInformation(id, qualifiers);

            pi.get("invalidName");
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
