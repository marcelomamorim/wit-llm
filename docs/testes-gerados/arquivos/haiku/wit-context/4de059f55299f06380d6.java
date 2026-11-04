/*
 * @test
 * @summary Witup generated tests for the ChaCha20ParameterSpec constructor
 * @run main ChaCha20ParameterSpecWitupTest
 */

import javax.crypto.spec.ChaCha20ParameterSpec;

public class ChaCha20ParameterSpecWitupTest {

    public static void main(String[] args) throws Exception {
        testNullNonceThrowsNullPointerException();
        testWrongNonceLengthThrowsIllegalArgumentException();
        testValidNonceAndCounterAreObservable();
        testNonceIsCopiedFromInput();
        testGetNonceReturnsNewArrayEachCall();
        testNegativeCounterIsPreserved();
    }

    static void testNullNonceThrowsNullPointerException() {
        try {
            new ChaCha20ParameterSpec(null, 1);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null nonce");
    }

    static void testWrongNonceLengthThrowsIllegalArgumentException() {
        int[] lengths = {0, 1, 11, 13, 16};
        for (int len : lengths) {
            try {
                new ChaCha20ParameterSpec(new byte[len], 0);
            } catch (IllegalArgumentException e) {
                continue;
            }
            throw new RuntimeException("expected IllegalArgumentException for length " + len);
        }
    }

    static void testValidNonceAndCounterAreObservable() {
        byte[] nonce = new byte[12];
        for (int i = 0; i < nonce.length; i++) {
            nonce[i] = (byte) (i + 1);
        }
        ChaCha20ParameterSpec spec = new ChaCha20ParameterSpec(nonce, 42);
        if (spec.getCounter() != 42) {
            throw new RuntimeException("unexpected counter: " + spec.getCounter());
        }
        byte[] got = spec.getNonce();
        if (got.length != 12) {
            throw new RuntimeException("unexpected nonce length: " + got.length);
        }
        for (int i = 0; i < nonce.length; i++) {
            if (got[i] != nonce[i]) {
                throw new RuntimeException("nonce mismatch at index " + i);
            }
        }
    }

    static void testNonceIsCopiedFromInput() {
        byte[] nonce = new byte[12];
        ChaCha20ParameterSpec spec = new ChaCha20ParameterSpec(nonce, 7);
        nonce[0] = (byte) 0x55;
        byte[] got = spec.getNonce();
        if (got[0] != 0) {
            throw new RuntimeException("constructor did not copy nonce from input");
        }
    }

    static void testGetNonceReturnsNewArrayEachCall() {
        ChaCha20ParameterSpec spec = new ChaCha20ParameterSpec(new byte[12], 0);
        byte[] first = spec.getNonce();
        byte[] second = spec.getNonce();
        if (first == second) {
            throw new RuntimeException("getNonce returned the same array instance");
        }
        first[0] = (byte) 0x7F;
        if (spec.getNonce()[0] != 0) {
            throw new RuntimeException("modifying returned nonce affected the spec");
        }
    }

    static void testNegativeCounterIsPreserved() {
        ChaCha20ParameterSpec spec = new ChaCha20ParameterSpec(new byte[12], -1);
        if (spec.getCounter() != -1) {
            throw new RuntimeException("unexpected counter: " + spec.getCounter());
        }
    }
}
