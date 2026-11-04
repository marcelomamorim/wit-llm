/*
 * @test
 * @summary Test the format method of java.time.MonthDay for various formatters.
 * @run main MonthDayWitupTest
 */

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testFormatWithISO_LOCAL_DATE();
        testFormatWithCustomPattern();
        testFormatWithNullFormatter();
    }

    static void testFormatWithISO_LOCAL_DATE() {
        try {
            MonthDay md = MonthDay.of(12, 25);
            String result = md.format(DateTimeFormatter.ISO_LOCAL_DATE);
            if (!"--12-25".equals(result)) {
                throw new AssertionError("Expected '--12-25', but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Exception in testFormatWithISO_LOCAL_DATE", e);
        }
    }

    static void testFormatWithCustomPattern() {
        try {
            MonthDay md = MonthDay.of(1, 1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
            String result = md.format(formatter);
            if (!"01/01".equals(result)) {
                throw new AssertionError("Expected '01/01', but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Exception in testFormatWithCustomPattern", e);
        }
    }

    static void testFormatWithNullFormatter() {
        try {
            MonthDay md = MonthDay.of(6, 15);
            md.format(null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
