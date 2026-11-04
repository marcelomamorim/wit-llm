/*
 * @test
 * @summary Test ChaCha20ParameterSpec constructor and accessors
 * @run main javax.crypto.spec.ChaCha20ParameterSpecWitupTest
 */

import javax.crypto.spec.ChaCha20ParameterSpec;
import java.util.Arrays;

public class ChaCha20ParameterSpecWitupTest {
    public static void main(String[] args) {
        testValidConstructorAndAccessors();
        testConstructorNullNonce();
        testConstructorInvalidNonceLength();
    }

    private static void testValidConstructorAndAccessors() {
        byte[] nonce = new byte[12];
        for (int i = 0; i < nonce.length; i++) {
            nonce[i] = (byte) i;
        }
        int counter = 123456789;
        ChaCha20ParameterSpec spec = new ChaCha20ParameterSpec(nonce, counter);

        // Check that getCounter returns the same counter
        if (spec.getCounter() != counter) {
            throw new AssertionError("Counter mismatch");
        }

        // Check that getNonce returns a copy equal to the original nonce
        byte[] returnedNonce = spec.getNonce();
        if (!Arrays.equals(returnedNonce, nonce)) {
            throw new AssertionError("Nonce returned does not match original");
        }

        // Check that modifying returned nonce does not affect internal state
        returnedNonce[0] = (byte) 99;
        byte[] returnedNonce2 = spec.getNonce();
        if (returnedNonce2[0] == (byte) 99) {
            throw new AssertionError("Nonce array was not cloned on getNonce");
        }

        // Check that modifying original nonce after construction does not affect internal state
        nonce[0] = (byte) 77;
        byte[] returnedNonce3 = spec.getNonce();
        if (returnedNonce3[0] == (byte) 77) {
            throw new AssertionError("Nonce array was not cloned on construction");
        }
    }

    private static void testConstructorNullNonce() {
        try {
            new ChaCha20ParameterSpec(null, 0);
            throw new AssertionError("Expected NullPointerException for null nonce");
        } catch (NullPointerException e) {
            // Expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Nonce must be non-null")) {
                throw new AssertionError("NullPointerException message unexpected: " + msg);
            }
        }
    }

    private static void testConstructorInvalidNonceLength() {
        byte[] shortNonce = new byte[11];
        try {
            new ChaCha20ParameterSpec(shortNonce, 0);
            throw new AssertionError("Expected IllegalArgumentException for nonce length != 12");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Nonce must be 12-bytes in length")) {
                throw new AssertionError("IllegalArgumentException message unexpected: " + msg);
            }
        }

        byte[] longNonce = new byte[13];
        try {
            new ChaCha20ParameterSpec(longNonce, 0);
            throw new AssertionError("Expected IllegalArgumentException for nonce length != 12");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Nonce must be 12-bytes in length")) {
                throw new AssertionError("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }
}
