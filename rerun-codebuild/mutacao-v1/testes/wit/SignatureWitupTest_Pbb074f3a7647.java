/* @test    @summary Test for Signature.sign() method to verify exception behavior when not initialized for signing    @run main SignatureWitupTest_Pbb074f3a7647 */

import java.security.Signature;
import java.security.SignatureException;

public class SignatureWitupTest_Pbb074f3a7647 {

    public static void main(String[] args) {
        testSignWithoutInitialization();
    }

    public static void testSignWithoutInitialization() {
        Signature signature = null;
        try {
            // Attempt to get an instance with a common algorithm
            signature = Signature.getInstance("SHA256withRSA");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Signature instance", e);
        }
        // Do not initialize the signature for signing
        // Call sign() expecting SignatureException
        try {
            byte[] result = signature.sign();
            // If no exception thrown, fail the test
            throw new AssertionError("Expected SignatureException was not thrown");
        } catch (SignatureException e) {
            // Verify that the exception message contains expected text
            if (!e.getMessage().contains("object not initialized for signing")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            // Test passes if SignatureException is thrown with correct message
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
