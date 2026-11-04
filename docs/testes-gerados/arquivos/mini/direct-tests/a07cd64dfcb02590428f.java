/*
 * @test
 * @summary Test RC2ParameterSpec constructor with effectiveKeyBits and iv
 * @run main RC2ParameterSpecWitupTest
 */

import javax.crypto.spec.RC2ParameterSpec;

public class RC2ParameterSpecWitupTest {
    public static void main(String[] args) {
        testValidIV();
        testNullIV();
        testShortIV();
    }

    private static void testValidIV() {
        byte[] iv = new byte[8];
        for (int i = 0; i < iv.length; i++) {
            iv[i] = (byte) i;
        }
        int effectiveKeyBits = 40;
        RC2ParameterSpec spec = new RC2ParameterSpec(effectiveKeyBits, iv);
        byte[] returnedIV = spec.getIV();
        if (returnedIV == null) {
            throw new RuntimeException("Returned IV is null");
        }
        if (returnedIV.length != 8) {
            throw new RuntimeException("Returned IV length is not 8");
        }
        for (int i = 0; i < 8; i++) {
            if (returnedIV[i] != iv[i]) {
                throw new RuntimeException("Returned IV does not match input IV at index " + i);
            }
        }
        if (spec.getEffectiveKeyBits() != effectiveKeyBits) {
            throw new RuntimeException("Effective key bits do not match");
        }
    }

    private static void testNullIV() {
        try {
            new RC2ParameterSpec(40, null);
            throw new RuntimeException("Expected IllegalArgumentException for null IV");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testShortIV() {
        byte[] shortIV = new byte[7];
        try {
            new RC2ParameterSpec(40, shortIV);
            throw new RuntimeException("Expected IllegalArgumentException for short IV");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
