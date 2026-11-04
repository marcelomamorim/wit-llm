/*
 * @test
 * @summary Test LocalTime.ofSecondOfDay(long) for valid and invalid inputs
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.DateTimeException;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testValidValues();
        testInvalidValues();
        System.out.println("All tests passed.");
    }

    private static void testValidValues() {
        // Test start of day
        LocalTime t = LocalTime.ofSecondOfDay(0L);
        if (t.getHour() != 0 || t.getMinute() != 0 || t.getSecond() != 0) {
            throw new AssertionError("Failed at start of day");
        }

        // Test one second before midnight
        t = LocalTime.ofSecondOfDay(86399L); // 23:59:59
        if (t.getHour() != 23 || t.getMinute() != 59 || t.getSecond() != 59) {
            throw new AssertionError("Failed at one second before midnight");
        }

        // Test a random valid time
        t = LocalTime.ofSecondOfDay(3661L); // 01:01:01
        if (t.getHour() != 1 || t.getMinute() != 1 || t.getSecond() != 1) {
            throw new AssertionError("Failed at 01:01:01");
        }
    }

    private static void testInvalidValues() {
        // Test negative secondOfDay
        try {
            LocalTime.ofSecondOfDay(-1L);
            throw new AssertionError("Expected DateTimeException for negative secondOfDay");
        } catch (DateTimeException expected) {
            // expected
        }

        // Test secondOfDay greater than max (86399)
        try {
            LocalTime.ofSecondOfDay(86400L);
            throw new AssertionError("Expected DateTimeException for secondOfDay > 86399");
        } catch (DateTimeException expected) {
            // expected
        }

        // Test very large secondOfDay
        try {
            LocalTime.ofSecondOfDay(Long.MAX_VALUE);
            throw new AssertionError("Expected DateTimeException for very large secondOfDay");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
