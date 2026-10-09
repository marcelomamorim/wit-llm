/* @test
   @summary Tests appendLocalized method of DateTimeFormatterBuilder.
   @run main DateTimeFormatterBuilderWitupTest_P09a9bcf13f29
 */
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

public class DateTimeFormatterBuilderWitupTest_P09a9bcf13f29 {
    public static void main(String[] args) {
        testAppendLocalizedWithNullStyles();
        testAppendLocalizedWithValidStyles();
    }

    private static void testAppendLocalizedWithNullStyles() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendLocalized(null, null);
            throw new AssertionError("Expected IllegalArgumentException for both null styles");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testAppendLocalizedWithValidStyles() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Test with date style only
        builder.appendLocalized(FormatStyle.SHORT, null);
        // Test with time style only
        builder.appendLocalized(null, FormatStyle.MEDIUM);
        // Test with both styles
        builder.appendLocalized(FormatStyle.FULL, FormatStyle.LONG);
        // No exception should be thrown
    }
}
