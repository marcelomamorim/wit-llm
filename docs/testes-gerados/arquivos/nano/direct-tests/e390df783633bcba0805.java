/* @test    @summary Test ChronoPeriodImpl.get() method for supported units and exception handling    @run main ChronoPeriodImplWitupTest */

import java.time.chrono.ChronoPeriodImpl;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class ChronoPeriodImplWitupTest {

    public static void main(String[] args) {
        testGetYears();
        testGetMonths();
        testGetDays();
        testGetUnsupportedUnit();
    }

    static void testGetYears() {
        ChronoPeriodImpl period = new ChronoPeriodImpl(2, 3, 4); // years=2, months=3, days=4
        long result = period.get(ChronoUnit.YEARS);
        if (result != 2) {
            throw new AssertionError("Expected 2 for YEARS, but got " + result);
        }
    }

    static void testGetMonths() {
        ChronoPeriodImpl period = new ChronoPeriodImpl(2, 3, 4); // years=2, months=3, days=4
        long result = period.get(ChronoUnit.MONTHS);
        if (result != 3) {
            throw new AssertionError("Expected 3 for MONTHS, but got " + result);
        }
    }

    static void testGetDays() {
        ChronoPeriodImpl period = new ChronoPeriodImpl(2, 3, 4); // years=2, months=3, days=4
        long result = period.get(ChronoUnit.DAYS);
        if (result != 4) {
            throw new AssertionError("Expected 4 for DAYS, but got " + result);
        }
    }

    static void testGetUnsupportedUnit() {
        ChronoPeriodImpl period = new ChronoPeriodImpl(1, 1, 1);
        try {
            period.get(ChronoUnit.HOURS);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for HOURS");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
