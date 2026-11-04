/*
 * @test
 * @summary Test java.time.Period.get(TemporalUnit) method behavior including exception for unsupported units
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class PeriodWitupTest {
    public static void main(String[] args) {
        testGetSupportedUnits();
        testGetUnsupportedUnitThrows();
    }

    private static void testGetSupportedUnits() {
        Period p = Period.of(1, 2, 3);
        long years = p.get(ChronoUnit.YEARS);
        if (years != 1) {
            throw new AssertionError("Expected years=1 but got " + years);
        }
        long months = p.get(ChronoUnit.MONTHS);
        if (months != 2) {
            throw new AssertionError("Expected months=2 but got " + months);
        }
        long days = p.get(ChronoUnit.DAYS);
        if (days != 3) {
            throw new AssertionError("Expected days=3 but got " + days);
        }
    }

    private static void testGetUnsupportedUnitThrows() {
        Period p = Period.of(0, 0, 0);
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

            @Override
            public <R extends java.time.temporal.Temporal> R addTo(R temporal, long amount) {
                throw new UnsupportedTemporalTypeException("Unsupported unit");
            }

            @Override
            public long between(java.time.temporal.Temporal temporal1Inclusive, java.time.temporal.Temporal temporal2Exclusive) {
                throw new UnsupportedTemporalTypeException("Unsupported unit");
            }
        };

        boolean thrown = false;
        try {
            p.get(unsupportedUnit);
        } catch (UnsupportedTemporalTypeException e) {
            thrown = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Unsupported unit")) {
                throw new AssertionError("Exception message does not contain expected text: " + msg);
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedTemporalTypeException was not thrown");
        }
    }
}
