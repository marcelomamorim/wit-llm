/* @test    @summary Test for Integer.decode(String) method to verify exception path for empty string and normal behavior.    @run main IntegerWitupTest_Pf900b8e05da7 */

import java.lang.Integer;

public class IntegerWitupTest_Pf900b8e05da7 {

    public static void main(String[] args) {
        testDecodeEmptyString();
        testDecodeValidHex();
        testDecodeValidOctal();
        testDecodeInvalidSignPosition();
        testDecodeValidDecimal();
    }

    private static void testDecodeEmptyString() {
        try {
            Integer.decode("");
            System.out.println("FAIL: Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            System.out.println("PASS: Caught expected NumberFormatException for empty string");
        }
    }

    private static void testDecodeValidHex() {
        try {
            Integer result = Integer.decode("0x10");
            if (result != 16) {
                System.out.println("FAIL: Unexpected result for 0x10: " + result);
            } else {
                System.out.println("PASS: Valid hex decode 0x10");
            }
        } catch (NumberFormatException e) {
            System.out.println("FAIL: Unexpected exception for 0x10: " + e);
        }
    }

    private static void testDecodeValidOctal() {
        try {
            Integer result = Integer.decode("010");
            if (result != 8) {
                System.out.println("FAIL: Unexpected result for 010: " + result);
            } else {
                System.out.println("PASS: Valid octal decode 010");
            }
        } catch (NumberFormatException e) {
            System.out.println("FAIL: Unexpected exception for 010: " + e);
        }
    }

    private static void testDecodeInvalidSignPosition() {
        try {
            Integer.decode("+123+");
            System.out.println("FAIL: Expected NumberFormatException for sign in wrong position");
        } catch (NumberFormatException e) {
            System.out.println("PASS: Caught expected NumberFormatException for sign in wrong position");
        }
    }

    private static void testDecodeValidDecimal() {
        try {
            Integer result = Integer.decode("123");
            if (result != 123) {
                System.out.println("FAIL: Unexpected result for 123: " + result);
            } else {
                System.out.println("PASS: Valid decimal decode 123");
            }
        } catch (NumberFormatException e) {
            System.out.println("FAIL: Unexpected exception for 123: " + e);
        }
    }
}
