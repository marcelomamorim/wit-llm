/*
 * @test
 * @summary Test for DateTimeFormatterBuilder.appendLocalized method
 * @run main DateTimeFormatterBuilderWitupTest_Pa69c505aee49
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

public class DateTimeFormatterBuilderWitupTest_Pa69c505aee49 {

    public static void main(String[] args) {
        testAppendLocalizedWithNullStyles();
        testAppendLocalizedWithValidStyles();
    }

    private static void testAppendLocalizedWithNullStyles() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendLocalized(null, null);
            throw new AssertionError("Expected IllegalArgumentException for null styles");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testAppendLocalizedWithValidStyles() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendLocalized(FormatStyle.SHORT, FormatStyle.MEDIUM);
            // No exception expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
