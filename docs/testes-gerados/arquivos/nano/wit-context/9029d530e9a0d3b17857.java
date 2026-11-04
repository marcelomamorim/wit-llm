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
        testPlusMonthsNormal();
        testPlusMonthsLargePositive();
        testPlusMonthsLargeNegative();
        testPlusMonthsZero();
        testPlusMonthsOverflow();
    }

    static void testPlusMonthsNormal() {
        LocalDateTime ldt = LocalDateTime.of(2021, 3, 15, 10, 30);
        LocalDateTime result = ldt.plusMonths(3);
        assert result.equals(LocalDateTime.of(2021, 6, 15, 10, 30)) : "Failed: normal plusMonths";
    }

    static void testPlusMonthsLargePositive() {
        LocalDateTime ldt = LocalDateTime.of(2021, 1, 31, 0, 0);
        LocalDateTime result = ldt.plusMonths(12);
        assert result.equals(LocalDateTime.of(2022, 1, 31, 0, 0)) : "Failed: large positive plusMonths";
    }

    static void testPlusMonthsLargeNegative() {
        LocalDateTime ldt = LocalDateTime.of(2021, 1, 31, 0, 0);
        LocalDateTime result = ldt.plusMonths(-1);
        assert result.equals(LocalDateTime.of(2020, 12, 31, 0, 0)) : "Failed: large negative plusMonths";
    }

    static void testPlusMonthsZero() {
        LocalDateTime ldt = LocalDateTime.of(2021, 5, 20, 12, 0);
        LocalDateTime result = ldt.plusMonths(0);
        assert result.equals(ldt) : "Failed: zero plusMonths";
    }

    static void testPlusMonthsOverflow() {
        LocalDateTime ldt = LocalDateTime.of(2000, 2, 29, 0, 0);
        LocalDateTime result = ldt.plusMonths(12 * 10000); // large number to test overflow
        LocalDate expectedDate = LocalDate.of(2000, 2, 29).plusMonths(12 * 10000);
        LocalDateTime expected = LocalDateTime.of(expectedDate, LocalTime.MIDNIGHT);
        assert result.equals(expected) : "Failed: overflow plusMonths";
    }
}
