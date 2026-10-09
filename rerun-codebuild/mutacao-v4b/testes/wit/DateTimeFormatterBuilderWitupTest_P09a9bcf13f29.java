/* @test
   @summary Verifies appendLocalized throws IllegalArgumentException when both style parameters are null.
   @run main DateTimeFormatterBuilderWitupTest_P09a9bcf13f29
 */
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

public class DateTimeFormatterBuilderWitupTest_P09a9bcf13f29 {
    public static void main(String[] args) {
        testAppendLocalizedNullStyles();
    }

    private static void testAppendLocalizedNullStyles() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendLocalized(null, null);
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }
}
