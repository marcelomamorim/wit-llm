/* @test    @summary Test IllegalFormatConversionException constructor with null argument    @run main IllegalFormatConversionExceptionWitupTest */

import java.util.IllegalFormatConversionException;

public class IllegalFormatConversionExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullArg();
        testConstructorWithValidArgs();
    }

    private static void testConstructorWithNullArg() {
        boolean exceptionThrown = false;
        try {
            new IllegalFormatConversionException('a', null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when arg is null");
        }
    }

    private static void testConstructorWithValidArgs() {
        Class<?> clazz = String.class;
        IllegalFormatConversionException ex = new IllegalFormatConversionException('b', clazz);
        assert ex.getConversion() == 'b' : "Conversion character mismatch";
        assert ex.getArgumentClass() == clazz : "Argument class mismatch";
        String message = ex.getMessage();
        if (message == null || !message.contains("b") || !message.contains("String")) {
            throw new AssertionError("Message does not contain expected values: " + message);
        }
    }
}
