/* @test
   @summary Tests the Date.parse(String s) method with various valid and invalid inputs.
   @run main DateWitupTest_Padc4168ff7f0
 */
import java.util.Date;
public class DateWitupTest_Padc4168ff7f0 {
    public static void main(String[] args) {
        testValidDateString();
        testNullString();
        testInvalidFormat();
        testCommentInString();
        testTimezoneOffset();
        testYearParsing();
        testIncompleteDate();
        testInvalidCharacters();
    }

    static void testValidDateString() {
        String dateStr = "Wed, 09 Aug 1995 00:00:00 GMT";
        long result = Date.parse(dateStr);
        assert result != -1 : "Valid date string should not return -1";
    }

    static void testNullString() {
        try {
            Date.parse(null);
            assert false : "Null input should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testInvalidFormat() {
        String invalidStr = "Invalid Date String";
        try {
            Date.parse(invalidStr);
            assert false : "Invalid format should throw IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testCommentInString() {
        String commentStr = "(Comment) 2020-01-01";
        long result = Date.parse(commentStr);
        assert result != -1 : "String with comment should parse";
    }

    static void testTimezoneOffset() {
        String tzStr = "2020-01-01T00:00:00+02";
        long result = Date.parse(tzStr);
        assert result != -1 : "Timezone offset string should parse";
    }

    static void testYearParsing() {
        String yearStr = "1999";
        long result = Date.parse(yearStr);
        assert result != -1 : "Year string should parse";
    }

    static void testIncompleteDate() {
        String incompleteStr = "2020-02";
        try {
            Date.parse(incompleteStr);
            assert false : "Incomplete date string should throw";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidCharacters() {
        String invalidChars = "2020-01-01!";
        try {
            Date.parse(invalidChars);
            assert false : "String with invalid characters should throw";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
