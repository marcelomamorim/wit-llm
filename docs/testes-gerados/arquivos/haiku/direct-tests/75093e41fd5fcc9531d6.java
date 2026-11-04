/*
 * @test
 * @summary RC5ParameterSpec 4-arg constructor validates and copies the IV
 * @run main RC5ParameterSpecWitupTest
 */

import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import javax.crypto.spec.RC5ParameterSpec;

public class RC5ParameterSpecWitupTest {

    public static void main(String[] args) {
        testNullIvRejected();
        testShortIvRejected();
        testValuesAndIvCopied();
        testIvDefensiveCopy();
        testExtraIvBytesIgnored();
        testIsAlgorithmParameterSpec();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullIvRejected() {
        try {
            new RC5ParameterSpec(1, 12, 32, null);
            throw new RuntimeException("null IV was accepted");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testShortIvRejected() {
        byte[] iv = new byte[7];
        try {
            new RC5ParameterSpec(1, 12, 32, iv);
            throw new RuntimeException("IV shorter than 2*(wordSize/8) was accepted");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testValuesAndIvCopied() {
        byte[] iv = new byte[] {1, 2, 3, 4, 5, 6, 7, 8};
        RC5ParameterSpec spec = new RC5ParameterSpec(1, 12, 32, iv);
        check(spec.getVersion() == 1, "unexpected version");
        check(spec.getRounds() == 12, "unexpected rounds");
        check(spec.getWordSize() == 32, "unexpected word size");
        check(Arrays.equals(iv, spec.getIV()), "IV content mismatch");
    }

    static void testIvDefensiveCopy() {
        byte[] iv = new byte[] {1, 2, 3, 4, 5, 6, 7, 8};
        RC5ParameterSpec spec = new RC5ParameterSpec(1, 12, 32, iv);
        iv[0] = (byte) 99;
        byte[] stored = spec.getIV();
        check(stored[0] == 1, "IV was not defensively copied");
    }

    static void testExtraIvBytesIgnored() {
        byte[] iv = new byte[16];
        for (int i = 0; i < iv.length; i++) {
            iv[i] = (byte) i;
        }
        RC5ParameterSpec spec = new RC5ParameterSpec(1, 12, 32, iv);
        byte[] stored = spec.getIV();
        check(stored.length == 8, "only first 2*(wordSize/8) bytes should be copied");
        check(Arrays.equals(Arrays.copyOf(iv, 8), stored), "copied IV prefix mismatch");
    }

    static void testIsAlgorithmParameterSpec() {
        RC5ParameterSpec spec = new RC5ParameterSpec(1, 12, 32, new byte[8]);
        AlgorithmParameterSpec aps = spec;
        check(aps == spec, "spec is not an AlgorithmParameterSpec");
    }
}
