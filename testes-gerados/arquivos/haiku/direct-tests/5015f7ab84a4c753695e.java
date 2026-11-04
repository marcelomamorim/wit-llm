/*
 * @test
 * @summary Verifies the public constructor of FormatFlagsConversionMismatchException: null flag rejection and observable getters/message.
 * @run main FormatFlagsConversionMismatchExceptionWitupTest
 */

import java.util.FormatFlagsConversionMismatchException;

public class FormatFlagsConversionMismatchExceptionWitupTest {

    public static void main(String[] args) {
        testNullFlagsThrowsNPE();
        testGettersReturnConstructorArguments();
        testMessageFormat();
        testEmptyFlagsAccepted();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullFlagsThrowsNPE() {
        try {
            new FormatFlagsConversionMismatchException(null, 'd');
            throw new RuntimeException("Expected NullPointerException for null flags");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testGettersReturnConstructorArguments() {
        FormatFlagsConversionMismatchException e =
            new FormatFlagsConversionMismatchException("-", 'd');
        check("-".equals(e.getFlags()), "getFlags() mismatch: " + e.getFlags());
        check(e.getConversion() == 'd', "getConversion() mismatch: " + e.getConversion());
    }

    static void testMessageFormat() {
        FormatFlagsConversionMismatchException e =
            new FormatFlagsConversionMismatchException("-", 'd');
        String expected = "Conversion = d, Flags = -";
        check(expected.equals(e.getMessage()),
              "getMessage() mismatch: " + e.getMessage());
    }

    static void testEmptyFlagsAccepted() {
        FormatFlagsConversionMismatchException e =
            new FormatFlagsConversionMismatchException("", 'x');
        check("".equals(e.getFlags()), "empty getFlags() mismatch");
        check(e.getConversion() == 'x', "conversion mismatch for empty flags");
        check("Conversion = x, Flags = ".equals(e.getMessage()),
              "empty-flags getMessage() mismatch: " + e.getMessage());
    }
}
