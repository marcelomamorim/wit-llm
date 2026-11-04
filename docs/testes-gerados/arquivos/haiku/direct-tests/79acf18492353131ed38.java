/*
 * @test
 * @summary Witup-style tests for IllegalFormatConversionException constructor and accessors
 * @run main IllegalFormatConversionExceptionWitupTest
 */

import java.util.IllegalFormatConversionException;

public class IllegalFormatConversionExceptionWitupTest {

    public static void main(String[] args) {
        IllegalFormatConversionException e = new IllegalFormatConversionException('d', Integer.class);
        check(e.getConversion() == 'd', "conversion mismatch: " + e.getConversion());
        check(e.getArgumentClass() == Integer.class, "argument class mismatch");
        check("d != java.lang.Integer".equals(e.getMessage()), "message mismatch: " + e.getMessage());

        e = new IllegalFormatConversionException('x', String.class);
        check(e.getConversion() == 'x', "conversion mismatch for x");
        check(e.getArgumentClass() == String.class, "argument class mismatch for String");
        check("x != java.lang.String".equals(e.getMessage()), "message mismatch: " + e.getMessage());

        e = new IllegalFormatConversionException('f', int[].class);
        check(e.getArgumentClass() == int[].class, "argument class mismatch for int[]");
        check("f != [I".equals(e.getMessage()), "message mismatch for int[]: " + e.getMessage());

        boolean npeThrown = false;
        try {
            new IllegalFormatConversionException('d', null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        check(npeThrown, "null argument class must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
