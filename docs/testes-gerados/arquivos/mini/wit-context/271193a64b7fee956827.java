/*
 * @test
 * @summary Deterministic tests for DateTimeFormatterBuilder.parse method
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseContext;
import java.time.chrono.IsoChronology;
import java.time.chrono.Chronology;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testParseWithValidContext();
        testParseWithNullText();
        testParseWithEmptyText();
        testParseWithPositionOutOfBounds();
    }

    private static void testParseWithValidContext() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeParseContext context = new DateTimeParseContext(Locale.US, IsoChronology.INSTANCE, null);
        CharSequence text = "2020-01-01";
        int position = 0;
        int result = builder.parse(context, text, position);
        if (result < position || result > text.length()) {
            throw new AssertionError("parse returned invalid position: " + result);
        }
    }

    private static void testParseWithNullText() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeParseContext context = new DateTimeParseContext(Locale.US, IsoChronology.INSTANCE, null);
        CharSequence text = null;
        int position = 0;
        try {
            builder.parse(context, text, position);
            throw new AssertionError("Expected NullPointerException for null text");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testParseWithEmptyText() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeParseContext context = new DateTimeParseContext(Locale.US, IsoChronology.INSTANCE, null);
        CharSequence text = "";
        int position = 0;
        int result = builder.parse(context, text, position);
        if (result != position) {
            throw new AssertionError("parse should return position unchanged for empty text");
        }
    }

    private static void testParseWithPositionOutOfBounds() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeParseContext context = new DateTimeParseContext(Locale.US, IsoChronology.INSTANCE, null);
        CharSequence text = "2020-01-01";
        int position = text.length() + 1; // out of bounds
        try {
            builder.parse(context, text, position);
            throw new AssertionError("Expected IndexOutOfBoundsException for position out of bounds");
        } catch (IndexOutOfBoundsException expected) {
            // Expected exception
        }
    }
}
