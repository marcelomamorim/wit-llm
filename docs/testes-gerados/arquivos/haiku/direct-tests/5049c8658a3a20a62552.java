/*
 * @test
 * @summary Verifies MissingFormatArgumentException constructor null handling, getFormatSpecifier and getMessage
 * @run main MissingFormatArgumentExceptionWitupTest
 */

import java.util.IllegalFormatException;
import java.util.MissingFormatArgumentException;

public class MissingFormatArgumentExceptionWitupTest {

    public static void main(String[] args) {
        testNullThrowsNullPointerException();
        testFormatSpecifierRoundTrip();
        testEmptyFormatSpecifier();
        testMessageFormat();
        testHierarchy();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new MissingFormatArgumentException(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "Constructor with null must throw NullPointerException");
    }

    static void testFormatSpecifierRoundTrip() {
        MissingFormatArgumentException e = new MissingFormatArgumentException("%d");
        check("%d".equals(e.getFormatSpecifier()),
              "getFormatSpecifier must return the specifier passed to the constructor");
    }

    static void testEmptyFormatSpecifier() {
        MissingFormatArgumentException e = new MissingFormatArgumentException("");
        check("".equals(e.getFormatSpecifier()),
              "getFormatSpecifier must return an empty specifier unchanged");
        check("Format specifier ''".equals(e.getMessage()),
              "getMessage must wrap an empty specifier in quotes");
    }

    static void testMessageFormat() {
        MissingFormatArgumentException e = new MissingFormatArgumentException("%s");
        check("Format specifier '%s'".equals(e.getMessage()),
              "getMessage returned unexpected text: " + e.getMessage());
    }

    static void testHierarchy() {
        MissingFormatArgumentException e = new MissingFormatArgumentException("%x");
        check(e instanceof IllegalFormatException,
              "MissingFormatArgumentException must extend IllegalFormatException");
        check(e instanceof IllegalArgumentException,
              "MissingFormatArgumentException must be an IllegalArgumentException");
    }
}
