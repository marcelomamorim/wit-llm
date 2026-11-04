/*
 * @test
 * @summary Test java.time.format.DateTimeFormatter.toFormat(TemporalQuery<?>) for null argument and basic usage
 * @run main DateTimeFormatterWitupTest
 */

import java.text.Format;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalQuery;
import java.util.Objects;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testToFormatNullQuery();
        testToFormatBasic();
        System.out.println("All tests passed.");
    }

    private static void testToFormatNullQuery() {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            dtf.toFormat(null);
            throw new AssertionError("Expected NullPointerException for null parseQuery");
        } catch (NullPointerException npe) {
            // Expected exception
            if (!"parseQuery".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testToFormatBasic() {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        TemporalQuery<String> query = new TemporalQuery<String>() {
            @Override
            public String queryFrom(java.time.temporal.TemporalAccessor temporal) {
                return "queried";
            }
        };
        Format format = dtf.toFormat(query);
        if (format == null) {
            throw new AssertionError("toFormat returned null");
        }
        // Check that the returned Format is not null and is a Format instance
        if (!(format instanceof Format)) {
            throw new AssertionError("toFormat did not return a Format instance");
        }
    }
}
