/*
 * @test
 * @summary Test DateTimeFormatterBuilder.append with a valid DateTimeFormatter
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeFormatterBuilderWitupTest;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendWithValidFormatter();
        testAppendWithNullFormatter();
    }

    static void testAppendWithValidFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.append(formatter);
        // No exception expected, verify that builder is not null
        assert builder != null : "Builder should not be null after append";
    }

    static void testAppendWithNullFormatter() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.append((DateTimeFormatter) null);
            assert false : "NullPointerException expected when appending null formatter";
        } catch (NullPointerException e) {
            // Expected exception
            assert e.getMessage().equals("formatter") : "Expected message 'formatter'";
        }
    }
}
