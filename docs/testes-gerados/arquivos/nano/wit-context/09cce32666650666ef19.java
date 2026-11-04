/* @test    @summary Test for Long.valueOf(String s, int radix) method to verify exception handling and normal behavior    @run main LongWitupTest */

import java.lang.Long;

public class LongWitupTest {

    public static void main(String[] args) {
        testNullInput();
        testValidInput();
        testInvalidInput();
    }

    private static void testNullInput() {
        try {
            Long.valueOf(null, 10);
            System.err.println("Failed: Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            System.out.println("Passed: NumberFormatException thrown for null input");
        }
    }

    private static void testValidInput() {
        try {
            Long result = Long.valueOf("12345", 10);
            if (result != null && result.equals(Long.valueOf(12345L))) {
                System.out.println("Passed: Valid input parsed correctly");
            } else {
                System.err.println("Failed: Valid input did not parse correctly");
            }
        } catch (NumberFormatException e) {
            System.err.println("Failed: Unexpected NumberFormatException for valid input");
        }
    }

    private static void testInvalidInput() {
        try {
            Long.valueOf("abc", 10);
            System.err.println("Failed: Expected NumberFormatException for invalid input");
        } catch (NumberFormatException e) {
            System.out.println("Passed: NumberFormatException thrown for invalid input");
        }
        try {
            Long.valueOf("123", 37); // invalid radix
            System.err.println("Failed: Expected NumberFormatException for invalid radix");
        } catch (NumberFormatException e) {
            System.out.println("Passed: NumberFormatException thrown for invalid radix");
        }
    }
}
