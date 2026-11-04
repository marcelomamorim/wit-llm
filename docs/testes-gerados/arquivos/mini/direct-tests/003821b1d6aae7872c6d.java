/*
 * @test
 * @summary Test parseObject method of DateTimeFormatter for normal and exceptional cases
 * @run main DateTimeFormatterWitupTest
 */

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) throws Exception {
        testParseObjectNullText();
        testParseObjectValidText();
        testParseObjectInvalidText();
    }

    private static void testParseObjectNullText() {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            dtf.parseObject(null);
            throw new AssertionError("Expected NullPointerException for null text");
        } catch (NullPointerException npe) {
            // expected
        } catch (ParseException pe) {
            throw new AssertionError("Expected NullPointerException, got ParseException", pe);
        }
    }

    private static void testParseObjectValidText() throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        Object result = dtf.parseObject("2018-09-01");
        if (result == null) {
            throw new AssertionError("parseObject returned null for valid input");
        }
        // The result should be a TemporalAccessor, but we only check non-null here
    }

    private static void testParseObjectInvalidText() {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            dtf.parseObject("invalid-date");
            throw new AssertionError("Expected ParseException for invalid date string");
        } catch (ParseException pe) {
            // expected
            if (pe.getMessage() == null || pe.getErrorOffset() < 0) {
                throw new AssertionError("ParseException has invalid message or error offset");
            }
        }
    }
}
