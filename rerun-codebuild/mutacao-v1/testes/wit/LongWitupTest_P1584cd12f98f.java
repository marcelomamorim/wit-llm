/* @test    @summary Testa o método Long.decode() para verificar o comportamento esperado e exceções.    @run main LongWitupTest_P1584cd12f98f */

import java.lang.Long;

public class LongWitupTest_P1584cd12f98f {
    public static void main(String[] args) {
        testDecodeNormal();
        testDecodeEmptyString();
        testDecodeSignInWrongPosition();
        testDecodeRadixPrefix();
        testDecodeInvalidNumber();
        System.out.println("All tests passed.");
    }

    static void testDecodeNormal() {
        try {
            Long result = Long.decode("12345");
            if (result == null || result.longValue() != 12345L) {
                throw new AssertionError("Failed: decode normal decimal string");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testDecodeNormal", e);
        }
    }

    static void testDecodeEmptyString() {
        try {
            Long.decode("");
            throw new AssertionError("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type in testDecodeEmptyString", e);
        }
    }

    static void testDecodeSignInWrongPosition() {
        try {
            Long.decode("-+123");
            throw new AssertionError("Expected NumberFormatException for sign in wrong position");
        } catch (NumberFormatException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type in testDecodeSignInWrongPosition", e);
        }
    }

    static void testDecodeRadixPrefix() {
        try {
            Long resultHex = Long.decode("0x1A");
            if (resultHex == null || resultHex.longValue() != 26L) {
                throw new AssertionError("Failed: decode hex prefix 0x");
            }
            Long resultHash = Long.decode("#1A");
            if (resultHash == null || resultHash.longValue() != 26L) {
                throw new AssertionError("Failed: decode hash prefix #");
            }
            Long resultOct = Long.decode("010");
            if (resultOct == null || resultOct.longValue() != 8L) {
                throw new AssertionError("Failed: decode octal prefix 0");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testDecodeRadixPrefix", e);
        }
    }

    static void testDecodeInvalidNumber() {
        try {
            Long.decode("abc");
            throw new AssertionError("Expected NumberFormatException for invalid number");
        } catch (NumberFormatException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testDecodeInvalidNumber", e);
        }
    }
}
