/*
 * @test
 * @summary Test appendValue(TemporalField, int) method of DateTimeFormatterBuilder
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendValueValidWidth();
        testAppendValueNullField();
        testAppendValueWidthTooSmall();
        testAppendValueWidthTooLarge();
    }

    private static void testAppendValueValidWidth() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TemporalField field = ChronoField.DAY_OF_MONTH;
        int width = 2;
        DateTimeFormatterBuilder result = builder.appendValue(field, width);
        if (result != builder) {
            throw new AssertionError("appendValue should return this");
        }
    }

    private static void testAppendValueNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendValue(null, 2);
            throw new AssertionError("Expected NullPointerException for null field");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testAppendValueWidthTooSmall() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TemporalField field = ChronoField.MONTH_OF_YEAR;
        try {
            builder.appendValue(field, 0);
            throw new AssertionError("Expected IllegalArgumentException for width < 1");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testAppendValueWidthTooLarge() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        TemporalField field = ChronoField.YEAR;
        try {
            builder.appendValue(field, 20);
            throw new AssertionError("Expected IllegalArgumentException for width > 19");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
