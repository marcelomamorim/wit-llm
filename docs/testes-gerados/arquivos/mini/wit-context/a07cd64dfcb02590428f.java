/*
 * @test
 * @summary Test javax.crypto.spec.RC2ParameterSpec constructor with int and byte[] iv
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
        RC2ParameterSpec spec = new RC2ParameterSpec(128, iv);
        byte[] returnedIV = spec.getIV();
        if (returnedIV == null) {
            throw new RuntimeException("Returned IV should not be null");
        }
        if (returnedIV.length != 8) {
            throw new RuntimeException("Returned IV length should be 8");
        }
        for (int i = 0; i < 8; i++) {
            if (returnedIV[i] != iv[i]) {
                throw new RuntimeException("Returned IV content mismatch at index " + i);
            }
        }
    }

    private static void testNullIV() {
        try {
            new RC2ParameterSpec(128, null);
            throw new RuntimeException("Expected IllegalArgumentException for null IV");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testShortIV() {
        byte[] shortIV = new byte[7];
        try {
            new RC2ParameterSpec(128, shortIV);
            throw new RuntimeException("Expected IllegalArgumentException for short IV");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
