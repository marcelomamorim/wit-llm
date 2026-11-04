/*
 * @test
 * @summary ThaiBuddhistDate.until returns the Thai Buddhist period between two dates
 * @run main ThaiBuddhistDateWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.chrono.ThaiBuddhistDate;

public class ThaiBuddhistDateWitupTest {

    public static void main(String[] args) {
        testOneYearForward();
        testDaysAcrossIsoDate();
        testNegativePeriod();
        testZeroPeriod();
        testResultChronology();
    }

    static void check(String msg, int expected, int actual) {
        if (expected != actual) {
            throw new RuntimeException(msg + ": expected " + expected + " but got " + actual);
        }
    }

    static void testOneYearForward() {
        ChronoPeriod p = ThaiBuddhistDate.of(2555, 1, 1).until(ThaiBuddhistDate.of(2556, 1, 1));
        check("years", 1, p.getYears());
        check("months", 0, p.getMonths());
        check("days", 0, p.getDays());
    }

    static void testDaysAcrossIsoDate() {
        ChronoPeriod p = ThaiBuddhistDate.of(2555, 3, 15).until(LocalDate.of(2012, 3, 20));
        check("years", 0, p.getYears());
        check("months", 0, p.getMonths());
        check("days", 5, p.getDays());
    }

    static void testNegativePeriod() {
        ChronoPeriod p = ThaiBuddhistDate.of(2555, 1, 1).until(LocalDate.of(2011, 1, 1));
        check("years", -1, p.getYears());
        check("months", 0, p.getMonths());
        check("days", 0, p.getDays());
    }

    static void testZeroPeriod() {
        ThaiBuddhistDate d = ThaiBuddhistDate.of(2555, 6, 10);
        ChronoPeriod p = d.until(d);
        check("years", 0, p.getYears());
        check("months", 0, p.getMonths());
        check("days", 0, p.getDays());
    }

    static void testResultChronology() {
        ChronoPeriod p = ThaiBuddhistDate.of(2555, 1, 1).until(ThaiBuddhistDate.of(2556, 1, 1));
        if (!ThaiBuddhistChronology.INSTANCE.equals(p.getChronology())) {
            throw new RuntimeException("period chronology is not ThaiBuddhistChronology: " + p.getChronology());
        }
    }
}
