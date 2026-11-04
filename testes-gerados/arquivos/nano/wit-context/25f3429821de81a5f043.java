/*
 * @test
 * @summary Tests for java.time.LocalDateTime.of method exception paths
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testInvalidMonthBelowRange();
        testInvalidMonthAboveRange();
        testInvalidYearBelowRange();
        testInvalidYearAboveRange();
    }

    private static void testInvalidMonthBelowRange() {
        int year = 2020;
        int month = 0; // invalid, below 1
        int dayOfMonth = 10;
        int hour = 10;
        int minute = 10;
        int second = 10;
        int nanoOfSecond = 0;
        try {
            LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
            System.out.println("FAIL: Expected DateTimeException for month below range");
        } catch (java.time.DateTimeException e) {
            System.out.println("PASS: Caught expected DateTimeException for month below range");
        }
    }

    private static void testInvalidMonthAboveRange() {
        int year = 2020;
        int month = 13; // invalid, above 12
        int dayOfMonth = 10;
        int hour = 10;
        int minute = 10;
        int second = 10;
        int nanoOfSecond = 0;
        try {
            LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
            System.out.println("FAIL: Expected DateTimeException for month above range");
        } catch (java.time.DateTimeException e) {
            System.out.println("PASS: Caught expected DateTimeException for month above range");
        }
    }

    private static void testInvalidYearBelowRange() {
        int year = -10000; // assuming invalid, outside valid range
        int month = 5;
        int dayOfMonth = 10;
        int hour = 10;
        int minute = 10;
        int second = 10;
        int nanoOfSecond = 0;
        try {
            LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
            System.out.println("FAIL: Expected DateTimeException for year below range");
        } catch (java.time.DateTimeException e) {
            System.out.println("PASS: Caught expected DateTimeException for year below range");
        }
    }

    private static void testInvalidYearAboveRange() {
        int year = 30000; // assuming invalid, outside valid range
        int month = 5;
        int dayOfMonth = 10;
        int hour = 10;
        int minute = 10;
        int second = 10;
        int nanoOfSecond = 0;
        try {
            LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
            System.out.println("FAIL: Expected DateTimeException for year above range");
        } catch (java.time.DateTimeException e) {
            System.out.println("PASS: Caught expected DateTimeException for year above range");
        }
    }
}
