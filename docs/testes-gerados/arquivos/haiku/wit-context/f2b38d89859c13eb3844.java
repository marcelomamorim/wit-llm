/*
 * @test
 * @summary Witup-generated checks for RC2ParameterSpec 3-arg constructor exception paths
 * @run main RC2ParameterSpecWitupTest
 */

import javax.crypto.spec.RC2ParameterSpec;

public class RC2ParameterSpecWitupTest {

    public static void main(String[] args) {
        testNullIvThrowsIAE();
        testIvTooShortThrowsIAE();
        testOffsetLeavesTooFewBytesThrowsIAE();
        testValidBoundariesDoNotThrow();
    }

    static void testNullIvThrowsIAE() {
        expectIAE(null, 0, "null iv");
    }

    static void testIvTooShortThrowsIAE() {
        expectIAE(new byte[7], 0, "iv length 7, offset 0");
        expectIAE(new byte[0], 0, "iv length 0, offset 0");
    }

    static void testOffsetLeavesTooFewBytesThrowsIAE() {
        expectIAE(new byte[8], 1, "iv length 8, offset 1");
        expectIAE(new byte[16], 9, "iv length 16, offset 9");
    }

    static void testValidBoundariesDoNotThrow() {
        new RC2ParameterSpec(40, new byte[8], 0);
        new RC2ParameterSpec(40, new byte[16], 8);
        new RC2ParameterSpec(128, new byte[9], 1);
    }

    static void expectIAE(byte[] iv, int offset, String name) {
        try {
            new RC2ParameterSpec(40, iv, offset);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException(name + ": expected IllegalArgumentException");
    }
}
