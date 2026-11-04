/* @test    @summary Test delete method of PolicyInformation for expected behaviors and exceptions    @run main PolicyInformationWitupTest */

import java.io.IOException;
import java.util.Collections;
import sun.security.x509.PolicyInformation;

public class PolicyInformationWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteQualifiers();
        testDeleteId();
        testDeleteUnrecognized();
    }

    private static void testDeleteQualifiers() throws Exception {
        // Setup: create PolicyInformation with dummy data
        PolicyInformation pi = createDummyPolicyInformation();
        // Call delete with "qualifiers" (case-insensitive)
        pi.delete("qualifiers");
        // Verify: policyQualifiers should be empty set
        assert pi.policyQualifiers.isEmpty() : "Qualifiers should be empty after delete";
    }

    private static void testDeleteId() throws Exception {
        PolicyInformation pi = createDummyPolicyInformation();
        boolean exceptionThrown = false;
        try {
            pi.delete("id");
        } catch (IOException e) {
            exceptionThrown = true;
            assert e.getMessage().contains("Attribute ID may not be deleted") : "Unexpected message: " + e.getMessage();
        }
        assert exceptionThrown : "Expected IOException when deleting id";
    }

    private static void testDeleteUnrecognized() throws Exception {
        PolicyInformation pi = createDummyPolicyInformation();
        boolean exceptionThrown = false;
        String unrecognizedName = "unknownAttribute";
        try {
            pi.delete(unrecognizedName);
        } catch (IOException e) {
            exceptionThrown = true;
            assert e.getMessage().contains(unrecognizedName) : "Unexpected message: " + e.getMessage();
        }
        assert exceptionThrown : "Expected IOException for unrecognized attribute";
    }

    private static PolicyInformation createDummyPolicyInformation() throws IOException {
        // Create dummy policyIdentifier (null for simplicity, as constructor requires it)
        // Since constructor requires a valid CertificatePolicyId, which is not provided,
        // we will simulate with a minimal mock or a dummy subclass if needed.
        // For simplicity, assume a minimal dummy implementation or mock.
        // Here, we will create a minimal anonymous subclass with dummy behavior.
        return new PolicyInformation(null, Collections.singleton(new sun.security.cert.PolicyQualifierInfo(new sun.security.util.DerValue(new byte[] {0x05, 0x00})))) {
            // Override to avoid null issues if needed
        };
    }
}
