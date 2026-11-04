/* @test    @summary Test IllegalFormatConversionException constructor and message output    @run main IllegalFormatConversionExceptionWitupTest */

import java.util.IllegalFormatConversionException;

public class IllegalFormatConversionExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidArguments();
        testGetMessageReturnsExpectedFormat();
        testGetConversionAndArgumentClass();
    }

    private static void testConstructorWithValidArguments() {
        Class<?> argClass = String.class;
        char conversionChar = 's';
        IllegalFormatConversionException ex = new IllegalFormatConversionException(conversionChar, argClass);
        assert ex.getConversion() == conversionChar : "Conversion char mismatch";
        assert ex.getArgumentClass() == argClass : "Argument class mismatch";
    }

    private static void testGetMessageReturnsExpectedFormat() {
        Class<?> argClass = Integer.class;
        char conversionChar = 'd';
        IllegalFormatConversionException ex = new IllegalFormatConversionException(conversionChar, argClass);
        String message = ex.getMessage();
        String expectedMessage = String.format("%c != %s", conversionChar, argClass.getName());
        assert message.equals(expectedMessage) : "Message format mismatch";
    }

    private static void testGetConversionAndArgumentClass() {
        Class<?> argClass = Double.class;
        char conversionChar = 'f';
        IllegalFormatConversionException ex = new IllegalFormatConversionException(conversionChar, argClass);
        assert ex.getConversion() == conversionChar : "getConversion() returned wrong char";
        assert ex.getArgumentClass() == argClass : "getArgumentClass() returned wrong class";
    }
}
