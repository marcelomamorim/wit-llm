/*
 * @test
 * @summary Test for FormatFlagsConversionMismatchException constructor behavior
 * @run main FormatFlagsConversionMismatchExceptionWitupTest
 */

import java.util.FormatFlagsConversionMismatchException;

public class FormatFlagsConversionMismatchExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidArguments();
        testConstructorWithNullFlag();
    }

    private static void testConstructorWithValidArguments() {
        String flag = "-";
        char conversion = 'd';
        FormatFlagsConversionMismatchException ex = new FormatFlagsConversionMismatchException(flag, conversion);
        assert ex.getFlags().equals(flag) : "Flags should match input";
        assert ex.getConversion() == conversion : "Conversion should match input";
        String message = ex.getMessage();
        assert message.contains("Conversion = " + conversion) : "Message should contain conversion";
        assert message.contains("Flags = " + flag) : "Message should contain flags";
    }

    private static void testConstructorWithNullFlag() {
        try {
            new FormatFlagsConversionMismatchException(null, 'a');
            assert false : "Expected NullPointerException";
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
