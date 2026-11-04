/* @test    @summary Test MonthDay.parse with custom DateTimeFormatter    @run main MonthDayWitupTest */

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testParseWithFormatter();
        testParseWithNullFormatter();
        testParseInvalidFormat();
    }

    static void testParseWithFormatter() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendLiteral('[')
            .appendValue(MONTH_OF_YEAR, 2)
            .appendLiteral('-')
            .appendValue(DAY_OF_MONTH, 2)
            .appendLiteral(']')
            .toFormatter();
        String text = "[12-25]";
        MonthDay md = MonthDay.parse(text, formatter);
        assert md.getMonthValue() == 12 : "Expected month 12, got " + md.getMonthValue();
        assert md.getDayOfMonth() == 25 : "Expected day 25, got " + md.getDayOfMonth();
    }

    static void testParseWithNullFormatter() {
        try {
            MonthDay.parse("12-25", null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testParseInvalidFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String invalidText = "2023-12-25";
        try {
            MonthDay.parse(invalidText, formatter);
            assert false : "Expected DateTimeParseException";
        } catch (DateTimeParseException e) {
            // expected
        }
    }
}
