/*
 * @test
 * @summary Test FormatFlagsConversionMismatchException constructor and methods
 * @run main java.util.FormatFlagsConversionMismatchExceptionWitupTest
 */

public class FormatFlagsConversionMismatchExceptionWitupTest {
    public static void main(String[] args) {
        testConstructorAndGetters();
        testConstructorNullFlag();
    }

    private static void testConstructorAndGetters() {
        String flag = "-";
        char conv = 'd';
        java.util.FormatFlagsConversionMismatchException ex = new java.util.FormatFlagsConversionMismatchException(flag, conv);

        if (!ex.getFlags().equals(flag)) {
            throw new RuntimeException("getFlags() returned unexpected value: " + ex.getFlags());
        }

        if (ex.getConversion() != conv) {
            throw new RuntimeException("getConversion() returned unexpected value: " + ex.getConversion());
        }

        String msg = ex.getMessage();
        if (!msg.contains(flag) || !msg.contains(String.valueOf(conv))) {
            throw new RuntimeException("getMessage() does not contain flag and conversion: " + msg);
        }
    }

    private static void testConstructorNullFlag() {
        try {
            new java.util.FormatFlagsConversionMismatchException(null, 'x');
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
