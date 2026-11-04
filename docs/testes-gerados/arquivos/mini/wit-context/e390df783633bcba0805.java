/*
 * @test
 * @summary Test java.time.chrono.ChronoPeriodImpl.get(TemporalUnit) method behavior
 * @run main ChronoPeriodImplWitupTest
 */

import java.time.chrono.ChronoPeriodImpl;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class ChronoPeriodImplWitupTest {

    public static void main(String[] args) {
        testGetYears();
        testGetMonths();
        testGetDays();
        testGetUnsupportedUnit();
    }

    private static void testGetYears() {
        ChronoPeriodImpl period = new ChronoPeriodImpl(5, 0, 0);
        long result = period.get(ChronoUnit.YEARS);
        if (result != 5) {
            throw new AssertionError("Expected 5 years but got " + result);
        }
    }

    private static void testGetMonths() {
        ChronoPeriodImpl period = new ChronoPeriodImpl(0, 7, 0);
        long result = period.get(ChronoUnit.MONTHS);
        if (result != 7) {
            throw new AssertionError("Expected 7 months but got " + result);
        }
    }

    private static void testGetDays() {
        ChronoPeriodImpl period = new ChronoPeriodImpl(0, 0, 9);
        long result = period.get(ChronoUnit.DAYS);
        if (result != 9) {
            throw new AssertionError("Expected 9 days but got " + result);
        }
    }

    private static void testGetUnsupportedUnit() {
        ChronoPeriodImpl period = new ChronoPeriodImpl(1, 2, 3);
        try {
            period.get(new TemporalUnit() {
                public String getName() { return "UnsupportedUnit"; }
                public boolean isDurationEstimated() { return false; }
                public boolean isDateBased() { return false; }
                public boolean isTimeBased() { return false; }
                public java.time.Duration getDuration() { return java.time.Duration.ZERO; }
                public <R extends java.time.temporal.Temporal> R addTo(R temporal, long amount) { throw new UnsupportedOperationException(); }
                public long between(java.time.temporal.Temporal temporal1Inclusive, java.time.temporal.Temporal temporal2Exclusive) { throw new UnsupportedOperationException(); }
            });
            throw new AssertionError("Expected UnsupportedTemporalTypeException not thrown");
        } catch (UnsupportedTemporalTypeException e) {
            // Expected exception
        }
    }
}
