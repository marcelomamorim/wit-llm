/*
 * @test
 * @summary Test IllegalFormatConversionException constructor and accessors
 * @run main IllegalFormatConversionExceptionWitupTest
 */

public class IllegalFormatConversionExceptionWitupTest {
    public static void main(String[] args) {
        testConstructorAndAccessors();
        testConstructorNullArg();
    }

    private static void testConstructorAndAccessors() {
        char conversion = 'd';
        Class<?> argClass = Integer.class;
        java.util.IllegalFormatConversionException ex = new java.util.IllegalFormatConversionException(conversion, argClass);

        if (ex.getConversion() != conversion) {
            throw new AssertionError("getConversion() returned wrong value");
        }

        if (ex.getArgumentClass() != argClass) {
            throw new AssertionError("getArgumentClass() returned wrong value");
        }

        String expectedMessage = String.format("%c != %s", conversion, argClass.getName());
        if (!ex.getMessage().equals(expectedMessage)) {
            throw new AssertionError("getMessage() returned wrong value: " + ex.getMessage());
        }
    }

    private static void testConstructorNullArg() {
        try {
            new java.util.IllegalFormatConversionException('x', null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
