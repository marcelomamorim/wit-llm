/*
 * @test
 * @summary Test appendText(TemporalField, TextStyle) method of DateTimeFormatterBuilder
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendTextNullField();
        testAppendTextNullTextStyle();
        testAppendTextValid();
    }

    private static void testAppendTextNullField() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendText(null, TextStyle.FULL);
            throw new AssertionError("Expected NullPointerException for null field");
        } catch (NullPointerException e) {
            // expected
            if (!"field".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null field");
            }
        }
    }

    private static void testAppendTextNullTextStyle() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendText(ChronoField.DAY_OF_MONTH, null);
            throw new AssertionError("Expected NullPointerException for null textStyle");
        } catch (NullPointerException e) {
            // expected
            if (!"textStyle".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null textStyle");
            }
        }
    }

    private static void testAppendTextValid() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendText(ChronoField.MONTH_OF_YEAR, TextStyle.SHORT);
        if (result != builder) {
            throw new AssertionError("appendText should return this instance");
        }
    }
}
