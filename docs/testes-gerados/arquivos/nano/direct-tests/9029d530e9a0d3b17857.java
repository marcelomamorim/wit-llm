/*
 * @test
 * @summary Test for LocalDateTime.plusMonths(long)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testPlusMonthsPositive();
        testPlusMonthsZero();
        testPlusMonthsNegative();
        testPlusMonthsLarge();
    }

    static void testPlusMonthsPositive() {
        LocalDateTime dateTime = LocalDateTime.of(2021, 1, 15, 10, 30);
        LocalDateTime result = dateTime.plusMonths(3);
        LocalDateTime expected = LocalDateTime.of(2021, 4, 15, 10, 30);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusMonths(3)\n" +
                "Expected: " + expected + 
                " but got: " + result);
        }
    }

    static void testPlusMonthsZero() {
        LocalDateTime dateTime = LocalDateTime.of(2021, 5, 20, 8, 45);
        LocalDateTime result = dateTime.plusMonths(0);
        if (!result.equals(dateTime)) {
            throw new AssertionError("Failed: plusMonths(0)\n" +
                "Expected: " + dateTime + 
                " but got: " + result);
        }
    }

    static void testPlusMonthsNegative() {
        LocalDateTime dateTime = LocalDateTime.of(2021, 6, 10, 14, 0);
        LocalDateTime result = dateTime.plusMonths(-2);
        LocalDateTime expected = LocalDateTime.of(2021, 4, 10, 14, 0);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusMonths(-2)\n" +
                "Expected: " + expected + 
                " but got: " + result);
        }
    }

    static void testPlusMonthsLarge() {
        LocalDateTime dateTime = LocalDateTime.of(2020, 2, 29, 12, 0);
        LocalDateTime result = dateTime.plusMonths(12);
        LocalDateTime expected = LocalDateTime.of(2021, 2, 28, 12, 0);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusMonths(12) from leap day\n" +
                "Expected: " + expected + 
                " but got: " + result);
        }
    }
}
