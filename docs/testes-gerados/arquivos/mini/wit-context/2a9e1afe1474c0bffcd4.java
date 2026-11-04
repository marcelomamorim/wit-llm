/*
 * @test
 * @summary Test LocalDateTime.of(int,int,int,int,int,int) for valid and invalid inputs
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testValidDateTime();
        testInvalidYear();
        testInvalidMonth();
        testInvalidDayOfMonth();
    }

    private static void testValidDateTime() {
        LocalDateTime dt = LocalDateTime.of(2018, 9, 30, 23, 59, 59);
        if (dt.getYear() != 2018 || dt.getMonthValue() != 9 || dt.getDayOfMonth() != 30 ||
            dt.getHour() != 23 || dt.getMinute() != 59 || dt.getSecond() != 59) {
            throw new AssertionError("Valid LocalDateTime.of did not produce expected values");
        }
    }

    private static void testInvalidYear() {
        // Year too low
        try {
            LocalDateTime.of(-1, 1, 1, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid year -1");
        } catch (DateTimeException expected) {
        }
        // Year too high (e.g. > 999999999)
        try {
            LocalDateTime.of(1000000000, 1, 1, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid year 1000000000");
        } catch (DateTimeException expected) {
        }
    }

    private static void testInvalidMonth() {
        // Month too low
        try {
            LocalDateTime.of(2018, 0, 1, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid month 0");
        } catch (DateTimeException expected) {
        }
        // Month too high
        try {
            LocalDateTime.of(2018, 13, 1, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid month 13");
        } catch (DateTimeException expected) {
        }
    }

    private static void testInvalidDayOfMonth() {
        // Day too low
        try {
            LocalDateTime.of(2018, 1, 0, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid dayOfMonth 0");
        } catch (DateTimeException expected) {
        }
        // Day too high for month
        try {
            LocalDateTime.of(2018, 2, 30, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid dayOfMonth 30 in February");
        } catch (DateTimeException expected) {
        }
    }
}
