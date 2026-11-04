/*
 * @test
 * @summary Tests for java.security.Signature.update(byte[])
 * @run main SignatureWitupTest
 */

import java.security.Signature;
import java.security.SignatureException;

public class SignatureWitupTest {

    public static void main(String[] args) {
        testUpdateWithNullDataThrowsIllegalArgumentException();
        testUpdateWithValidDataDoesNotThrow();
        testUpdateWithInvalidStateThrowsSignatureException();
    }

    private static void testUpdateWithNullDataThrowsIllegalArgumentException() {
        Signature sig = null;
        try {
            sig = Signature.getInstance("SHA256withRSA");
        } catch (Exception e) {
            // Fail the test if exception occurs during getInstance
            throw new RuntimeException("Failed to get Signature instance", e);
        }
        try {
            sig.update((byte[]) null);
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException for null data");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (SignatureException e) {
            // Not expected here
            throw new AssertionError("Unexpected SignatureException", e);
        }
    }

    private static void testUpdateWithValidDataDoesNotThrow() {
        Signature sig = null;
        try {
            sig = Signature.getInstance("SHA256withRSA");
            // Initialize for signing with a dummy private key (not shown here, assuming a valid key is available)
            // For the purpose of this test, we skip actual signing setup
            // and focus on calling update with valid data
            byte[] data = {1, 2, 3, 4};
            sig.update(data);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during valid update", e);
        }
    }

    private static void testUpdateWithInvalidStateThrowsSignatureException() {
        Signature sig = null;
        try {
            sig = Signature.getInstance("SHA256withRSA");
            // Simulate invalid state by not initializing for sign or verify
            // For this test, we assume the Signature object is in an invalid state
            // Since actual state manipulation is complex, we simulate by calling update after a reset
            // or by mocking, but here we just call update directly to observe behavior
            byte[] data = {5, 6, 7};
            sig.update(data);
            // If no exception, test fails
            throw new AssertionError("Expected SignatureException due to invalid state");
        } catch (SignatureException e) {
            // Expected exception
        } catch (Exception e) {
            // Other exceptions are not expected
            throw new AssertionError("Unexpected exception", e);
        }
    }
}
