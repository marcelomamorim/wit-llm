/*
 * @test
 * @summary Witup-generated checks for MinguoDate.until(ChronoLocalDate)
 * @run main MinguoDateWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.MinguoDate;
import java.time.temporal.ChronoUnit;

public class MinguoDateWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testUntilNullThrowsNPE() {
        MinguoDate start = MinguoDate.of(100, 1, 1);
        boolean thrown = false;
        try {
            start.until((ChronoLocalDate) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "until(null) should throw NullPointerException");
    }

    static void testUntilOneYear() {
        MinguoDate start = MinguoDate.of(100, 1, 1);
        MinguoDate end = MinguoDate.of(101, 1, 1);
        ChronoPeriod period = start.until(end);
        check(period.get(ChronoUnit.YEARS) == 1, "expected 1 year, got " + period.get(ChronoUnit.YEARS));
        check(period.get(ChronoUnit.MONTHS) == 0, "expected 0 months");
        check(period.get(ChronoUnit.DAYS) == 0, "expected 0 days");
    }

    static void testUntilNegativeYear() {
        MinguoDate start = MinguoDate.of(101, 1, 1);
        MinguoDate end = MinguoDate.of(100, 1, 1);
        ChronoPeriod period = start.until(end);
        check(period.get(ChronoUnit.YEARS) == -1, "expected -1 year, got " + period.get(ChronoUnit.YEARS));
    }

    static void testUntilIsoLocalDate() {
        MinguoDate start = MinguoDate.of(100, 1, 1);
        LocalDate end = LocalDate.of(2011, 3, 1);
        ChronoPeriod period = start.until(end);
        check(period.get(ChronoUnit.YEARS) == 0, "expected 0 years, got " + period.get(ChronoUnit.YEARS));
        check(period.get(ChronoUnit.MONTHS) == 2, "expected 2 months, got " + period.get(ChronoUnit.MONTHS));
        check(period.get(ChronoUnit.DAYS) == 0, "expected 0 days");
    }

    static void testUntilSameDate() {
        MinguoDate d = MinguoDate.of(100, 6, 15);
        ChronoPeriod period = d.until(MinguoDate.of(100, 6, 15));
        check(period.isZero(), "period between equal dates should be zero");
    }

    public static void main(String[] args) {
        testUntilNullThrowsNPE();
        testUntilOneYear();
        testUntilNegativeYear();
        testUntilIsoLocalDate();
        testUntilSameDate();
    }
}
