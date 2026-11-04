/*
 * @test
 * @summary Checks MinguoDate.until returns the ISO-based ChronoPeriod with the expected years, months and days
 * @run main MinguoDateWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.MinguoChronology;
import java.time.chrono.MinguoDate;
import java.time.temporal.ChronoUnit;

public class MinguoDateWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkPeriod(ChronoPeriod p, long years, long months, long days, String label) {
        long y = p.get(ChronoUnit.YEARS);
        long m = p.get(ChronoUnit.MONTHS);
        long d = p.get(ChronoUnit.DAYS);
        check(y == years && m == months && d == days,
              label + ": expected " + years + "/" + months + "/" + days
              + " but was " + y + "/" + m + "/" + d);
    }

    static void testSameDateIsZero() {
        MinguoDate d = MinguoDate.of(100, 1, 1);
        ChronoPeriod p = d.until(MinguoDate.of(100, 1, 1));
        checkPeriod(p, 0, 0, 0, "same date");
    }

    static void testForwardPeriod() {
        // Minguo 100-01-01 is ISO 2011-01-01; Minguo 101-03-15 is ISO 2012-03-15
        MinguoDate start = MinguoDate.of(100, 1, 1);
        MinguoDate end = MinguoDate.of(101, 3, 15);
        ChronoPeriod p = start.until(end);
        checkPeriod(p, 1, 2, 14, "forward");
    }

    static void testMatchesIsoUntil() {
        MinguoDate start = MinguoDate.of(100, 1, 1);
        ChronoLocalDate endIso = LocalDate.of(2012, 3, 15);
        ChronoPeriod p = start.until(endIso);
        checkPeriod(p, 1, 2, 14, "iso end date");
    }

    static void testNegativePeriod() {
        MinguoDate start = MinguoDate.of(101, 3, 15);
        MinguoDate end = MinguoDate.of(100, 1, 1);
        ChronoPeriod p = start.until(end);
        checkPeriod(p, -1, -2, -14, "negative");
    }

    static void testChronologyOfResult() {
        MinguoDate start = MinguoDate.of(100, 1, 1);
        MinguoDate end = MinguoDate.of(100, 2, 1);
        ChronoPeriod p = start.until(end);
        check(p.getChronology().equals(MinguoChronology.INSTANCE),
              "result chronology must be MinguoChronology");
        checkPeriod(p, 0, 1, 0, "one month");
    }

    public static void main(String[] args) {
        testSameDateIsZero();
        testForwardPeriod();
        testMatchesIsoUntil();
        testNegativePeriod();
        testChronologyOfResult();
    }
}
