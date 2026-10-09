/* @test    @summary Test Period.get(TemporalUnit) for supported units and exception for unsupported units    @run main PeriodWitupTest_P9d6426710a0c */

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class PeriodWitupTest_P9d6426710a0c {

    public static void main(String[] args) {
        testGetYears();
        testGetMonths();
        testGetDays();
        testGetUnsupportedUnit();
    }

    static void testGetYears() {
        Period period = Period.of(3, 4, 5);
        long years = period.get(ChronoUnit.YEARS);
        if (years != 3) {
            throw new AssertionError("Expected 3 years, but got " + years);
        }
    }

    static void testGetMonths() {
        Period period = Period.of(3, 4, 5);
        long months = period.get(ChronoUnit.MONTHS);
        if (months != 4) {
            throw new AssertionError("Expected 4 months, but got " + months);
        }
    }

    static void testGetDays() {
        Period period = Period.of(3, 4, 5);
        long days = period.get(ChronoUnit.DAYS);
        if (days != 5) {
            throw new AssertionError("Expected 5 days, but got " + days);
        }
    }

    static void testGetUnsupportedUnit() {
        Period period = Period.of(1, 2, 3);
        try {
            period.get(ChronoUnit.HOURS);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for HOURS");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
