/*
 * @test
 * @summary Test DateTimeFormatterBuilder.append(DateTimeFormatter) method
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendWithValidFormatter();
        testAppendWithNullFormatter();
    }

    private static void testAppendWithValidFormatter() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatterBuilder result = builder.append(formatter);
        if (result != builder) {
            throw new AssertionError("append should return the same builder instance");
        }
    }

    private static void testAppendWithNullFormatter() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.append(null);
            throw new AssertionError("append(null) should throw NullPointerException");
        } catch (NullPointerException npe) {
            // expected
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message should be 'formatter'");
            }
        }
    }
}
