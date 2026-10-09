/*
 * @test
 * @summary Tests for java.util.Date.parse(String s)
 * @run main DateWitupTest_Paa210a9ddcad
 */

import java.util.Date;

public class DateWitupTest_Paa210a9ddcad {

    public static void main(String[] args) {
        testNullInput();
        testEmptyString();
        testInvalidFormat();
        testValidDateFormat();
        testDateWithTimezoneOffset();
        testDateWithComments();
        testTwoDigitYear();
    }

    static void testNullInput() {
        try {
            long result = Date.parse(null);
            System.out.println("testNullInput: FAILED - Expected IllegalArgumentException, got result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("testNullInput: PASSED");
        } catch (Exception e) {
            System.out.println("testNullInput: FAILED - Unexpected exception: " + e);
        }
    }

    static void testEmptyString() {
        try {
            long result = Date.parse("");
            System.out.println("testEmptyString: FAILED - Expected IllegalArgumentException, got result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("testEmptyString: PASSED");
        } catch (Exception e) {
            System.out.println("testEmptyString: FAILED - Unexpected exception: " + e);
        }
    }

    static void testInvalidFormat() {
        try {
            long result = Date.parse("Invalid Date String");
            System.out.println("testInvalidFormat: FAILED - Expected IllegalArgumentException, got result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidFormat: PASSED");
        } catch (Exception e) {
            System.out.println("testInvalidFormat: FAILED - Unexpected exception: " + e);
        }
    }

    static void testValidDateFormat() {
        try {
            long result = Date.parse("Wed, 02 Oct 2002 13:00:00 GMT");
            System.out.println("testValidDateFormat: PASSED, result: " + result);
        } catch (Exception e) {
            System.out.println("testValidDateFormat: FAILED - Exception: " + e);
        }
    }

    static void testDateWithTimezoneOffset() {
        try {
            long result = Date.parse("Wed, 02 Oct 2002 13:00:00 +0200");
            System.out.println("testDateWithTimezoneOffset: PASSED, result: " + result);
        } catch (Exception e) {
            System.out.println("testDateWithTimezoneOffset: FAILED - Exception: " + e);
        }
    }

    static void testDateWithComments() {
        try {
            long result = Date.parse("(Comment) Wed, 02 Oct 2002 13:00:00 GMT");
            System.out.println("testDateWithComments: PASSED, result: " + result);
        } catch (Exception e) {
            System.out.println("testDateWithComments: FAILED - Exception: " + e);
        }
    }

    static void testTwoDigitYear() {
        try {
            long result = Date.parse("02 Oct 99");
            System.out.println("testTwoDigitYear: PASSED, result: " + result);
        } catch (Exception e) {
            System.out.println("testTwoDigitYear: FAILED - Exception: " + e);
        }
    }
}
