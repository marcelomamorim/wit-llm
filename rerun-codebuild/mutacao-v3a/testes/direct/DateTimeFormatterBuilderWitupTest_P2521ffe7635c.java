/*
 * @test
 * @summary Tests for DateTimeFormatterBuilder.appendLocalized method
 * @run main DateTimeFormatterBuilderWitupTest_P2521ffe7635c
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

public class DateTimeFormatterBuilderWitupTest_P2521ffe7635c {

    public static void main(String[] args) {
        testAppendLocalizedWithDateStyleOnly();
        testAppendLocalizedWithTimeStyleOnly();
        testAppendLocalizedWithBothStyles();
        testAppendLocalizedWithNullStyles();
    }

    public static void testAppendLocalizedWithDateStyleOnly() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendLocalized(FormatStyle.MEDIUM, null);
        String result = builder.toString();
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-empty string after appendLocalized with date style");
        }
    }

    public static void testAppendLocalizedWithTimeStyleOnly() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendLocalized(null, FormatStyle.SHORT);
        String result = builder.toString();
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-empty string after appendLocalized with time style");
        }
    }

    public static void testAppendLocalizedWithBothStyles() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendLocalized(FormatStyle.FULL, FormatStyle.LONG);
        String result = builder.toString();
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-empty string after appendLocalized with both styles");
        }
    }

    public static void testAppendLocalizedWithNullStyles() {
        boolean exceptionThrown = false;
        try {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.appendLocalized(null, null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException when both styles are null");
        }
    }
}
