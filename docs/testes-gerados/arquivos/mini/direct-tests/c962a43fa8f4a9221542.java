/*
 * @test
 * @summary Test appendLocalized method of DateTimeFormatterBuilder
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendLocalizedWithDateStyleOnly();
        testAppendLocalizedWithTimeStyleOnly();
        testAppendLocalizedWithBothStyles();
        testAppendLocalizedWithBothNullThrows();
    }

    private static void testAppendLocalizedWithDateStyleOnly() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLocalized(FormatStyle.SHORT, null);
        if (result != builder) {
            throw new AssertionError("appendLocalized should return this instance when dateStyle is non-null and timeStyle is null");
        }
    }

    private static void testAppendLocalizedWithTimeStyleOnly() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLocalized(null, FormatStyle.MEDIUM);
        if (result != builder) {
            throw new AssertionError("appendLocalized should return this instance when timeStyle is non-null and dateStyle is null");
        }
    }

    private static void testAppendLocalizedWithBothStyles() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLocalized(FormatStyle.FULL, FormatStyle.LONG);
        if (result != builder) {
            throw new AssertionError("appendLocalized should return this instance when both dateStyle and timeStyle are non-null");
        }
    }

    private static void testAppendLocalizedWithBothNullThrows() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendLocalized(null, null);
            throw new AssertionError("appendLocalized should throw IllegalArgumentException when both dateStyle and timeStyle are null");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }
}
