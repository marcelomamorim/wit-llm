/*
 * @test
 * @summary Tests constructor and accessors of FormatFlagsConversionMismatchException, including null flag handling
 * @run main FormatFlagsConversionMismatchExceptionWitupTest
 */

import java.util.FormatFlagsConversionMismatchException;

public class FormatFlagsConversionMismatchExceptionWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        testNullFlagThrowsNullPointerException();
        testValidFlagAndConversionAreReturned();
        testMessageContainsConversionAndFlags();
    }

    static void testNullFlagThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new FormatFlagsConversionMismatchException(null, 'd');
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "Expected NullPointerException for null flag");
    }

    static void testValidFlagAndConversionAreReturned() {
        FormatFlagsConversionMismatchException e =
            new FormatFlagsConversionMismatchException("-", 'd');
        check("-".equals(e.getFlags()), "getFlags() should return the flag");
        check(e.getConversion() == 'd', "getConversion() should return the conversion");
    }

    static void testMessageContainsConversionAndFlags() {
        FormatFlagsConversionMismatchException e =
            new FormatFlagsConversionMismatchException("#", 'x');
        String msg = e.getMessage();
        check(msg.contains("x"), "Message should contain the conversion");
        check(msg.contains("#"), "Message should contain the flags");
    }
}
