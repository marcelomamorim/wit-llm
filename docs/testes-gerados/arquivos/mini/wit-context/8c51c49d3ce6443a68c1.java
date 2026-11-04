/*
 * @test
 * @summary Deterministic tests for DateTimeFormatter.parse(CharSequence, TemporalQuery)
 * @run main java.time.format.DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalQuery;
import java.util.Objects;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testParseNullText();
        testParseNullQuery();
        testParseValidInput();
    }

    private static void testParseNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        TemporalQuery<Object> query = new TemporalQuery<Object>() {
            public Object queryFrom(java.time.temporal.TemporalAccessor temporal) {
                return temporal;
            }
        };
        try {
            formatter.parse(null, query);
            throw new AssertionError("Expected NullPointerException for null text");
        } catch (NullPointerException ex) {
            // expected
        }
    }

    private static void testParseNullQuery() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        CharSequence text = "2018-09-01";
        try {
            formatter.parse(text, null);
            throw new AssertionError("Expected NullPointerException for null query");
        } catch (NullPointerException ex) {
            // expected
        }
    }

    private static void testParseValidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        CharSequence text = "2018-09-01";
        TemporalQuery<String> query = new TemporalQuery<String>() {
            public String queryFrom(java.time.temporal.TemporalAccessor temporal) {
                // Return a fixed string to verify query is called
                return "queried";
            }
        };
        String result = formatter.parse(text, query);
        if (!"queried".equals(result)) {
            throw new AssertionError("Expected 'queried' but got: " + result);
        }
    }
}
