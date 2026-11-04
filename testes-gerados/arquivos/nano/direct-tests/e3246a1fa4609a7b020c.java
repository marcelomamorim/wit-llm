/* @test    @summary Test constructor of UnknownFormatConversionException with valid string    @run main UnknownFormatConversionExceptionWitupTest */

import java.util.UnknownFormatConversionException;

public class UnknownFormatConversionExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidString();
        testConstructorWithNullThrows();
    }

    static void testConstructorWithValidString() {
        String testStr = "abc";
        UnknownFormatConversionException ex = new UnknownFormatConversionException(testStr);
        assert ex.getConversion().equals(testStr) : "Expected conversion to be 'abc'";
        String expectedMessage = "Conversion = 'abc'";
        assert ex.getMessage().equals(expectedMessage) : "Expected message to be 'Conversion = \'abc\''";
    }

    static void testConstructorWithNullThrows() {
        try {
            new UnknownFormatConversionException(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
