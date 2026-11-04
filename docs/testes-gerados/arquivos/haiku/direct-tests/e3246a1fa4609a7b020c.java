/*
 * @test
 * @summary Verifies the UnknownFormatConversionException constructor null check, getConversion and getMessage behavior
 * @run main UnknownFormatConversionExceptionWitupTest
 */

import java.util.UnknownFormatConversionException;

public class UnknownFormatConversionExceptionWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNullPointerException();
        testGetConversionReturnsSameValue();
        testGetConversionAcceptsEmptyString();
        testGetMessageFormatsConversion();
    }

    static void testNullArgumentThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new UnknownFormatConversionException(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "constructor must throw NullPointerException for null");
    }

    static void testGetConversionReturnsSameValue() {
        String conv = "q";
        UnknownFormatConversionException ex = new UnknownFormatConversionException(conv);
        check(conv.equals(ex.getConversion()), "getConversion should return 'q'");
    }

    static void testGetConversionAcceptsEmptyString() {
        UnknownFormatConversionException ex = new UnknownFormatConversionException("");
        check("".equals(ex.getConversion()), "getConversion should return empty string");
    }

    static void testGetMessageFormatsConversion() {
        UnknownFormatConversionException ex = new UnknownFormatConversionException("q");
        String msg = ex.getMessage();
        check("Conversion = 'q'".equals(msg), "unexpected message: " + msg);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
