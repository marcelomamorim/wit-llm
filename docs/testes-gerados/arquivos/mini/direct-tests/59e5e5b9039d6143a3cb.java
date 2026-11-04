/*
 * @test
 * @summary Test java.lang.Short(String) constructor for valid and invalid inputs
 * @run main ShortWitupTest
 */

public class ShortWitupTest {
    public static void main(String[] args) {
        testValidString();
        testInvalidString();
        testNullString();
        testEmptyString();
        testOutOfRangeString();
    }

    private static void testValidString() {
        Short s1 = new Short("0");
        if (s1.shortValue() != 0) throw new RuntimeException("Failed for input '0'");

        Short s2 = new Short("32767");
        if (s2.shortValue() != 32767) throw new RuntimeException("Failed for input '32767'");

        Short s3 = new Short("-32768");
        if (s3.shortValue() != -32768) throw new RuntimeException("Failed for input '-32768'");

        Short s4 = new Short("+123");
        if (s4.shortValue() != 123) throw new RuntimeException("Failed for input '+123'");
    }

    private static void testInvalidString() {
        try {
            new Short("abc");
            throw new RuntimeException("Expected NumberFormatException for input 'abc'");
        } catch (NumberFormatException e) {
            // expected
        }

        try {
            new Short("12.3");
            throw new RuntimeException("Expected NumberFormatException for input '12.3'");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testNullString() {
        try {
            new Short(null);
            throw new RuntimeException("Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // expected
        } catch (NullPointerException e) {
            // Also acceptable since parseShort may throw NPE on null
        }
    }

    private static void testEmptyString() {
        try {
            new Short("");
            throw new RuntimeException("Expected NumberFormatException for empty string input");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testOutOfRangeString() {
        try {
            new Short("32768");
            throw new RuntimeException("Expected NumberFormatException for input '32768' (out of range)");
        } catch (NumberFormatException e) {
            // expected
        }

        try {
            new Short("-32769");
            throw new RuntimeException("Expected NumberFormatException for input '-32769' (out of range)");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}