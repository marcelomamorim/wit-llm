/*
 * @test
 * @summary Test DateTimeFormatterBuilder.append(DateTimeFormatter) for null and valid input
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendWithNull();
        testAppendWithValidFormatter();
    }

    private static void testAppendWithNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.append(null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testAppendWithValidFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.append(formatter);
        if (result != builder) {
            throw new AssertionError("append should return the same builder instance");
        }
    }
}
