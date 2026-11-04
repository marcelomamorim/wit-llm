/*
 * @test
 * @summary Test appendPattern method of DateTimeFormatterBuilder
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendPatternNonNull();
        testAppendPatternEmptyString();
        testAppendPatternReturnsSameInstance();
        testAppendPatternNullThrows();
    }

    private static void testAppendPatternNonNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        String pattern = "yyyy-MM-dd";
        DateTimeFormatterBuilder result = builder.appendPattern(pattern);
        if (result == null) {
            throw new AssertionError("appendPattern returned null");
        }
    }

    private static void testAppendPatternEmptyString() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        String pattern = "";
        DateTimeFormatterBuilder result = builder.appendPattern(pattern);
        if (result == null) {
            throw new AssertionError("appendPattern returned null for empty string");
        }
    }

    private static void testAppendPatternReturnsSameInstance() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        String pattern = "yyyy";
        DateTimeFormatterBuilder result = builder.appendPattern(pattern);
        if (result != builder) {
            throw new AssertionError("appendPattern should return the same builder instance");
        }
    }

    private static void testAppendPatternNullThrows() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendPattern(null);
            throw new AssertionError("appendPattern should throw NullPointerException for null pattern");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
