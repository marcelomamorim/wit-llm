/*
 * @test
 * @summary Test java.time.Period.get(TemporalUnit) method for supported units.
 * @run main PeriodWitupTest_P81b9a1b30bba
 */

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class PeriodWitupTest_P81b9a1b30bba {
    public static void main(String[] args) {
        testGetYears();
        testGetMonths();
        testGetDays();
        testGetUnsupportedUnit();
    }

    static void testGetYears() {
        Period p = Period.of(3, 5, 10);
        long years = p.get(ChronoUnit.YEARS);
        if (years != 3) {
            throw new AssertionError("Expected 3 years, got " + years);
        }
    }

    static void testGetMonths() {
        Period p = Period.of(3, 5, 10);
        long months = p.get(ChronoUnit.MONTHS);
        if (months != 5) {
            throw new AssertionError("Expected 5 months, got " + months);
        }
    }

    static void testGetDays() {
        Period p = Period.of(3, 5, 10);
        long days = p.get(ChronoUnit.DAYS);
        if (days != 10) {
            throw new AssertionError("Expected 10 days, got " + days);
        }
    }

    static void testGetUnsupportedUnit() {
        Period p = Period.of(1, 2, 3);
        try {
            p.get(java.time.temporal.ChronoUnit.HOURS);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for HOURS");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
