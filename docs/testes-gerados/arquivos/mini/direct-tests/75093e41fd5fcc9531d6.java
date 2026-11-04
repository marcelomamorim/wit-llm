/*
 * @test
 * @summary Test RC5ParameterSpec constructor with IV array
 * @run main RC5ParameterSpecWitupTest
 */

import javax.crypto.spec.RC5ParameterSpec;

public class RC5ParameterSpecWitupTest {
    public static void main(String[] args) {
        testValidIV();
        testNullIV();
        testShortIV();
    }

    private static void testValidIV() {
        int version = 1;
        int rounds = 12;
        int wordSize = 32; // bits
        byte[] iv = new byte[2 * (wordSize / 8)];
        for (int i = 0; i < iv.length; i++) {
            iv[i] = (byte) i;
        }
        RC5ParameterSpec spec = new RC5ParameterSpec(version, rounds, wordSize, iv);
        if (spec == null) {
            throw new RuntimeException("RC5ParameterSpec constructor returned null");
        }
    }

    private static void testNullIV() {
        try {
            new RC5ParameterSpec(1, 12, 32, null);
            throw new RuntimeException("Expected IllegalArgumentException for null IV");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testShortIV() {
        int version = 1;
        int rounds = 12;
        int wordSize = 32; // bits
        byte[] iv = new byte[2 * (wordSize / 8) - 1]; // one byte short
        try {
            new RC5ParameterSpec(version, rounds, wordSize, iv);
            throw new RuntimeException("Expected IllegalArgumentException for short IV");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
