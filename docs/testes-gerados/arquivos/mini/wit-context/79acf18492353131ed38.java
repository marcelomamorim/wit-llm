/*
 * @test
 * @summary Test constructor and behavior of IllegalFormatConversionException
 * @run main IllegalFormatConversionExceptionWitupTest
 */

public class IllegalFormatConversionExceptionWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidArgs();
        testConstructorWithNullArg();
        testGetConversionAndArgumentClass();
        testGetMessageFormat();
    }

    private static void testConstructorWithValidArgs() {
        char conversion = 'd';
        Class<?> argClass = Integer.class;
        java.util.IllegalFormatConversionException ex = new java.util.IllegalFormatConversionException(conversion, argClass);
        if (ex == null) {
            throw new AssertionError("Constructor returned null");
        }
    }

    private static void testConstructorWithNullArg() {
        try {
            new java.util.IllegalFormatConversionException('s', null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }

    private static void testGetConversionAndArgumentClass() {
        char conversion = 'f';
        Class<?> argClass = Double.class;
        java.util.IllegalFormatConversionException ex = new java.util.IllegalFormatConversionException(conversion, argClass);
        if (ex.getConversion() != conversion) {
            throw new AssertionError("getConversion() returned wrong value");
        }
        if (ex.getArgumentClass() != argClass) {
            throw new AssertionError("getArgumentClass() returned wrong value");
        }
    }

    private static void testGetMessageFormat() {
        char conversion = 'x';
        Class<?> argClass = Long.class;
        java.util.IllegalFormatConversionException ex = new java.util.IllegalFormatConversionException(conversion, argClass);
        String msg = ex.getMessage();
        String expected = String.format("%c != %s", conversion, argClass.getName());
        if (!msg.equals(expected)) {
            throw new AssertionError("getMessage() returned unexpected message: " + msg);
        }
    }
}
