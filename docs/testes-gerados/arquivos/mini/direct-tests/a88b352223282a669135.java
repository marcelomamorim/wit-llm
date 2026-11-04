/*
 * @test
 * @summary Test Integer(String) constructor with valid and invalid inputs
 * @run main IntegerWitupTest
 */
public class IntegerWitupTest {
    public static void main(String[] args) {
        testValidString();
        testInvalidString();
        testEmptyString();
        testNullString();
    }

    private static void testValidString() {
        Integer i = new Integer("123");
        if (i.intValue() != 123) {
            throw new Error("Expected 123 but got " + i.intValue());
        }
        Integer neg = new Integer("-456");
        if (neg.intValue() != -456) {
            throw new Error("Expected -456 but got " + neg.intValue());
        }
        Integer zero = new Integer("0");
        if (zero.intValue() != 0) {
            throw new Error("Expected 0 but got " + zero.intValue());
        }
    }

    private static void testInvalidString() {
        try {
            new Integer("abc");
            throw new Error("Expected NumberFormatException for input 'abc'");
        } catch (NumberFormatException e) {
            // expected
        }
        try {
            new Integer("12.3");
            throw new Error("Expected NumberFormatException for input '12.3'");
        } catch (NumberFormatException e) {
            // expected
        }
        try {
            new Integer("--1");
            throw new Error("Expected NumberFormatException for input '--1'");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testEmptyString() {
        try {
            new Integer("");
            throw new Error("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testNullString() {
        try {
            new Integer(null);
            throw new Error("Expected NullPointerException for null string");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
