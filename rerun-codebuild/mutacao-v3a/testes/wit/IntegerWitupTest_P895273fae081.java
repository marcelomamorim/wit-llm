/*
 * @test    @summary Test Integer.decode with various inputs for handling NumberFormatException.
 * @run main IntegerWitupTest_P895273fae081
 */

import java.lang.Integer;

public class IntegerWitupTest_P895273fae081 {

    public static void main(String[] args) {
        testDecodeEmptyString();
        testDecodeInvalidSignPosition();
        testDecodeValidHex();
        testDecodeValidOctal();
        testDecodeValidDecimal();
        testDecodeInvalidNumber();
    }

    static void testDecodeEmptyString() {
        try {
            Integer.decode("");
            System.out.println("testDecodeEmptyString failed: expected NumberFormatException");
        } catch (NumberFormatException e) {
            System.out.println("testDecodeEmptyString passed");
        }
    }

    static void testDecodeInvalidSignPosition() {
        try {
            Integer.decode("+123");
            System.out.println("testDecodeInvalidSignPosition failed: expected NumberFormatException");
        } catch (NumberFormatException e) {
            System.out.println("testDecodeInvalidSignPosition passed");
        }
    }

    static void testDecodeValidHex() {
        try {
            Integer result = Integer.decode("0x10");
            if (result.equals(16)) {
                System.out.println("testDecodeValidHex passed");
            } else {
                System.out.println("testDecodeValidHex failed: wrong result " + result);
            }
        } catch (NumberFormatException e) {
            System.out.println("testDecodeValidHex failed: " + e);
        }
    }

    static void testDecodeValidOctal() {
        try {
            Integer result = Integer.decode("010");
            if (result.equals(8)) {
                System.out.println("testDecodeValidOctal passed");
            } else {
                System.out.println("testDecodeValidOctal failed: wrong result " + result);
            }
        } catch (NumberFormatException e) {
            System.out.println("testDecodeValidOctal failed: " + e);
        }
    }

    static void testDecodeValidDecimal() {
        try {
            Integer result = Integer.decode("123");
            if (result.equals(123)) {
                System.out.println("testDecodeValidDecimal passed");
            } else {
                System.out.println("testDecodeValidDecimal failed: wrong result " + result);
            }
        } catch (NumberFormatException e) {
            System.out.println("testDecodeValidDecimal failed: " + e);
        }
    }

    static void testDecodeInvalidNumber() {
        try {
            Integer.decode("abc");
            System.out.println("testDecodeInvalidNumber failed: expected NumberFormatException");
        } catch (NumberFormatException e) {
            System.out.println("testDecodeInvalidNumber passed");
        }
    }
}
