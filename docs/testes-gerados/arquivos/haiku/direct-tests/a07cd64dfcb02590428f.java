/*
 * @test
 * @summary Verifies RC2ParameterSpec(int, byte[]) argument handling, IV copying and delegation to the offset constructor
 * @run main RC2ParameterSpecWitupTest
 */

import java.util.Arrays;
import javax.crypto.spec.RC2ParameterSpec;

public class RC2ParameterSpecWitupTest {

    public static void main(String[] args) throws Exception {
        testValidIvIsStored();
        testNullIvRejected();
        testShortIvRejected();
        testIvIsCopied();
        testLongerIvUsesFirstEightBytes();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidIvIsStored() {
        byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};
        RC2ParameterSpec p = new RC2ParameterSpec(64, iv);
        check(p.getEffectiveKeyBits() == 64, "effectiveKeyBits mismatch");
        check(Arrays.equals(p.getIV(), iv), "IV mismatch for valid 8-byte IV");
    }

    static void testNullIvRejected() {
        try {
            new RC2ParameterSpec(64, (byte[]) null);
            throw new RuntimeException("expected IllegalArgumentException for null IV");
        } catch (IllegalArgumentException e) {
            check("IV missing".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }

    static void testShortIvRejected() {
        try {
            new RC2ParameterSpec(64, new byte[7]);
            throw new RuntimeException("expected IllegalArgumentException for short IV");
        } catch (IllegalArgumentException e) {
            check("IV too short".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }

    static void testIvIsCopied() {
        byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};
        RC2ParameterSpec p = new RC2ParameterSpec(40, iv);
        iv[0] = 99;
        check(p.getIV()[0] == 1, "IV was not defensively copied");
    }

    static void testLongerIvUsesFirstEightBytes() {
        byte[] iv = {10, 11, 12, 13, 14, 15, 16, 17, 18};
        RC2ParameterSpec p = new RC2ParameterSpec(128, iv);
        byte[] expected = {10, 11, 12, 13, 14, 15, 16, 17};
        check(Arrays.equals(p.getIV(), expected), "constructor did not use first 8 bytes with offset 0");
    }
}
