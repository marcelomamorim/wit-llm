/*
 * @test
 * @summary Test java.time.Period.get(TemporalUnit) method behavior
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class PeriodWitupTest {
    public static void main(String[] args) {
        testGetYears();
        testGetMonths();
        testGetDays();
        testGetUnsupportedUnit();
    }

    private static void testGetYears() {
        Period p = Period.of(5, 0, 0);
        long years = p.get(ChronoUnit.YEARS);
        if (years != 5) {
            throw new AssertionError("Expected 5 years but got " + years);
        }
    }

    private static void testGetMonths() {
        Period p = Period.of(0, 7, 0);
        long months = p.get(ChronoUnit.MONTHS);
        if (months != 7) {
            throw new AssertionError("Expected 7 months but got " + months);
        }
    }

    private static void testGetDays() {
        Period p = Period.of(0, 0, 9);
        long days = p.get(ChronoUnit.DAYS);
        if (days != 9) {
            throw new AssertionError("Expected 9 days but got " + days);
        }
    }

    private static void testGetUnsupportedUnit() {
        Period p = Period.of(1, 2, 3);
        TemporalUnit unsupportedUnit = new TemporalUnit() {
            @Override
            public String getName() {
                return "UnsupportedUnit";
            }

            @Override
            public boolean isDurationEstimated() {
                return false;
            }

            @Override
            public boolean isDateBased() {
                return false;
            }

            @Override
            public boolean isTimeBased() {
                return false;
            }

            @Override
            public java.time.Duration getDuration() {
                return java.time.Duration.ZERO;
            }
        };

        try {
            p.get(unsupportedUnit);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for unsupported unit");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
