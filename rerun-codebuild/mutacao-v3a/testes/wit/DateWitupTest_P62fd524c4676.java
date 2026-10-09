/*
 * @test
 * @summary Tests for java.util.Date.parse(String s)
 * @run main DateWitupTest_P62fd524c4676
 */

import java.util.Date;

public class DateWitupTest_P62fd524c4676 {

    public static void main(String[] args) {
        testValidDateString();
        testNullString();
        testInvalidFormatString();
        testEmptyString();
        testStringWithComments();
        testStringWithTimezoneOffset();
    }

    static void testValidDateString() {
        try {
            String dateStr = "Wed, 02 Oct 2002 13:00:00 GMT";
            long time = Date.parse(dateStr);
            if (time == 1033568400000L) {
                System.out.println("PASS: testValidDateString");
            } else {
                System.err.println("FAIL: testValidDateString - unexpected timestamp: " + time);
            }
        } catch (Exception e) {
            System.err.println("FAIL: testValidDateString - exception: " + e);
        }
    }

    static void testNullString() {
        try {
            Date.parse(null);
            System.err.println("FAIL: testNullString - expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("PASS: testNullString");
        } catch (Exception e) {
            System.err.println("FAIL: testNullString - unexpected exception: " + e);
        }
    }

    static void testInvalidFormatString() {
        try {
            String invalidStr = "Invalid Date String";
            Date.parse(invalidStr);
            System.err.println("FAIL: testInvalidFormatString - expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: testInvalidFormatString");
        } catch (Exception e) {
            System.err.println("FAIL: testInvalidFormatString - unexpected exception: " + e);
        }
    }

    static void testEmptyString() {
        try {
            String emptyStr = "";
            Date.parse(emptyStr);
            System.err.println("FAIL: testEmptyString - expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: testEmptyString");
        } catch (Exception e) {
            System.err.println("FAIL: testEmptyString - unexpected exception: " + e);
        }
    }

    static void testStringWithComments() {
        try {
            String commentStr = "(comment) Wed, 02 Oct 2002 13:00:00 GMT";
            long time = Date.parse(commentStr);
            if (time == 1033568400000L) {
                System.out.println("PASS: testStringWithComments");
            } else {
                System.err.println("FAIL: testStringWithComments - unexpected timestamp: " + time);
            }
        } catch (Exception e) {
            System.err.println("FAIL: testStringWithComments - exception: " + e);
        }
    }

    static void testStringWithTimezoneOffset() {
        try {
            String tzStr = "Wed, 02 Oct 2002 13:00:00 +0200";
            long time = Date.parse(tzStr);
            long expected = 1033085200000L; // corresponds to the date with timezone offset
            if (time == expected) {
                System.out.println("PASS: testStringWithTimezoneOffset");
            } else {
                System.err.println("FAIL: testStringWithTimezoneOffset - unexpected timestamp: " + time);
            }
        } catch (Exception e) {
            System.err.println("FAIL: testStringWithTimezoneOffset - exception: " + e);
        }
    }
}
