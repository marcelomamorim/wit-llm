/* @test    @summary Test constructor of ChaCha20ParameterSpec for valid and invalid inputs    @run main ChaCha20ParameterSpecWitupTest */

import javax.crypto.spec.ChaCha20ParameterSpec;
import java.util.Arrays;

public class ChaCha20ParameterSpecWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNullNonce();
        testInvalidNonceLength();
    }

    private static void testValidConstructor() {
        byte[] nonce = new byte[12];
        for (int i = 0; i < nonce.length; i++) {
            nonce[i] = (byte) i;
        }
        int counter = 42;
        ChaCha20ParameterSpec spec = new ChaCha20ParameterSpec(nonce, counter);
        byte[] returnedNonce = spec.getNonce();
        int returnedCounter = spec.getCounter();
        if (!Arrays.equals(nonce, returnedNonce)) {
            throw new AssertionError("Nonce mismatch");
        }
        if (counter != returnedCounter) {
            throw new AssertionError("Counter mismatch");
        }
    }

    private static void testNullNonce() {
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

    private static void testInvalidNonceLength() {
        byte[] invalidNonce = new byte[10];
        boolean exceptionThrown = false;
        try {
            new ChaCha20ParameterSpec(invalidNonce, 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid nonce length");
        }
    }
}
