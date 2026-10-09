/* @test
   @summary Testa o método Date.parse(String s) para comportamentos observáveis.
   @run main DateWitupTest_Padc4168ff7f0
 */
import java.util.Date;
public class DateWitupTest_Padc4168ff7f0 {
    public static void main(String[] args) {
        testNullInput();
        testEmptyString();
        testInvalidFormat();
        testValidDateFormat();
        testDateWithTimezoneOffset();
        testDateWithComments();
        testDateWithCommentsAndTimezone();
        testDateWithInvalidCharacters();
        testDateWithLeapSecond();
        testDateWithYearOnly();
    }

    static void testNullInput() {
        try {
            Date.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testEmptyString() {
        try {
            Date.parse("");
            throw new AssertionError("Expected IllegalArgumentException for empty string");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidFormat() {
        try {
            Date.parse("Invalid date string");
            throw new AssertionError("Expected IllegalArgumentException for invalid format");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testValidDateFormat() {
        long result = Date.parse("Wed, 09 Aug 1995 00:00:00 GMT");
        if (result == 0) {
            throw new AssertionError("Unexpected result for valid date");
        }
    }

    static void testDateWithTimezoneOffset() {
        long result = Date.parse("Wed, 09 Aug 1995 00:00:00 +0200");
        if (result == 0) {
            throw new AssertionError("Unexpected result for date with timezone offset");
        }
    }

    static void testDateWithComments() {
        long result = Date.parse("Wed, 09 Aug 1995 00:00:00 (comment)");
        if (result == 0) {
            throw new AssertionError("Unexpected result for date with comments");
        }
    }

    static void testDateWithCommentsAndTimezone() {
        long result = Date.parse("Wed, 09 Aug 1995 00:00:00 (comment) +0200");
        if (result == 0) {
            throw new AssertionError("Unexpected result for date with comments and timezone");
        }
    }

    static void testDateWithInvalidCharacters() {
        try {
            Date.parse("Wed, 09 Aug 1995 00:00:00 @");
            throw new AssertionError("Expected IllegalArgumentException for invalid characters");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testDateWithLeapSecond() {
        long result = Date.parse("Jun 30 23:59:60 1995");
        if (result == 0) {
            throw new AssertionError("Unexpected result for leap second date");
        }
    }

    static void testDateWithYearOnly() {
        long result = Date.parse("1995");
        if (result == 0) {
            throw new AssertionError("Unexpected result for year only date");
        }
    }
}
