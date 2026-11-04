/* @test    @summary Test for Short.valueOf(String s) method, including null input.    @run main ShortWitupTest */

import java.lang.Short;

public class ShortWitupTest {

    public static void main(String[] args) {
        testValidShortString();
        testNullInput();
        testInvalidShortString();
    }

    private static void testValidShortString() {
        String validStr = "12345";
        Short result = null;
        try {
            result = Short.valueOf(validStr);
            if (result == null || result.shortValue() != 12345) {
                throw new AssertionError("Expected Short value 12345, but got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input: " + e);
        }
    }

    private static void testNullInput() {
        String nullStr = null;
        try {
            Short.valueOf(nullStr);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testInvalidShortString() {
        String invalidStr = "notANumber";
        try {
            Short.valueOf(invalidStr);
            throw new AssertionError("Expected NumberFormatException for invalid input");
        } catch (NumberFormatException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
