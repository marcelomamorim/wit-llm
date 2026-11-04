/*
 * @test
 * @summary Test appendZoneText(TextStyle) method of DateTimeFormatterBuilder
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.format.DateTimeParseException;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendZoneTextWithValidTextStyle();
        testAppendZoneTextWithNullTextStyle();
    }

    private static void testAppendZoneTextWithValidTextStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Use a valid TextStyle, e.g. FULL
        builder.appendZoneText(TextStyle.FULL);
        // If no exception, test passes for valid input
    }

    private static void testAppendZoneTextWithNullTextStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendZoneText(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception, test passes
        }
    }
}
