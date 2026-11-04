/*
 * @test
 * @summary Test appendOptional(DateTimeFormatter) method of DateTimeFormatterBuilder
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendOptionalWithNull();
        testAppendOptionalWithValidFormatter();
    }

    private static void testAppendOptionalWithNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendOptional(null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testAppendOptionalWithValidFormatter() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatterBuilder result = builder.appendOptional(formatter);
        if (result != builder) {
            throw new AssertionError("appendOptional should return the same builder instance");
        }
    }
}
