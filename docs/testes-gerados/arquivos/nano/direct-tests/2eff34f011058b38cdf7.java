/*
 * @test
 * @summary Test DateTimeFormatter.toFormat method for correct behavior
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalQuery;
import java.text.Format;
import java.util.Objects;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testToFormatWithValidQuery();
        testToFormatWithNullQuery();
    }

    static void testToFormatWithValidQuery() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TemporalQuery<?> query = new TemporalQuery<Object>() {
            @Override
            public Object queryFrom(java.time.temporal.TemporalAccessor temporal) {
                return null;
            }
        };
        Format format = formatter.toFormat(query);
        if (!(format instanceof java.time.format.DateTimeFormatter)) {
            throw new AssertionError("Expected instance of DateTimeFormatter");
        }
    }

    static void testToFormatWithNullQuery() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formatter.toFormat(null);
            throw new AssertionError("Expected NullPointerException for null parseQuery");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
