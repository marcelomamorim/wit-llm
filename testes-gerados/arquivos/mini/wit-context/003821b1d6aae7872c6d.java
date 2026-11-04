/*
 * @test
 * @summary Test java.time.format.DateTimeFormatter.parseObject(String) for null input and valid parse
 * @run main DateTimeFormatterWitupTest
 */

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) throws Exception {
        testParseObjectNullInput();
        testParseObjectValidInput();
    }

    private static void testParseObjectNullInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            formatter.parseObject(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // Expected exception
            if (!"text".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        } catch (Exception ex) {
            throw new AssertionError("Unexpected exception type: " + ex);
        }
    }

    private static void testParseObjectValidInput() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String validDate = "2018-09-01";
        Object result = formatter.parseObject(validDate);
        if (result == null) {
            throw new AssertionError("parseObject returned null for valid input");
        }
        // The returned object is a TemporalAccessor, verify it can be queried for YEAR field
        try {
            java.time.temporal.TemporalAccessor ta = (java.time.temporal.TemporalAccessor) result;
            int year = ta.get(java.time.temporal.ChronoField.YEAR);
            if (year != 2018) {
                throw new AssertionError("Parsed year mismatch, expected 2018 but got " + year);
            }
        } catch (Exception ex) {
            throw new AssertionError("Returned object is not a valid TemporalAccessor or missing YEAR field: " + ex);
        }
    }
}
