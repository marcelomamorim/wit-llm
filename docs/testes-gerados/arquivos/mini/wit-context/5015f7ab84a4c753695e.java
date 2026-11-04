/*
 * @test
 * @summary Test FormatFlagsConversionMismatchException constructor and accessors
 * @run main FormatFlagsConversionMismatchExceptionWitupTest
 */

public class FormatFlagsConversionMismatchExceptionWitupTest {
    public static void main(String[] args) {
        testConstructorRejectsNullFlag();
        testGettersAndMessage();
    }

    private static void testConstructorRejectsNullFlag() {
        try {
            new java.util.FormatFlagsConversionMismatchException(null, 'a');
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGettersAndMessage() {
        String flag = "-";
        char conv = 'x';
        java.util.FormatFlagsConversionMismatchException ex =
            new java.util.FormatFlagsConversionMismatchException(flag, conv);

        if (!flag.equals(ex.getFlags())) {
            throw new RuntimeException("getFlags() returned unexpected value: " + ex.getFlags());
        }

        if (conv != ex.getConversion()) {
            throw new RuntimeException("getConversion() returned unexpected value: " + ex.getConversion());
        }

        String msg = ex.getMessage();
        if (!msg.contains(flag) || !msg.contains(String.valueOf(conv))) {
            throw new RuntimeException("getMessage() does not contain flag and conversion: " + msg);
        }
    }
}
