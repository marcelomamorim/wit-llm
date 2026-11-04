/* @test    @summary Test the get method of PolicyInformation class for known attribute names.    @run main PolicyInformationWitupTest */

import sun.security.x509.PolicyInformation;
import java.io.IOException;
import java.security.cert.PolicyQualifierInfo;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PolicyInformationWitupTest {

    public static void main(String[] args) throws Exception {
        testGetPolicyIdentifier();
        testGetPolicyQualifiers();
        testGetUnknownAttribute();
    }

    private static void testGetPolicyIdentifier() throws Exception {
        // Create a dummy policy identifier
        PolicyInformation pi = createPolicyInformation();
        Object result = pi.get("id");
        if (result == null) {
            throw new AssertionError("Expected non-null result for 'id'");
        }
        if (!(result instanceof sun.security.x509.CertificatePolicyId)) {
            throw new AssertionError("Result is not of expected type");
        }
    }

    private static void testGetPolicyQualifiers() throws Exception {
        PolicyInformation pi = createPolicyInformation();
        Object result = pi.get("qualifiers");
        if (result == null) {
            throw new AssertionError("Expected non-null result for 'qualifiers'");
        }
        if (!(result instanceof Set)) {
            throw new AssertionError("Result is not of type Set");
        }
    }

    private static void testGetUnknownAttribute() throws Exception {
        PolicyInformation pi = createPolicyInformation();
        boolean exceptionThrown = false;
        try {
            pi.get("unknown");
        } catch (IOException e) {
            exceptionThrown = true;
            if (!e.getMessage().contains("not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException for unknown attribute name");
        }
    }

    private static PolicyInformation createPolicyInformation() throws IOException {
        // Create dummy policy identifier
        sun.security.x509.CertificatePolicyId policyId = null;
        try {
            Class<?> certPolicyIdClass = Class.forName("sun.security.x509.CertificatePolicyId");
            java.lang.reflect.Constructor<?> ctor = certPolicyIdClass.getConstructor(String.class);
            Object obj = ctor.newInstance("1.2.3.4");
            policyId = (sun.security.x509.CertificatePolicyId) obj;
        } catch (Exception e) {
            throw new IOException("Failed to create CertificatePolicyId", e);
        }
        // Create dummy policy qualifiers set
        Set<PolicyQualifierInfo> qualifiers = new HashSet<>();
        // For simplicity, leave empty
        return new PolicyInformation(policyId, qualifiers);
    }
}
