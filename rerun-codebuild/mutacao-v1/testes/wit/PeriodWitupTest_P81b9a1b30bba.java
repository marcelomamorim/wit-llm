/* @test    @summary Test for Period.get(TemporalUnit) method to verify exception handling for unsupported units    @run main PeriodWitupTest_P81b9a1b30bba */

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class PeriodWitupTest_P81b9a1b30bba {

    public static void main(String[] args) {
        testGetWithSupportedUnits();
        testGetWithUnsupportedUnit();
    }

    static void testGetWithSupportedUnits() {
        Period p = Period.of(1, 2, 3);
        long years = p.get(ChronoUnit.YEARS);
        long months = p.get(ChronoUnit.MONTHS);
        long days = p.get(ChronoUnit.DAYS);
        if (years != 1) {
            throw new AssertionError("Expected 1 year, got " + years);
        }
        if (months != 2) {
            throw new AssertionError("Expected 2 months, got " + months);
        }
        if (days != 3) {
            throw new AssertionError("Expected 3 days, got " + days);
        }
    }

    static void testGetWithUnsupportedUnit() {
        Period p = Period.of(1, 2, 3);
        try {
            p.get(ChronoUnit.WEEKS);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for WEEKS");
        } catch (java.time.temporal.UnsupportedTemporalTypeException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("Unsupported unit")) {
                throw new AssertionError("Unexpected exception message: " + message);
            }
        }
    }
}
