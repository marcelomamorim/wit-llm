/*
 * @test
 * @summary Witup-derived checks for Period.addTo(Temporal): null guard and observable arithmetic on ISO dates
 * @run main PeriodWitupTest
 */

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.Temporal;

public class PeriodWitupTest {

    public static void main(String[] args) {
        testNullTemporalThrowsNpe();
        testZeroPeriodReturnsSameTemporal();
        testYearsOnly();
        testMonthsAndDaysOrder();
        testDaysOnly();
    }

    static void testNullTemporalThrowsNpe() {
        boolean thrown = false;
        try {
            Period.of(1, 2, 3).addTo(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "addTo(null) must throw NullPointerException");
    }

    static void testZeroPeriodReturnsSameTemporal() {
        LocalDate base = LocalDate.of(2020, 1, 1);
        Temporal result = Period.ZERO.addTo(base);
        check(result == base, "Period.ZERO.addTo must return the same temporal");
    }

    static void testYearsOnly() {
        LocalDate base = LocalDate.of(2020, 2, 29);
        Temporal result = Period.of(1, 0, 0).addTo(base);
        check(LocalDate.of(2021, 2, 28).equals(result),
              "Period.of(1,0,0) from 2020-02-29 must be 2021-02-28 but was " + result);
    }

    static void testMonthsAndDaysOrder() {
        LocalDate base = LocalDate.of(2020, 1, 31);
        Temporal result = Period.of(1, 2, 3).addTo(base);
        check(LocalDate.of(2021, 4, 3).equals(result),
              "Period.of(1,2,3) from 2020-01-31 must be 2021-04-03 but was " + result);
    }

    static void testDaysOnly() {
        LocalDate base = LocalDate.of(2020, 12, 28);
        Temporal result = Period.of(0, 0, 5).addTo(base);
        check(LocalDate.of(2021, 1, 2).equals(result),
              "Period.of(0,0,5) from 2020-12-28 must be 2021-01-02 but was " + result);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
