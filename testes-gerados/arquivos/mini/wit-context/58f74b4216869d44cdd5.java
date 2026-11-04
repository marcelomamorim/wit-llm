/*
 * @test
 * @summary Test sun.security.x509.PolicyInformation.get(String) method
 * @run main PolicyInformationWitupTest
 */

import sun.security.x509.PolicyInformation;
import sun.security.x509.CertificatePolicyId;
import java.security.cert.PolicyQualifierInfo;
import java.util.Set;
import java.util.LinkedHashSet;
import java.io.IOException;

public class PolicyInformationWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithId();
        testGetWithQualifiers();
        testGetWithInvalidName();
    }

    private static void testGetWithId() throws IOException {
        CertificatePolicyId id = new CertificatePolicyId(new int[] {1, 2, 3, 4});
        Set<PolicyQualifierInfo> qualifiers = new LinkedHashSet<>();
        PolicyInformation pi = new PolicyInformation(id, qualifiers);

        Object result = pi.get("id");
        if (!(result instanceof CertificatePolicyId)) {
            throw new RuntimeException("Expected CertificatePolicyId for 'id' key");
        }
        if (!result.equals(id)) {
            throw new RuntimeException("Returned CertificatePolicyId does not match the original");
        }
    }

    private static void testGetWithQualifiers() throws IOException {
        CertificatePolicyId id = new CertificatePolicyId(new int[] {1, 2, 3, 4});
        Set<PolicyQualifierInfo> qualifiers = new LinkedHashSet<>();
        PolicyInformation pi = new PolicyInformation(id, qualifiers);

        Object result = pi.get("qualifiers");
        if (!(result instanceof Set)) {
            throw new RuntimeException("Expected Set for 'qualifiers' key");
        }
        @SuppressWarnings("unchecked")
        Set<PolicyQualifierInfo> resultSet = (Set<PolicyQualifierInfo>) result;
        if (!resultSet.equals(qualifiers)) {
            throw new RuntimeException("Returned qualifiers Set does not match the original");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            CertificatePolicyId id = new CertificatePolicyId(new int[] {1, 2, 3, 4});
            Set<PolicyQualifierInfo> qualifiers = new LinkedHashSet<>();
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
