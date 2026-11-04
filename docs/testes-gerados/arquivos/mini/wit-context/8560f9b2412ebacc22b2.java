/*
 * @test
 * @summary Test appendInstant(int) method of DateTimeFormatterBuilder for valid and invalid fractionalDigits
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testValidFractionalDigits();
        testInvalidFractionalDigits();
    }

    private static void testValidFractionalDigits() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Test boundary values and some typical values within valid range
        for (int fd = -1; fd <= 9; fd++) {
            DateTimeFormatterBuilder result = builder.appendInstant(fd);
            if (result == null) {
                throw new AssertionError("appendInstant returned null for fractionalDigits=" + fd);
            }
            if (result != builder) {
                throw new AssertionError("appendInstant did not return this for fractionalDigits=" + fd);
            }
        }
    }

    private static void testInvalidFractionalDigits() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        int[] invalidValues = { -2, 10, Integer.MIN_VALUE, Integer.MAX_VALUE };
        for (int fd : invalidValues) {
            try {
                builder.appendInstant(fd);
                throw new AssertionError("Expected IllegalArgumentException for fractionalDigits=" + fd);
            } catch (IllegalArgumentException expected) {
                // Expected exception, test passes
            } catch (Exception unexpected) {
                throw new AssertionError("Unexpected exception type for fractionalDigits=" + fd + ": " + unexpected);
            }
        }
    }
}
