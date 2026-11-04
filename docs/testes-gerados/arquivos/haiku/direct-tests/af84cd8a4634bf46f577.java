/*
 * @test
 * @summary Verifies observable behavior of DateTimeFormatterBuilder.appendValue(TemporalField)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testReturnsSameBuilder();
        testFormatsYearWithoutPadding();
        testFormatsMonthWithoutPadding();
        testFormatsDayWithoutPadding();
        testNullFieldThrowsNullPointerException();
        testUnsupportedFieldThrowsDateTimeException();
    }

    static void testReturnsSameBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendValue(ChronoField.YEAR);
        check(result == builder, "appendValue must return the same builder instance");
    }

    static void testFormatsYearWithoutPadding() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR)
                .toFormatter();
        check("2024".equals(f.format(LocalDate.of(2024, 1, 15))), "year 2024 expected");
        check("999".equals(f.format(LocalDate.of(999, 6, 1))), "year 999 must not be padded");
    }

    static void testFormatsMonthWithoutPadding() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .toFormatter();
        check("1".equals(f.format(LocalDate.of(2024, 1, 15))), "month 1 must print as '1'");
        check("12".equals(f.format(LocalDate.of(2024, 12, 15))), "month 12 expected");
    }

    static void testFormatsDayWithoutPadding() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.DAY_OF_MONTH)
                .toFormatter();
        check("5".equals(f.format(LocalDate.of(2024, 3, 5))), "day 5 must print as '5'");
    }

    static void testNullFieldThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder().appendValue(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null field must throw NullPointerException");
    }

    static void testUnsupportedFieldThrowsDateTimeException() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR)
                .toFormatter();
        boolean thrown = false;
        try {
            f.format(LocalTime.of(10, 30));
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "formatting LocalTime with YEAR must throw DateTimeException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
