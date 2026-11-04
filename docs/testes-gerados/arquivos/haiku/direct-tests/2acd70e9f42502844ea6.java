/*
 * @test
 * @summary Verifies LocalDate.withDayOfMonth for same-day, valid, leap-year and invalid day values
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testSameDayReturnsSameInstance();
        testValidDayChange();
        testMonthAndYearArePreserved();
        testLeapDayAccepted();
        testNonLeapFebruary29Rejected();
        testDay31InThirtyDayMonthRejected();
        testDayZeroRejected();
        testDay32Rejected();
    }

    static void testSameDayReturnsSameInstance() {
        LocalDate d = LocalDate.of(2024, 1, 15);
        LocalDate r = d.withDayOfMonth(15);
        check(r == d, "same day should return this instance");
    }

    static void testValidDayChange() {
        LocalDate d = LocalDate.of(2024, 1, 15);
        LocalDate r = d.withDayOfMonth(20);
        check(r.getDayOfMonth() == 20, "day should be 20");
        check(r.equals(LocalDate.of(2024, 1, 20)), "expected 2024-01-20");
        check(d.getDayOfMonth() == 15, "original must be immutable");
    }

    static void testMonthAndYearArePreserved() {
        LocalDate d = LocalDate.of(1999, 12, 31);
        LocalDate r = d.withDayOfMonth(1);
        check(r.getYear() == 1999, "year must be preserved");
        check(r.getMonthValue() == 12, "month must be preserved");
        check(r.getDayOfMonth() == 1, "day must be 1");
    }

    static void testLeapDayAccepted() {
        LocalDate r = LocalDate.of(2024, 2, 10).withDayOfMonth(29);
        check(r.equals(LocalDate.of(2024, 2, 29)), "expected 2024-02-29");
    }

    static void testNonLeapFebruary29Rejected() {
        expectDateTimeException(LocalDate.of(2023, 2, 10), 29);
    }

    static void testDay31InThirtyDayMonthRejected() {
        expectDateTimeException(LocalDate.of(2024, 4, 10), 31);
    }

    static void testDayZeroRejected() {
        expectDateTimeException(LocalDate.of(2024, 1, 15), 0);
    }

    static void testDay32Rejected() {
        expectDateTimeException(LocalDate.of(2024, 1, 15), 32);
    }

    static void expectDateTimeException(LocalDate d, int day) {
        try {
            d.withDayOfMonth(day);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for day " + day);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
