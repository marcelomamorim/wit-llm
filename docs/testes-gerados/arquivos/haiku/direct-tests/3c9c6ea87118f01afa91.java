/*
 * @test
 * @summary Verifies the public constructor and accessors of MissingFormatWidthException
 * @run main MissingFormatWidthExceptionWitupTest
 */

import java.util.MissingFormatWidthException;

public class MissingFormatWidthExceptionWitupTest {

    public static void main(String[] args) {
        testNullSpecifierThrowsNPE();
        testFormatSpecifierIsReturned();
        testMessageEqualsSpecifier();
        testIsIllegalArgumentException();
        testEmptySpecifierIsAccepted();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullSpecifierThrowsNPE() {
        boolean thrown = false;
        try {
            new MissingFormatWidthException(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null specifier must throw NullPointerException");
    }

    static void testFormatSpecifierIsReturned() {
        String spec = "%d";
        MissingFormatWidthException e = new MissingFormatWidthException(spec);
        check(spec.equals(e.getFormatSpecifier()),
              "getFormatSpecifier should return the constructor argument");
    }

    static void testMessageEqualsSpecifier() {
        String spec = "%-s";
        MissingFormatWidthException e = new MissingFormatWidthException(spec);
        check(spec.equals(e.getMessage()),
              "getMessage should return the format specifier");
    }

    static void testIsIllegalArgumentException() {
        MissingFormatWidthException e = new MissingFormatWidthException("%x");
        check(e instanceof IllegalArgumentException,
              "MissingFormatWidthException should be an IllegalArgumentException");
    }

    static void testEmptySpecifierIsAccepted() {
        MissingFormatWidthException e = new MissingFormatWidthException("");
        check("".equals(e.getFormatSpecifier()),
              "empty specifier should be stored and returned unchanged");
    }
}
