/*
 * @test
 * @summary Test appendInstant with valid and invalid fractionalDigits
 * @run main DateTimeFormatterBuilderWitupTest_P242977e799d7
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_P242977e799d7 {

    public static void main(String[] args) {
        testAppendInstantValid();
        testAppendInstantInvalidNegative();
        testAppendInstantInvalidTooLarge();
    }

    static void testAppendInstantValid() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendInstant(0);
            builder.appendInstant(9);
            builder.appendInstant(5);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid fractionalDigits", e);
        }
    }

    static void testAppendInstantInvalidNegative() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendInstant(-2);
            throw new AssertionError("Expected IllegalArgumentException for fractionalDigits -2");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for fractionalDigits -2", e);
        }
    }

    static void testAppendInstantInvalidTooLarge() {
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendInstant(10);
            throw new AssertionError("Expected IllegalArgumentException for fractionalDigits 10");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for fractionalDigits 10", e);
        }
    }
}
