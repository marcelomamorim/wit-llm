/*
 * @test
 * @summary WITUP-derived behavioral tests for MissingFormatWidthException(String) constructor
 * @run main MissingFormatWidthExceptionWitupTest
 */

public class MissingFormatWidthExceptionWitupTest {

    public static void main(String[] args) {
        testNullSpecifierThrowsNullPointerException();
        testSpecifierIsPreserved();
        testMessageMatchesSpecifier();
        testIsIllegalFormatException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullSpecifierThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new MissingFormatWidthException(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "Expected NullPointerException for null specifier");
    }

    static void testSpecifierIsPreserved() {
        String spec = "%d";
        MissingFormatWidthException e = new MissingFormatWidthException(spec);
        check(spec.equals(e.getFormatSpecifier()),
              "getFormatSpecifier() should return the constructor argument");
    }

    static void testMessageMatchesSpecifier() {
        String spec = "%s";
        MissingFormatWidthException e = new MissingFormatWidthException(spec);
        check(spec.equals(e.getMessage()),
              "getMessage() should return the format specifier");
    }

    static void testIsIllegalFormatException() {
        Object e = new MissingFormatWidthException("%x");
        check(e instanceof IllegalFormatException,
              "MissingFormatWidthException should extend IllegalFormatException");
    }
}
