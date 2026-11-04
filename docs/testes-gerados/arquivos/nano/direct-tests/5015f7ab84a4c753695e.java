/* @test    @summary Test for FormatFlagsConversionMismatchException constructor and message output    @run main FormatFlagsConversionMismatchExceptionWitupTest */

import java.util.FormatFlagsConversionMismatchException;

public class FormatFlagsConversionMismatchExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorAndMessage();
    }

    static void testConstructorAndMessage() {
        String flag = "-";
        char conversion = 'd';
        FormatFlagsConversionMismatchException ex = new FormatFlagsConversionMismatchException(flag, conversion);
        assert ex.getFlags().equals(flag) : "Flags do not match";
        assert ex.getConversion() == conversion : "Conversion does not match";
        String message = ex.getMessage();
        assert message.contains("Conversion = " + conversion) : "Message does not contain conversion";
        assert message.contains("Flags = " + flag) : "Message does not contain flags";
    }
}
