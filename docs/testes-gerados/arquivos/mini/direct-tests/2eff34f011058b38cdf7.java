/*
 * @test
 * @summary Test DateTimeFormatter.toFormat(TemporalQuery) method
 * @run main DateTimeFormatterWitupTest
 */

import java.text.Format;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalQuery;
import java.util.Objects;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testToFormatWithValidQuery();
        testToFormatWithNullQuery();
    }

    private static void testToFormatWithValidQuery() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        TemporalQuery<Object> query = new TemporalQuery<Object>() {
            @Override
            public Object queryFrom(java.time.temporal.TemporalAccessor temporal) {
                return "queried";
            }
        };
        Format format = formatter.toFormat(query);
        if (format == null) {
            throw new AssertionError("toFormat returned null");
        }
        // The returned Format should be non-null and of type ClassicFormat (not accessible here), so test basic behavior
        // We test that the format's toString contains the formatter's toString (indirect check)
        String fmtStr = format.toString();
        String formatterStr = formatter.toString();
        if (fmtStr == null || !fmtStr.contains(formatterStr)) {
            throw new AssertionError("Format.toString() does not contain formatter.toString()");
        }
    }

    private static void testToFormatWithNullQuery() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            formatter.toFormat(null);
            throw new AssertionError("Expected NullPointerException for null parseQuery");
        } catch (NullPointerException npe) {
            if (!"parseQuery".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
