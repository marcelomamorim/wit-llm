/*
 * @test
 * @summary Tests for java.time.format.DateTimeFormatter.parse method
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalQuery;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testParseWithValidInput();
        testParseWithNullText();
        testParseWithNullQuery();
        testParseWithInvalidInput();
    }

    static void testParseWithValidInput() {
        try {
            String text = "2023-03-15";
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            // Using a dummy query that returns null, as parse returns a TemporalAccessor
            TemporalQuery<?> query = t -> null;
            Object result = formatter.parse(text, query);
            if (result != null) {
                System.out.println("testParseWithValidInput: PASSED");
            } else {
                System.out.println("testParseWithValidInput: FAILED - result is null");
            }
        } catch (Exception e) {
            System.out.println("testParseWithValidInput: FAILED with exception: " + e);
        }
    }

    static void testParseWithNullText() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            formatter.parse(null, t -> null);
            System.out.println("testParseWithNullText: FAILED - expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testParseWithNullText: PASSED");
        } catch (Exception e) {
            System.out.println("testParseWithNullText: FAILED with unexpected exception: " + e);
        }
    }

    static void testParseWithNullQuery() {
        try {
            String text = "2023-03-15";
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            formatter.parse(text, null);
            System.out.println("testParseWithNullQuery: FAILED - expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testParseWithNullQuery: PASSED");
        } catch (Exception e) {
            System.out.println("testParseWithNullQuery: FAILED with unexpected exception: " + e);
        }
    }

    static void testParseWithInvalidInput() {
        try {
            String text = "invalid-date";
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            formatter.parse(text, t -> null);
            System.out.println("testParseWithInvalidInput: FAILED - expected DateTimeParseException");
        } catch (DateTimeParseException e) {
            System.out.println("testParseWithInvalidInput: PASSED");
        } catch (Exception e) {
            System.out.println("testParseWithInvalidInput: FAILED with unexpected exception: " + e);
        }
    }
}
