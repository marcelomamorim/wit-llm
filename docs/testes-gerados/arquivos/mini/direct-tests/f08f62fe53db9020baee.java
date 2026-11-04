/*
 * @test
 * @summary Test java.util.Date.parse(String) method for various valid and invalid inputs
 * @run main java.util.DateWitupTest
 */

public class DateWitupTest {
    public static void main(String[] args) {
        testValidDateParsing();
        testDateParsingWithTimeZoneOffset();
        testDateParsingWithAmPm();
        testDateParsingWithTwoDigitYear();
        testDateParsingWithCommentsAndWhitespace();
        testDateParsingSyntaxError();
    }

    private static void testValidDateParsing() {
        // Basic valid date string
        String dateStr = "Wed, 02 Oct 2002 08:00:00 GMT";
        long time = java.util.Date.parse(dateStr);
        if (time == 0) {
            throw new RuntimeException("Parsed time should not be zero for valid date string");
        }
    }

    private static void testDateParsingWithTimeZoneOffset() {
        // Date string with timezone offset +0200
        String dateStr = "Wed, 02 Oct 2002 08:00:00 +0200";
        long timeWithOffset = java.util.Date.parse(dateStr);

        // Date string with timezone offset -0300
        String dateStr2 = "Wed, 02 Oct 2002 08:00:00 -0300";
        long timeWithOffset2 = java.util.Date.parse(dateStr2);

        if (timeWithOffset == timeWithOffset2) {
            throw new RuntimeException("Times with different timezone offsets should differ");
        }
    }

    private static void testDateParsingWithAmPm() {
        // Date string with PM indicator
        String dateStr = "Wed, 02 Oct 2002 08:00:00 PM GMT";
        long timePm = java.util.Date.parse(dateStr);

        // Date string with AM indicator
        String dateStr2 = "Wed, 02 Oct 2002 08:00:00 AM GMT";
        long timeAm = java.util.Date.parse(dateStr2);

        if (timePm == timeAm) {
            throw new RuntimeException("AM and PM times should differ");
        }
    }

    private static void testDateParsingWithTwoDigitYear() {
        // Two digit year less than 70, should be adjusted to default century
        String dateStr = "Wed, 02 Oct 69 08:00:00 GMT";
        long time = java.util.Date.parse(dateStr);

        // Two digit year greater or equal 70, treated as full year
        String dateStr2 = "Wed, 02 Oct 70 08:00:00 GMT";
        long time2 = java.util.Date.parse(dateStr2);

        if (time == time2) {
            throw new RuntimeException("Two digit years <70 and >=70 should produce different times");
        }
    }

    private static void testDateParsingWithCommentsAndWhitespace() {
        // Date string with comments and extra whitespace
        String dateStr = "(comment) Wed, (another) 02 Oct 2002 08:00:00 GMT (end)";
        long time = java.util.Date.parse(dateStr);
        if (time == 0) {
            throw new RuntimeException("Parsing should succeed ignoring comments and whitespace");
        }
    }

    private static void testDateParsingSyntaxError() {
        // Invalid date string that should cause IllegalArgumentException
        String invalidDateStr = "InvalidDateString";
        boolean thrown = false;
        try {
            java.util.Date.parse(invalidDateStr);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for invalid date string");
        }
    }
}
