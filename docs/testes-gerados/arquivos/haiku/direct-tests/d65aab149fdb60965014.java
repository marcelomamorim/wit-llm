/*
 * @test
 * @summary Tests DateTimeFormatterBuilder.appendText(TemporalField, TextStyle) observable behavior
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testReturnsSameBuilder();
        testFormatShortMonth();
        testFormatFullMonth();
        testFormatShortDayOfWeek();
        testNullFieldThrowsNpe();
        testNullTextStyleThrowsNpe();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsSameBuilder() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder r = b.appendText(ChronoField.MONTH_OF_YEAR, TextStyle.SHORT);
        check(r == b, "appendText must return the same builder");
    }

    static void testFormatShortMonth() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendText(ChronoField.MONTH_OF_YEAR, TextStyle.SHORT)
                .toFormatter(Locale.US);
        String s = f.format(LocalDate.of(2018, 1, 15));
        check("Jan".equals(s), "expected Jan but was " + s);
    }

    static void testFormatFullMonth() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendText(ChronoField.MONTH_OF_YEAR, TextStyle.FULL)
                .toFormatter(Locale.US);
        String s = f.format(LocalDate.of(2018, 1, 15));
        check("January".equals(s), "expected January but was " + s);
    }

    static void testFormatShortDayOfWeek() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_WEEK, TextStyle.SHORT)
                .toFormatter(Locale.US);
        // 2018-01-15 is a Monday
        String s = f.format(LocalDate.of(2018, 1, 15));
        check("Mon".equals(s), "expected Mon but was " + s);
    }

    static void testNullFieldThrowsNpe() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        boolean thrown = false;
        try {
            b.appendText(null, TextStyle.SHORT);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null field must throw NullPointerException");
    }

    static void testNullTextStyleThrowsNpe() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        boolean thrown = false;
        try {
            b.appendText(ChronoField.MONTH_OF_YEAR, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null textStyle must throw NullPointerException");
    }
}
