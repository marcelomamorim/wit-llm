/*
 * @test
 * @summary Tests the constructor of ChaCha20ParameterSpec for null input.
 * @run main ChaCha20ParameterSpecWitupTest
 */

import javax.crypto.spec.ChaCha20ParameterSpec;
import java.util.Arrays;

public class ChaCha20ParameterSpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullNonce();
        testConstructorWithInvalidNonceLength();
        testConstructorWithValidInputs();
    }

    private static void testConstructorWithNullNonce() {
        boolean exceptionThrown = false;
        try {
            new ChaCha20ParameterSpec(null, 0);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null nonce");
        }
    }

    private static void testConstructorWithInvalidNonceLength() {
        boolean exceptionThrown = false;
        byte[] invalidNonce = new byte[11]; // less than 12
        try {
            new ChaCha20ParameterSpec(invalidNonce, 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid nonce length");
        }
    }

    private static void testConstructorWithValidInputs() {
        byte[] validNonce = new byte[12];
        Arrays.fill(validNonce, (byte) 1);
        int counter = 42;
        ChaCha20ParameterSpec spec = new ChaCha20ParameterSpec(validNonce, counter);
        if (spec.getCounter() != counter) {
            throw new AssertionError("Counter value mismatch");
        }
        byte[] nonceCopy = spec.getNonce();
        if (!Arrays.equals(nonceCopy, validNonce)) {
            throw new AssertionError("Nonce value mismatch");
        }
        if (nonceCopy == validNonce) {
            throw new AssertionError("getNonce() should return a new array");
        }
    }
}
