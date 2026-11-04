/* @test    @summary Test for UnknownFormatConversionException constructor with null argument    @run main UnknownFormatConversionExceptionWitupTest */

import java.util.UnknownFormatConversionException;

public class UnknownFormatConversionExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithNull();
        testConstructorWithValidString();
    }

    private static void testConstructorWithNull() {
        try {
            new UnknownFormatConversionException(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testConstructorWithValidString() {
        String testStr = "abc";
        UnknownFormatConversionException ex = new UnknownFormatConversionException(testStr);
        if (!ex.getConversion().equals(testStr)) {
            System.err.println("Failed: getConversion() did not return the expected string");
            System.exit(1);
        }
        String expectedMessage = String.format("Conversion = '%s'", testStr);
        if (!ex.getMessage().equals(expectedMessage)) {
            System.err.println("Failed: getMessage() did not return the expected message");
            System.exit(1);
        }
    }
}
