/*
 * @test
 * @summary Test RC2ParameterSpec constructor with effectiveKeyBits, iv and offset
 * @run main javax.crypto.spec.RC2ParameterSpecWitupTest
 */

import javax.crypto.spec.RC2ParameterSpec;

public class RC2ParameterSpecWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testNullIV();
        testIVTooShort();
    }

    private static void testValidConstruction() {
        byte[] iv = new byte[16];
        for (int i = 0; i < iv.length; i++) {
            iv[i] = (byte) i;
        }
        int effectiveKeyBits = 40;
        int offset = 4;
        RC2ParameterSpec spec = new RC2ParameterSpec(effectiveKeyBits, iv, offset);
        // No exception means success
        if (spec == null) {
            throw new RuntimeException("RC2ParameterSpec construction returned null");
        }
    }

    private static void testNullIV() {
        try {
            new RC2ParameterSpec(40, null, 0);
            throw new RuntimeException("Expected IllegalArgumentException for null IV");
        } catch (IllegalArgumentException e) {
            if (!"IV missing".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testIVTooShort() {
        byte[] iv = new byte[7]; // less than 8 bytes
        try {
            new RC2ParameterSpec(40, iv, 0);
            throw new RuntimeException("Expected IllegalArgumentException for IV too short");
        } catch (IllegalArgumentException e) {
            if (!"IV too short".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }

        byte[] iv2 = new byte[10];
        try {
            new RC2ParameterSpec(40, iv2, 3); // 10 - 3 = 7 < 8
            throw new RuntimeException("Expected IllegalArgumentException for IV too short with offset");
        } catch (IllegalArgumentException e) {
            if (!"IV too short".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
