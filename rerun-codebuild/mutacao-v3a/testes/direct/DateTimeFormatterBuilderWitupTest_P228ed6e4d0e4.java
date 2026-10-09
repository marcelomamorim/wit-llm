/*
 * @test
 * @summary Test appendInstant method of DateTimeFormatterBuilder for valid and invalid inputs
 * @run main DateTimeFormatterBuilderWitupTest_P228ed6e4d0e4
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_P228ed6e4d0e4 {

    public static void main(String[] args) {
        testAppendInstantValid();
        testAppendInstantInvalid();
    }

    private static void testAppendInstantValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Test with valid fractionalDigits values
        for (int digit = -1; digit <= 9; digit++) {
            try {
                builder.appendInstant(digit);
            } catch (Exception e) {
                throw new AssertionError("Expected no exception for fractionalDigits=" + digit, e);
            }
        }
    }

    private static void testAppendInstantInvalid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        int[] invalidValues = {-2, 10};
        for (int invalid : invalidValues) {
            try {
                builder.appendInstant(invalid);
                throw new AssertionError("Expected IllegalArgumentException for fractionalDigits=" + invalid);
            } catch (IllegalArgumentException e) {
                // Expected exception
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception type for fractionalDigits=" + invalid, e);
            }
        }
    }
}
