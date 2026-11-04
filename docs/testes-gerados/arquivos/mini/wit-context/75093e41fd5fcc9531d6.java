/*
 * @test
 * @summary Test RC5ParameterSpec constructor with iv parameter for exception and normal cases
 * @run main RC5ParameterSpecWitupTest
 */

import javax.crypto.spec.RC5ParameterSpec;

public class RC5ParameterSpecWitupTest {
    public static void main(String[] args) {
        testNullIVThrows();
        testIVTooShortThrows();
        testValidIVCopiesCorrectly();
    }

    private static void testNullIVThrows() {
        try {
            new RC5ParameterSpec(1, 12, 32, null);
            throw new RuntimeException("Expected IllegalArgumentException for null iv");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testIVTooShortThrows() {
        byte[] iv = new byte[3]; // too short for wordSize=32 (needs 2*(32/8)=8 bytes)
        try {
            new RC5ParameterSpec(1, 12, 32, iv);
            throw new RuntimeException("Expected IllegalArgumentException for iv too short");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testValidIVCopiesCorrectly() {
        byte[] iv = new byte[8];
        for (int i = 0; i < iv.length; i++) {
            iv[i] = (byte) i;
        }
        RC5ParameterSpec spec = new RC5ParameterSpec(1, 12, 32, iv);
        byte[] ivFromSpec = spec.getIV();
        if (ivFromSpec == null) {
            throw new RuntimeException("IV should not be null for valid constructor input");
        }
        if (ivFromSpec.length != 8) {
            throw new RuntimeException("IV length mismatch: expected 8, got " + ivFromSpec.length);
        }
        for (int i = 0; i < ivFromSpec.length; i++) {
            if (ivFromSpec[i] != iv[i]) {
                throw new RuntimeException("IV content mismatch at index " + i);
            }
        }
        // Modify original iv and check spec iv is not affected
        iv[0] = 99;
        if (spec.getIV()[0] == 99) {
            throw new RuntimeException("IV array was not copied defensively");
        }
    }
}
