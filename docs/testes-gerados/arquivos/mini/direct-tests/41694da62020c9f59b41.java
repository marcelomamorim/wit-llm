/*
 * @test
 * @summary Test java.security.Signature update(byte[] data) method
 * @run main SignatureWitupTest
 */

import java.security.Signature;
import java.security.SignatureException;

public class SignatureWitupTest {
    public static void main(String[] args) throws Exception {
        // Test 1: Normal usage with valid data
        Signature sig = Signature.getInstance("SHA256withDSA");
        byte[] data = {1, 2, 3, 4, 5};
        try {
            sig.update(data);
        } catch (SignatureException e) {
            throw new RuntimeException("Unexpected SignatureException on valid update", e);
        }

        // Test 2: Null data should throw NullPointerException
        try {
            sig.update((byte[]) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        } catch (SignatureException se) {
            throw new RuntimeException("Expected NullPointerException but got SignatureException", se);
        }

        // Test 3: Empty array should not throw exception
        try {
            sig.update(new byte[0]);
        } catch (SignatureException e) {
            throw new RuntimeException("Unexpected SignatureException on empty array", e);
        }
    }
}
