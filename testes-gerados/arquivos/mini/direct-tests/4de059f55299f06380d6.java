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
        testNullNonceThrows();
        testInvalidNonceLengthThrows();
    }

    private static void testValidConstructorAndAccessors() {
        byte[] nonce = new byte[12];
        for (int i = 0; i < nonce.length; i++) {
            nonce[i] = (byte) i;
        }
        int counter = 12345;

        ChaCha20ParameterSpec spec = new ChaCha20ParameterSpec(nonce, counter);

        // Check that getCounter returns the same counter
        if (spec.getCounter() != counter) {
            throw new RuntimeException("Counter mismatch: expected " + counter + " but got " + spec.getCounter());
        }

        // Check that getNonce returns a copy equal to the original nonce
        byte[] returnedNonce = spec.getNonce();
        if (!Arrays.equals(returnedNonce, nonce)) {
            throw new RuntimeException("Nonce mismatch: expected " + Arrays.toString(nonce) + " but got " + Arrays.toString(returnedNonce));
        }

        // Check that modifying returned nonce does not affect internal nonce
        returnedNonce[0] = (byte) 99;
        byte[] returnedNonce2 = spec.getNonce();
        if (returnedNonce2[0] == (byte) 99) {
            throw new RuntimeException("Nonce array is not cloned on getNonce()");
        }

        // Check that modifying original nonce after construction does not affect internal nonce
        nonce[1] = (byte) 88;
        byte[] returnedNonce3 = spec.getNonce();
        if (returnedNonce3[1] == (byte) 88) {
            throw new RuntimeException("Nonce array is not cloned on construction");
        }
    }

    private static void testNullNonceThrows() {
        try {
            new ChaCha20ParameterSpec(null, 0);
            throw new RuntimeException("Expected NullPointerException for null nonce");
        } catch (NullPointerException e) {
            // Expected
            if (!"Nonce must be non-null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + e.getMessage());
            }
        }
    }

    private static void testInvalidNonceLengthThrows() {
        byte[] tooShort = new byte[11];
        try {
            new ChaCha20ParameterSpec(tooShort, 0);
            throw new RuntimeException("Expected IllegalArgumentException for nonce length 11");
        } catch (IllegalArgumentException e) {
            if (!"Nonce must be 12-bytes in length".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IllegalArgumentException message for length 11: " + e.getMessage());
            }
        }

        byte[] tooLong = new byte[13];
        try {
            new ChaCha20ParameterSpec(tooLong, 0);
            throw new RuntimeException("Expected IllegalArgumentException for nonce length 13");
        } catch (IllegalArgumentException e) {
            if (!"Nonce must be 12-bytes in length".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IllegalArgumentException message for length 13: " + e.getMessage());
            }
        }
    }
}
