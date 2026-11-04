/*
 * @test
 * @summary Test appendInstant with valid and invalid fractionalDigits
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendInstantValid();
        testAppendInstantInvalidNegative();
        testAppendInstantInvalidTooLarge();
    }

    static void testAppendInstantValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendInstant(0);
            builder.appendInstant(9);
            builder.appendInstant(5);
        } catch (Exception e) {
            throw new AssertionError("Valid fractionalDigits caused exception", e);
        }
    }

    static void testAppendInstantInvalidNegative() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendInstant(-2);
            throw new AssertionError("Expected IllegalArgumentException for fractionalDigits -2");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testAppendInstantInvalidTooLarge() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendInstant(10);
            throw new AssertionError("Expected IllegalArgumentException for fractionalDigits 10");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
