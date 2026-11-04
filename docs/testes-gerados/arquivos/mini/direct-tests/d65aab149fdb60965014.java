/*
 * @test
 * @summary Test appendText(TemporalField, TextStyle) method of DateTimeFormatterBuilder
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendTextReturnsSameBuilder();
        testAppendTextRejectsNullField();
        testAppendTextRejectsNullTextStyle();
    }

    private static void testAppendTextReturnsSameBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TemporalField field = ChronoField.MONTH_OF_YEAR;
        TextStyle style = TextStyle.FULL;
        DateTimeFormatterBuilder returned = builder.appendText(field, style);
        if (returned != builder) {
            throw new AssertionError("appendText should return the same builder instance");
        }
    }

    private static void testAppendTextRejectsNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TextStyle style = TextStyle.FULL;
        try {
            builder.appendText(null, style);
            throw new AssertionError("appendText should throw NullPointerException when field is null");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testAppendTextRejectsNullTextStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TemporalField field = ChronoField.DAY_OF_MONTH;
        try {
            builder.appendText(field, null);
            throw new AssertionError("appendText should throw NullPointerException when textStyle is null");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
