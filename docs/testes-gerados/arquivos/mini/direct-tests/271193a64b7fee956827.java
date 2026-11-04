/*
 * @test
 * @summary Basic tests for DateTimeFormatterBuilder.parse method
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseContext;
import java.time.chrono.IsoChronology;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testParseReturnsPositionWhenNoParsePossible();
        testParseWithEmptyTextReturnsPosition();
        testParseWithValidTextReturnsAdvancedPosition();
    }

    private static void testParseReturnsPositionWhenNoParsePossible() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeParseContext context = new DateTimeParseContext(Locale.US);
        context.setEffectiveChronology(IsoChronology.INSTANCE);
        CharSequence text = ""; // empty text, no parse possible
        int position = 0;
        int result = builder.parse(context, text, position);
        if (result != position) {
            throw new AssertionError("Expected parse to return initial position when no parse possible, got: " + result);
        }
    }

    private static void testParseWithEmptyTextReturnsPosition() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeParseContext context = new DateTimeParseContext(Locale.US);
        context.setEffectiveChronology(IsoChronology.INSTANCE);
        CharSequence text = "";
        int position = 0;
        int result = builder.parse(context, text, position);
        if (result != position) {
            throw new AssertionError("Expected parse to return initial position for empty text, got: " + result);
        }
    }

    private static void testParseWithValidTextReturnsAdvancedPosition() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Build a formatter that parses a 4-digit year
        builder.appendValue(java.time.temporal.ChronoField.YEAR, 4);
        DateTimeParseContext context = new DateTimeParseContext(Locale.US);
        context.setEffectiveChronology(IsoChronology.INSTANCE);
        CharSequence text = "2020";
        int position = 0;
        int result = builder.parse(context, text, position);
        if (result <= position) {
            throw new AssertionError("Expected parse to advance position for valid input, got: " + result);
        }
        if (result != 4) {
            throw new AssertionError("Expected parse to consume 4 characters for year, got: " + result);
        }
    }
}
