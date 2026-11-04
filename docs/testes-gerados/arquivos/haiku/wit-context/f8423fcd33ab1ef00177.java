/*
 * @test
 * @summary Witup-generated checks for ThaiBuddhistDate.until(ChronoLocalDate)
 * @run main ThaiBuddhistDateWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.ThaiBuddhistDate;
import java.time.temporal.ChronoUnit;

public class ThaiBuddhistDateWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkPeriod(ChronoPeriod p, long years, long months, long days, String label) {
        check(p.get(ChronoUnit.YEARS) == years, label + " years expected " + years + " but was " + p.get(ChronoUnit.YEARS));
        check(p.get(ChronoUnit.MONTHS) == months, label + " months expected " + months + " but was " + p.get(ChronoUnit.MONTHS));
        check(p.get(ChronoUnit.DAYS) == days, label + " days expected " + days + " but was " + p.get(ChronoUnit.DAYS));
    }

    static void testNullEndDateThrowsNPE() {
        ThaiBuddhistDate start = ThaiBuddhistDate.of(2560, 1, 1);
        boolean thrown = false;
        try {
            start.until(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "until(null) must throw NullPointerException");
    }

    static void testForwardPeriodThaiEndDate() {
        ThaiBuddhistDate start = ThaiBuddhistDate.of(2560, 1, 1);
        ThaiBuddhistDate end = ThaiBuddhistDate.of(2561, 3, 15);
        checkPeriod(start.until(end), 1, 2, 14, "forward Thai");
    }

    static void testForwardPeriodIsoEndDate() {
        ThaiBuddhistDate start = ThaiBuddhistDate.of(2560, 1, 1);
        LocalDate end = LocalDate.of(2018, 3, 15);
        checkPeriod(start.until(end), 1, 2, 14, "forward ISO");
    }

    static void testBackwardPeriodIsNegative() {
        ThaiBuddhistDate start = ThaiBuddhistDate.of(2561, 3, 15);
        ThaiBuddhistDate end = ThaiBuddhistDate.of(2560, 1, 1);
        checkPeriod(start.until(end), -1, -2, -14, "backward");
    }

    static void testSameDateIsZero() {
        ThaiBuddhistDate start = ThaiBuddhistDate.of(2560, 6, 10);
        checkPeriod(start.until(ThaiBuddhistDate.of(2560, 6, 10)), 0, 0, 0, "same date");
    }

    public static void main(String[] args) {
        testNullEndDateThrowsNPE();
        testForwardPeriodThaiEndDate();
        testForwardPeriodIsoEndDate();
        testBackwardPeriodIsNegative();
        testSameDateIsZero();
    }
}
