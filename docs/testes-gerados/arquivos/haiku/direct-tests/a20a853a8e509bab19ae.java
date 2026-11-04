/*
 * @test
 * @summary Observable behavior of LocalDateTime.minusNanos
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testBorrowAcrossYearBoundary();
        testZeroNanosReturnsEquivalentValue();
        testNegativeNanosMovesForward();
        testBorrowAcrossLeapDay();
        testOneDayInNanos();
    }

    static void testBorrowAcrossYearBoundary() {
        LocalDateTime base = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime r = base.minusNanos(1L);
        check(r.getYear() == 2019, "year");
        check(r.getMonthValue() == 12, "month");
        check(r.getDayOfMonth() == 31, "day");
        check(r.getHour() == 23, "hour");
        check(r.getMinute() == 59, "minute");
        check(r.getSecond() == 59, "second");
        check(r.getNano() == 999999999, "nano");
        check(r.equals(LocalDateTime.of(2019, 12, 31, 23, 59, 59, 999999999)), "equals");
    }

    static void testZeroNanosReturnsEquivalentValue() {
        LocalDateTime base = LocalDateTime.of(2021, 6, 15, 10, 20, 30, 40);
        LocalDateTime r = base.minusNanos(0L);
        check(r.equals(base), "zero nanos should keep value");
    }

    static void testNegativeNanosMovesForward() {
        LocalDateTime base = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime r = base.minusNanos(-1L);
        check(r.equals(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 1)), "negative nanos should add one nano");
    }

    static void testBorrowAcrossLeapDay() {
        LocalDateTime base = LocalDateTime.of(2020, 3, 1, 0, 0);
        LocalDateTime r = base.minusNanos(1L);
        check(r.equals(LocalDateTime.of(2020, 2, 29, 23, 59, 59, 999999999)), "leap day borrow");
    }

    static void testOneDayInNanos() {
        LocalDateTime base = LocalDateTime.of(2020, 1, 2, 12, 30);
        LocalDateTime r = base.minusNanos(86400000000000L);
        check(r.equals(LocalDateTime.of(2020, 1, 1, 12, 30)), "one day in nanos");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
