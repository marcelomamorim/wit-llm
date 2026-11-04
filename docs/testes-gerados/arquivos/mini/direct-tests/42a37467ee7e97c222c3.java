/*
 * @test
 * @summary Test java.lang.Integer.valueOf(String) method
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {
    public static void main(String[] args) {
        testValidIntegerString();
        testNegativeIntegerString();
        testZeroString();
        testInvalidIntegerString();
        testEmptyString();
        testNullString();
    }

    private static void testValidIntegerString() {
        Integer i = Integer.valueOf("12345");
        if (i.intValue() != 12345) {
            throw new AssertionError("Expected 12345 but got " + i);
        }
    }

    private static void testNegativeIntegerString() {
        Integer i = Integer.valueOf("-9876");
        if (i.intValue() != -9876) {
            throw new AssertionError("Expected -9876 but got " + i);
        }
    }

    private static void testZeroString() {
        Integer i = Integer.valueOf("0");
        if (i.intValue() != 0) {
            throw new AssertionError("Expected 0 but got " + i);
        }
    }

    private static void testInvalidIntegerString() {
        try {
            Integer.valueOf("abc123");
            throw new AssertionError("Expected NumberFormatException for input 'abc123'");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testEmptyString() {
        try {
            Integer.valueOf("");
            throw new AssertionError("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testNullString() {
        try {
            Integer.valueOf(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
