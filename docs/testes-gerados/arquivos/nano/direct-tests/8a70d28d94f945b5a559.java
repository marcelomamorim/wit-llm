/* @test    @summary Test parseUnsignedLong with valid decimal string input    @run main LongWitupTest */

import java.lang.Long;

public class LongWitupTest {
    public static void main(String[] args) {
        testParseUnsignedLongValid();
        testParseUnsignedLongInvalid();
    }

    static void testParseUnsignedLongValid() {
        String decimalString = "123456789";
        long expected = 123456789L;
        long result = Long.parseUnsignedLong(decimalString);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
        String zeroString = "0";
        long zeroResult = Long.parseUnsignedLong(zeroString);
        if (zeroResult != 0L) {
            throw new AssertionError("Expected 0 but got " + zeroResult);
        }
        String maxUnsignedString = "18446744073709551615"; // 2^64 - 1
        long maxUnsignedLong = -1L; // As unsigned, this represents 2^64 - 1
        long resultMax = Long.parseUnsignedLong(maxUnsignedString);
        if (resultMax != maxUnsignedLong) {
            throw new AssertionError("Expected " + maxUnsignedLong + " but got " + resultMax);
        }
    }

    static void testParseUnsignedLongInvalid() {
        String invalidString = "notANumber";
        boolean exceptionThrown = false;
        try {
            Long.parseUnsignedLong(invalidString);
        } catch (NumberFormatException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NumberFormatException for input: " + invalidString);
        }
        String tooLargeString = "18446744073709551616"; // 2^64, out of range
        exceptionThrown = false;
        try {
            Long.parseUnsignedLong(tooLargeString);
        } catch (NumberFormatException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NumberFormatException for input: " + tooLargeString);
        }
    }
}
