/*
 * @test
 * @summary Test RC2ParameterSpec constructor with effectiveKeyBits, iv, and offset
 * @run main RC2ParameterSpecWitupTest
 */

import javax.crypto.spec.RC2ParameterSpec;

public class RC2ParameterSpecWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testNullIVThrows();
        testShortIVThrows();
    }

    private static void testValidConstruction() {
        byte[] iv = new byte[16];
        for (int i = 0; i < iv.length; i++) {
            iv[i] = (byte) i;
        }
        int effectiveKeyBits = 40;
        int offset = 4;
        RC2ParameterSpec spec = new RC2ParameterSpec(effectiveKeyBits, iv, offset);
        // Check that the IV copied is exactly iv[offset..offset+7]
        byte[] expectedIV = new byte[8];
        System.arraycopy(iv, offset, expectedIV, 0, 8);
        byte[] actualIV = spec.getIV();
        if (actualIV == null) {
            throw new RuntimeException("IV should not be null");
        }
        if (actualIV.length != 8) {
            throw new RuntimeException("IV length should be 8");
        }
        for (int i = 0; i < 8; i++) {
            if (actualIV[i] != expectedIV[i]) {
                throw new RuntimeException("IV byte mismatch at index " + i);
            }
        }
        if (spec.getEffectiveKeyBits() != effectiveKeyBits) {
            throw new RuntimeException("Effective key bits mismatch");
        }
    }

    private static void testNullIVThrows() {
        int effectiveKeyBits = 40;
        byte[] iv = null;
        int offset = 0;
        try {
            new RC2ParameterSpec(effectiveKeyBits, iv, offset);
            throw new RuntimeException("Expected IllegalArgumentException for null IV");
        } catch (IllegalArgumentException e) {
            // Expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("IV missing")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testShortIVThrows() {
        int effectiveKeyBits = 40;
        byte[] iv = new byte[7]; // less than 8 bytes
        int offset = 0;
        try {
            new RC2ParameterSpec(effectiveKeyBits, iv, offset);
            throw new RuntimeException("Expected IllegalArgumentException for short IV");
        } catch (IllegalArgumentException e) {
            // Expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("IV too short")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }

        // Also test with offset that makes length insufficient
        iv = new byte[10];
        offset = 5; // 10 - 5 = 5 < 8
        try {
            new RC2ParameterSpec(effectiveKeyBits, iv, offset);
            throw new RuntimeException("Expected IllegalArgumentException for short IV with offset");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("IV too short")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
