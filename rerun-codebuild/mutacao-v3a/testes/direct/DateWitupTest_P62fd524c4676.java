/* @test @summary Test Date.parse(String s) for various valid and invalid date strings. @run main DateWitupTest_P62fd524c4676 */

import java.util.Date;

public class DateWitupTest_P62fd524c4676 {

    public static void main(String[] args) {
        testParseValidDate();
        testParseInvalidDate();
        testParseNull();
        testParseEmpty();
        testParseWithComments();
        testParseWithTimezoneOffset();
    }

    static void testParseValidDate() {
        String dateStr = "Wed, 09 Aug 1995 00:00:00 GMT";
        long result = Date.parse(dateStr);
        System.out.println("parse: " + dateStr + " -> " + result);
        assert result != -1L : "Parsing valid date failed";
    }

    static void testParseInvalidDate() {
        String invalidStr = "Invalid Date String";
        boolean exceptionThrown = false;
        try {
            Date.parse(invalidStr);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException on invalid date string";
    }

    static void testParseNull() {
        boolean exceptionThrown = false;
        try {
            Date.parse(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException on null string";
    }

    static void testParseEmpty() {
        long result = Date.parse("");
        System.out.println("parse: empty string -> " + result);
        assert result == -1L : "Parsing empty string should result in -1";
    }

    static void testParseWithComments() {
        String dateStr = "(comment) Wed, 09 Aug 1995 00:00:00 GMT";
        long result = Date.parse(dateStr);
        System.out.println("parse with comment: " + dateStr + " -> " + result);
        assert result != -1L : "Parsing date with comment failed";
    }

    static void testParseWithTimezoneOffset() {
        String dateStr = "Wed, 09 Aug 1995 00:00:00 +0200";
        long result = Date.parse(dateStr);
        System.out.println("parse with timezone offset: " + dateStr + " -> " + result);
        assert result != -1L : "Parsing date with timezone offset failed";
    }
}
