/* @test    @summary Test constructor BigDecimal(char[] in, int offset, int len) with valid inputs    @run main BigDecimalWitupTest */

import java.math.BigDecimal;

public class BigDecimalWitupTest {

    public static void main(String[] args) {
        testValidInput();
        testEmptyCharArray();
        testNullCharArray();
        testNegativeOffset();
        testNegativeLength();
        testOffsetPlusLengthExceedsArray();
    }

    static void testValidInput() {
        char[] input = "12345".toCharArray();
        BigDecimal bd = new BigDecimal(input, 0, input.length);
        assert bd.toString().equals("12345") : "Expected 12345, got " + bd.toString();
    }

    static void testEmptyCharArray() {
        char[] input = new char[0];
        BigDecimal bd = new BigDecimal(input, 0, 0);
        assert bd.toString().equals("0") : "Expected 0, got " + bd.toString();
    }

    static void testNullCharArray() {
        try {
            new BigDecimal((char[]) null, 0, 0);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNegativeOffset() {
        char[] input = "12345".toCharArray();
        try {
            new BigDecimal(input, -1, 3);
            assert false : "Expected StringIndexOutOfBoundsException";
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testNegativeLength() {
        char[] input = "12345".toCharArray();
        try {
            new BigDecimal(input, 0, -1);
            assert false : "Expected StringIndexOutOfBoundsException";
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testOffsetPlusLengthExceedsArray() {
        char[] input = "12345".toCharArray();
        try {
            new BigDecimal(input, 2, 4);
            assert false : "Expected StringIndexOutOfBoundsException";
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
