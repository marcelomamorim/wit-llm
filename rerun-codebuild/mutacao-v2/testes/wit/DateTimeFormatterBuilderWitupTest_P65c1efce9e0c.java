/*
 * @test
 * @summary Test appendLocalized method of DateTimeFormatterBuilder for valid and invalid inputs
 * @run main DateTimeFormatterBuilderWitupTest_P65c1efce9e0c
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.lang.IllegalArgumentException;

public class DateTimeFormatterBuilderWitupTest_P65c1efce9e0c {
    public static void main(String[] args) {
        testAppendLocalizedThrowsOnBothNull();
        testAppendLocalizedAcceptsDateStyleOnly();
        testAppendLocalizedAcceptsTimeStyleOnly();
        testAppendLocalizedAcceptsBothNonNull();
    }

    private static void testAppendLocalizedThrowsOnBothNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendLocalized(null, null);
            throw new AssertionError("Expected IllegalArgumentException when both dateStyle and timeStyle are null");
        } catch (IllegalArgumentException ex) {
            // Expected exception
            String msg = ex.getMessage();
            if (msg == null || !msg.contains("Either the date or time style must be non-null")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }

    private static void testAppendLocalizedAcceptsDateStyleOnly() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLocalized(FormatStyle.SHORT, null);
        if (result != builder) {
            throw new AssertionError("appendLocalized should return this when dateStyle is non-null and timeStyle is null");
        }
    }

    private static void testAppendLocalizedAcceptsTimeStyleOnly() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLocalized(null, FormatStyle.MEDIUM);
        if (result != builder) {
            throw new AssertionError("appendLocalized should return this when dateStyle is null and timeStyle is non-null");
        }
    }

    private static void testAppendLocalizedAcceptsBothNonNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLocalized(FormatStyle.FULL, FormatStyle.LONG);
        if (result != builder) {
            throw new AssertionError("appendLocalized should return this when both dateStyle and timeStyle are non-null");
        }
    }
}
