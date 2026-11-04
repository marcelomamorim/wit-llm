/*
 * @test
 * @summary Checks public contract of MissingFormatArgumentException constructor, getFormatSpecifier and getMessage
 * @run main MissingFormatArgumentExceptionWitupTest
 */

import java.util.MissingFormatArgumentException;

public class MissingFormatArgumentExceptionWitupTest {

    public static void main(String[] args) {
        testNullSpecifierRejected();
        testSpecifierPreserved();
        testMessageFormat();
        testIsIllegalFormatException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullSpecifierRejected() {
        boolean thrown = false;
        try {
            new MissingFormatArgumentException(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null format specifier must throw NullPointerException");
    }

    static void testSpecifierPreserved() {
        String spec = "%d";
        MissingFormatArgumentException e = new MissingFormatArgumentException(spec);
        check(spec.equals(e.getFormatSpecifier()),
              "getFormatSpecifier must return the specifier passed to the constructor");

        MissingFormatArgumentException empty = new MissingFormatArgumentException("");
        check("".equals(empty.getFormatSpecifier()),
              "getFormatSpecifier must preserve an empty specifier");
    }

    static void testMessageFormat() {
        MissingFormatArgumentException e = new MissingFormatArgumentException("%s");
        check("Format specifier '%s'".equals(e.getMessage()),
              "getMessage must have the form Format specifier 'spec'");
    }

    static void testIsIllegalFormatException() {
        Object e = new MissingFormatArgumentException("%x");
        check(e instanceof java.util.IllegalFormatException,
              "MissingFormatArgumentException must extend IllegalFormatException");
    }
}
