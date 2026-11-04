/*
 * @test
 * @summary Behavioral checks for LocalDate.withDayOfMonth including same-day shortcut, valid change, invalid day rejection and leap-year boundaries
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testSameDayReturnsSameInstance();
        testValidDayChange();
        testDayZeroRejected();
        testDayAboveMonthLengthRejected();
        testDayThirtyOneRejectedInApril();
        testLeapDayAccepted();
        testLeapDayRejectedInCommonYear();
        testOriginalIsImmutable();
    }

    static void testSameDayReturnsSameInstance() {
        LocalDate d = LocalDate.of(2020, 1, 15);
        LocalDate r = d.withDayOfMonth(15);
        check(r == d, "same day should return this");
    }

    static void testValidDayChange() {
        LocalDate d = LocalDate.of(2020, 1, 15);
        LocalDate r = d.withDayOfMonth(20);
        check(r.equals(LocalDate.of(2020, 1, 20)), "valid day change failed");
        check(r.getYear() == 2020 && r.getMonthValue() == 1, "year or month changed unexpectedly");
    }

    static void testDayZeroRejected() {
        expectDateTimeException(LocalDate.of(2020, 1, 15), 0);
    }

    static void testDayAboveMonthLengthRejected() {
        expectDateTimeException(LocalDate.of(2020, 1, 15), 32);
    }

    static void testDayThirtyOneRejectedInApril() {
        expectDateTimeException(LocalDate.of(2020, 4, 10), 31);
    }

    static void testLeapDayAccepted() {
        LocalDate r = LocalDate.of(2020, 2, 1).withDayOfMonth(29);
        check(r.equals(LocalDate.of(2020, 2, 29)), "leap day should be accepted in leap year");
    }

    static void testLeapDayRejectedInCommonYear() {
        expectDateTimeException(LocalDate.of(2021, 2, 1), 29);
    }

    static void testOriginalIsImmutable() {
        LocalDate d = LocalDate.of(2020, 5, 10);
        d.withDayOfMonth(25);
        check(d.getDayOfMonth() == 10, "original LocalDate was mutated");
    }

    static void expectDateTimeException(LocalDate d, int day) {
        try {
            d.withDayOfMonth(day);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for day " + day);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
