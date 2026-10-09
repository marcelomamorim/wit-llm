/*
 * @test
 * @summary Test appendInstant method of DateTimeFormatterBuilder for valid and invalid inputs
 * @run main DateTimeFormatterBuilderWitupTest_P242977e799d7
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_P242977e799d7 {

    public static void main(String[] args) {
        testAppendInstantValid();
        testAppendInstantInvalid();
    }

    private static void testAppendInstantValid() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendInstant(0);
            builder.appendInstant(9);
            builder.appendInstant(-1);
            builder.appendInstant(5);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid appendInstant inputs", e);
        }
    }

    private static void testAppendInstantInvalid() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendInstant(10);
            throw new AssertionError("Expected IllegalArgumentException for fractionalDigits=10 not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendInstant(-2);
            throw new AssertionError("Expected IllegalArgumentException for fractionalDigits=-2 not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
