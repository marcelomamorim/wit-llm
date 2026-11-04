/* @test    @summary Test for BigDecimal constructor with char[] input, offset, and length    @run main BigDecimalWitupTest */

import java.math.BigDecimal;

public class BigDecimalWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testInvalidOffsetNegative();
        testInvalidOffsetExceedsLength();
        testEmptyCharArray();
        testNullCharArray();
    }

    static void testValidInputs() {
        char[] input = "12345".toCharArray();
        try {
            BigDecimal bd = new BigDecimal(input, 0, 5);
            System.out.println("Valid input: " + bd.toString());
        } catch (Exception e) {
            System.err.println("Unexpected exception for valid input: " + e);
        }
    }

    static void testInvalidOffsetNegative() {
        char[] input = "12345".toCharArray();
        try {
            new BigDecimal(input, -1, 3);
            System.err.println("Expected exception not thrown for negative offset");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught expected IndexOutOfBoundsException for negative offset");
        } catch (Exception e) {
            System.err.println("Unexpected exception type: " + e);
        }
    }

    static void testInvalidOffsetExceedsLength() {
        char[] input = "12345".toCharArray();
        try {
            new BigDecimal(input, 2, 4); // offset + len = 6 > input.length()
            System.err.println("Expected exception not thrown for offset + len exceeding array length");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught expected IndexOutOfBoundsException for offset + len exceeding array length");
        } catch (Exception e) {
            System.err.println("Unexpected exception type: " + e);
        }
    }

    static void testEmptyCharArray() {
        char[] empty = new char[0];
        try {
            new BigDecimal(empty, 0, 0);
            System.out.println("Empty char[] with zero length processed without exception");
        } catch (Exception e) {
            System.err.println("Unexpected exception for empty char[]: " + e);
        }
    }

    static void testNullCharArray() {
        try {
            new BigDecimal((char[]) null, 0, 0);
            System.err.println("Expected NullPointerException not thrown for null char[]");
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null char[]");
        } catch (Exception e) {
            System.err.println("Unexpected exception type: " + e);
        }
    }
}
