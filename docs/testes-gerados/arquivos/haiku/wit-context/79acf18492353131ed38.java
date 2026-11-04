/*
 * @test
 * @summary Tests public behavior of IllegalFormatConversionException constructor and accessors
 * @run main IllegalFormatConversionExceptionWitupTest
 */

public class IllegalFormatConversionExceptionWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testAccessors();
        testGetMessage();
    }

    static void testNullArgumentThrowsNPE() {
        try {
            new IllegalFormatConversionException('d', null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException when arg is null");
    }

    static void testAccessors() {
        IllegalFormatConversionException e =
            new IllegalFormatConversionException('d', String.class);
        check(e.getConversion() == 'd', "getConversion() should return 'd'");
        check(e.getArgumentClass() == String.class,
              "getArgumentClass() should return String.class");
    }

    static void testGetMessage() {
        IllegalFormatConversionException e =
            new IllegalFormatConversionException('d', String.class);
        check("d != java.lang.String".equals(e.getMessage()),
              "unexpected message: " + e.getMessage());

        IllegalFormatConversionException e2 =
            new IllegalFormatConversionException('x', Integer.class);
        check("x != java.lang.Integer".equals(e2.getMessage()),
              "unexpected message: " + e2.getMessage());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
