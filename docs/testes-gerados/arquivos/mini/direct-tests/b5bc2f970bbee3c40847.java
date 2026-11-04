/*
 * @test
 * @summary Test java.time.MonthDay.parse(CharSequence, DateTimeFormatter) method
 * @run main MonthDayWitupTest
 */

import java.time.MonthDay;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Objects;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        testParseValid();
        testParseNullFormatter();
        testParseInvalidText();
    }

    private static void testParseValid() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendLiteral("--")
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .appendLiteral('-')
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .toFormatter();

        MonthDay md = MonthDay.parse("--12-03", formatter);
        if (md.getMonth() != Month.DECEMBER || md.getDayOfMonth() != 3) {
            throw new AssertionError("Parsed MonthDay does not match expected values");
        }
    }

    private static void testParseNullFormatter() {
        try {
            MonthDay.parse("--12-03", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testParseInvalidText() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendLiteral("--")
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .appendLiteral('-')
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .toFormatter();

        try {
            MonthDay.parse("invalid", formatter);
            throw new AssertionError("Expected DateTimeParseException for invalid text");
        } catch (DateTimeParseException dtpe) {
            // expected
        }
    }
}
