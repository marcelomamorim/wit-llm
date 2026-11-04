/* @test    @summary Test for Integer.valueOf(String s) method to verify exception handling and normal behavior    @run main IntegerWitupTest */

import java.lang.Integer;

public class IntegerWitupTest {

    public static void main(String[] args) {
        testValidString();
        testNullString();
        testInvalidString();
    }

    private static void testValidString() {
        String validNumber = "12345";
        Integer result = null;
        try {
            result = Integer.valueOf(validNumber);
            if (result == null || result.intValue() != 12345) {
                throw new AssertionError("Expected Integer value 12345, but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input: " + e);
        }
    }

    private static void testNullString() {
        String nullString = null;
        try {
            Integer.valueOf(nullString);
            throw new AssertionError("Expected NumberFormatException for null input, but no exception was thrown.");
        } catch (NumberFormatException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for null input: " + e);
        }
    }

    private static void testInvalidString() {
        String invalidString = "abc";
        try {
            Integer.valueOf(invalidString);
            throw new AssertionError("Expected NumberFormatException for invalid input, but no exception was thrown.");
        } catch (NumberFormatException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for invalid input: " + e);
        }
    }
}
