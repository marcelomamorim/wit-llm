/*
 * @test
 * @summary Tests for java.time.ZonedDateTime.parse(CharSequence)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testParseWithNullText();
        testParseWithNullFormatter();
    }

    public static void testParseWithNullText() {
        try {
            ZonedDateTime.parse(null);
            System.out.println("testParseWithNullText: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testParseWithNullText: PASSED");
        } catch (Throwable t) {
            System.out.println("testParseWithNullText: FAILED - Unexpected exception: " + t);
        }
    }

    public static void testParseWithNullFormatter() {
        CharSequence text = "2023-03-25T10:15:30+01:00[Europe/Paris]";
        try {
            // Emulating null formatter by calling parse with default formatter, which is not null, so no test here.
            // Instead, test the internal parse call with a null formatter, which is not directly accessible.
            // As per current API, parse(CharSequence) uses default formatter, so no null formatter scenario.
            // Therefore, this test is not applicable unless parse(CharSequence, DateTimeFormatter) is called with null.
            // For demonstration, we can simulate the internal call.
            ZonedDateTime.parse(text, null);
            System.out.println("testParseWithNullFormatter: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testParseWithNullFormatter: PASSED");
        } catch (Throwable t) {
            System.out.println("testParseWithNullFormatter: FAILED - Unexpected exception: " + t);
        }
    }
}
