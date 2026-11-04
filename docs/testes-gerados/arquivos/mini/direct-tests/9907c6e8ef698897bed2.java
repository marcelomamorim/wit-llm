/*
 * @test
 * @summary Test appendOptional method of DateTimeFormatterBuilder
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendOptionalWithValidFormatter();
        testAppendOptionalWithNullFormatter();
    }

    private static void testAppendOptionalWithValidFormatter() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatterBuilder result = builder.appendOptional(formatter);
        if (result != builder) {
            throw new AssertionError("appendOptional should return the same builder instance");
        }
    }

    private static void testAppendOptionalWithNullFormatter() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendOptional(null);
            throw new AssertionError("appendOptional should throw NullPointerException when formatter is null");
        } catch (NullPointerException npe) {
            // expected
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message should be 'formatter'");
            }
        }
    }
}
