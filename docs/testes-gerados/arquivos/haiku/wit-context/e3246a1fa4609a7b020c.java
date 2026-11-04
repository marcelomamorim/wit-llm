/*
 * @test
 * @summary Checks the public constructor of UnknownFormatConversionException: null rejection, conversion retrieval and message format
 * @run main UnknownFormatConversionExceptionWitupTest
 */

import java.util.UnknownFormatConversionException;

public class UnknownFormatConversionExceptionWitupTest {

    public static void main(String[] args) {
        testNullConversionThrowsNullPointerException();
        testConversionIsPreserved();
        testMessageFormat();
        testEmptyConversionIsAccepted();
    }

    static void testNullConversionThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new UnknownFormatConversionException(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null conversion");
        }
    }

    static void testConversionIsPreserved() {
        String conv = "q";
        UnknownFormatConversionException e = new UnknownFormatConversionException(conv);
        if (!conv.equals(e.getConversion())) {
            throw new RuntimeException("getConversion() returned unexpected value: " + e.getConversion());
        }
    }

    static void testMessageFormat() {
        UnknownFormatConversionException e = new UnknownFormatConversionException("y");
        String expected = "Conversion = 'y'";
        if (!expected.equals(e.getMessage())) {
            throw new RuntimeException("Unexpected message: " + e.getMessage());
        }
    }

    static void testEmptyConversionIsAccepted() {
        UnknownFormatConversionException e = new UnknownFormatConversionException("");
        if (!"".equals(e.getConversion())) {
            throw new RuntimeException("Empty conversion not preserved");
        }
    }
}
