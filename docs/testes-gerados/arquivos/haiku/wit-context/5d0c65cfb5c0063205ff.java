/*
 * @test
 * @summary Witup-derived behavioural tests for LocalDate.until(ChronoLocalDate)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Period;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testNullEndThrowsNPE();
        testForwardPeriod();
        testBackwardPeriod();
        testMonthEndAdjustment();
        testSameDateIsZero();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkPeriod(Period p, int years, int months, int days, String label) {
        check(p.getYears() == years, label + ": years expected " + years + " but was " + p.getYears());
        check(p.getMonths() == months, label + ": months expected " + months + " but was " + p.getMonths());
        check(p.getDays() == days, label + ": days expected " + days + " but was " + p.getDays());
    }

    static void testNullEndThrowsNPE() {
        boolean thrown = false;
        try {
            LocalDate.of(2020, 1, 1).until(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "until(null) must throw NullPointerException");
    }

    static void testForwardPeriod() {
        Period p = LocalDate.of(2020, 1, 1).until(LocalDate.of(2020, 3, 15));
        checkPeriod(p, 0, 2, 14, "forward");
    }

    static void testBackwardPeriod() {
        Period p = LocalDate.of(2020, 3, 15).until(LocalDate.of(2020, 1, 1));
        checkPeriod(p, 0, -2, -14, "backward");
    }

    static void testMonthEndAdjustment() {
        Period p = LocalDate.of(2020, 1, 31).until(LocalDate.of(2020, 3, 1));
        checkPeriod(p, 0, 1, 1, "month-end");
    }

    static void testSameDateIsZero() {
        Period p = LocalDate.of(2021, 6, 10).until(LocalDate.of(2021, 6, 10));
        checkPeriod(p, 0, 0, 0, "same-date");
    }
}
