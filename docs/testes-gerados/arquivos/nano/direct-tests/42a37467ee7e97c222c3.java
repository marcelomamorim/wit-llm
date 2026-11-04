/* @test    @summary Test Integer.valueOf(String s) method for various inputs    @run main IntegerWitupTest */

import java.lang.Integer;

public class IntegerWitupTest {

    public static void main(String[] args) {
        testValidNumericString();
        testInvalidNumericString();
        testEmptyString();
        testNullInput();
        testLeadingZeros();
        testNegativeNumber();
        testLargeNumber();
    }

    static void testValidNumericString() {
        String input = "12345";
        Integer result = Integer.valueOf(input);
        assert result != null : "Result should not be null for valid input";
        assert result.equals(12345) : "Expected 12345, got " + result;
    }

    static void testInvalidNumericString() {
        String input = "12a45";
        try {
            Integer.valueOf(input);
            assert false : "Expected NumberFormatException for input: " + input;
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testEmptyString() {
        String input = "";
        try {
            Integer.valueOf(input);
            assert false : "Expected NumberFormatException for empty input";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testNullInput() {
        String input = null;
        try {
            Integer.valueOf(input);
            assert false : "Expected NullPointerException for null input";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testLeadingZeros() {
        String input = "0000123";
        Integer result = Integer.valueOf(input);
        assert result != null : "Result should not be null for input with leading zeros";
        assert result.equals(123) : "Expected 123, got " + result;
    }

    static void testNegativeNumber() {
        String input = "-456";
        Integer result = Integer.valueOf(input);
        assert result != null : "Result should not be null for negative input";
        assert result.equals(-456) : "Expected -456, got " + result;
    }

    static void testLargeNumber() {
        String input = "2147483647"; // MAX_VALUE
        Integer result = Integer.valueOf(input);
        assert result != null : "Result should not be null for max int";
        assert result.equals(Integer.MAX_VALUE) : "Expected MAX_VALUE, got " + result;
    }
}
