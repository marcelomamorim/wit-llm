/*
 * @test
 * @summary Tests for java.time.format.DateTimeFormatter.parse(CharSequence, TemporalQuery)
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalQuery;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testParseNullText();
        testParseNullQuery();
    }

    private static void testParseNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TemporalQuery<?> query = null; // Using null query for test
        try {
            formatter.parse(null, query);
            System.out.println("FAIL: Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null text");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null text: " + e);
        }
    }

    private static void testParseNullQuery() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String text = "2023-03-15";
        try {
            formatter.parse(text, null);
            System.out.println("PASS: parse with null query did not throw");
        } catch (NullPointerException e) {
            System.out.println("FAIL: Unexpected NullPointerException for null query");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null query: " + e);
        }
    }
}
