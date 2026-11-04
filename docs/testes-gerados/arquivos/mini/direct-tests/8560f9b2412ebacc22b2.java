/*
 * @test
 * @summary Test appendInstant method of DateTimeFormatterBuilder for valid and invalid fractionalDigits
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testValidFractionalDigits();
        testInvalidFractionalDigitsBelowRange();
        testInvalidFractionalDigitsAboveRange();
    }

    private static void testValidFractionalDigits() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        for (int i = -1; i <= 9; i++) {
            DateTimeFormatterBuilder result = builder.appendInstant(i);
            if (result != builder) {
                throw new AssertionError("appendInstant should return the same builder instance for fractionalDigits=" + i);
            }
        }
    }

    private static void testInvalidFractionalDigitsBelowRange() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendInstant(-2);
            throw new AssertionError("Expected IllegalArgumentException for fractionalDigits=-2");
        } catch (IllegalArgumentException ex) {
            String msg = ex.getMessage();
            if (!msg.contains("fractional digits must be from -1 to 9")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }

    private static void testInvalidFractionalDigitsAboveRange() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendInstant(10);
            throw new AssertionError("Expected IllegalArgumentException for fractionalDigits=10");
        } catch (IllegalArgumentException ex) {
            String msg = ex.getMessage();
            if (!msg.contains("fractional digits must be from -1 to 9")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }
}
