/*
 * @test
 * @summary Tests the ChaCha20ParameterSpec constructor: null and wrong-length nonce handling, counter storage, and defensive copying of the nonce
 * @run main ChaCha20ParameterSpecWitupTest
 */

import java.util.Arrays;
import javax.crypto.spec.ChaCha20ParameterSpec;

public class ChaCha20ParameterSpecWitupTest {

    public static void main(String[] args) throws Exception {
        testNullNonce();
        testWrongNonceLengths();
        testValidNonceAndCounter();
        testInputArrayIsCopied();
        testOutputArrayIsCopied();
    }

    static void testNullNonce() {
        try {
            new ChaCha20ParameterSpec(null, 1);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null nonce");
    }

    static void testWrongNonceLengths() {
        int[] lengths = {0, 11, 13, 16};
        for (int len : lengths) {
            try {
                new ChaCha20ParameterSpec(new byte[len], 0);
            } catch (IllegalArgumentException e) {
                continue;
            }
            throw new RuntimeException("Expected IllegalArgumentException for length " + len);
        }
    }

    static void testValidNonceAndCounter() {
        byte[] nonce = new byte[12];
        for (int i = 0; i < nonce.length; i++) {
            nonce[i] = (byte) (i + 1);
        }
        int[] counters = {0, 1, -7, Integer.MAX_VALUE, Integer.MIN_VALUE};
        for (int c : counters) {
            ChaCha20ParameterSpec spec = new ChaCha20ParameterSpec(nonce, c);
            if (spec.getCounter() != c) {
                throw new RuntimeException("Counter mismatch: expected " + c
                        + " got " + spec.getCounter());
            }
            if (!Arrays.equals(nonce, spec.getNonce())) {
                throw new RuntimeException("Nonce mismatch for counter " + c);
            }
        }
    }

    static void testInputArrayIsCopied() {
        byte[] nonce = new byte[12];
        ChaCha20ParameterSpec spec = new ChaCha20ParameterSpec(nonce, 3);
        nonce[0] = (byte) 0x7F;
        nonce[11] = (byte) 0x55;
        byte[] got = spec.getNonce();
        if (got[0] != 0 || got[11] != 0) {
            throw new RuntimeException("Constructor did not copy the nonce array");
        }
    }

    static void testOutputArrayIsCopied() {
        byte[] nonce = new byte[12];
        nonce[5] = 9;
        ChaCha20ParameterSpec spec = new ChaCha20ParameterSpec(nonce, 0);
        byte[] first = spec.getNonce();
        first[5] = 42;
        byte[] second = spec.getNonce();
        if (second[5] != 9) {
            throw new RuntimeException("getNonce returned a shared internal array");
        }
        if (first == second) {
            throw new RuntimeException("getNonce must return a new array on each call");
        }
    }
}
