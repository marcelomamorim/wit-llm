/*
 * @test
 * @summary Verify ChronoPeriodImpl.get(TemporalUnit) returns years, months and days and rejects other units
 * @run main ChronoPeriodImplWitupTest
 */

import java.time.chrono.ChronoPeriod;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class ChronoPeriodImplWitupTest {

    public static void main(String[] args) {
        testPositivePeriod();
        testZeroPeriod();
        testNegativePeriod();
        testUnsupportedUnits();
        testNullUnit();
    }

    static void testPositivePeriod() {
        ChronoPeriod p = IsoChronology.INSTANCE.period(1, 2, 3);
        check(p.get(ChronoUnit.YEARS) == 1L, "YEARS expected 1");
        check(p.get(ChronoUnit.MONTHS) == 2L, "MONTHS expected 2");
        check(p.get(ChronoUnit.DAYS) == 3L, "DAYS expected 3");
    }

    static void testZeroPeriod() {
        ChronoPeriod p = IsoChronology.INSTANCE.period(0, 0, 0);
        check(p.get(ChronoUnit.YEARS) == 0L, "zero YEARS");
        check(p.get(ChronoUnit.MONTHS) == 0L, "zero MONTHS");
        check(p.get(ChronoUnit.DAYS) == 0L, "zero DAYS");
    }

    static void testNegativePeriod() {
        ChronoPeriod p = IsoChronology.INSTANCE.period(-4, -5, -6);
        check(p.get(ChronoUnit.YEARS) == -4L, "negative YEARS");
        check(p.get(ChronoUnit.MONTHS) == -5L, "negative MONTHS");
        check(p.get(ChronoUnit.DAYS) == -6L, "negative DAYS");
    }

    static void testUnsupportedUnits() {
        ChronoPeriod p = IsoChronology.INSTANCE.period(1, 2, 3);
        expectUnsupported(p, ChronoUnit.WEEKS);
        expectUnsupported(p, ChronoUnit.HOURS);
        expectUnsupported(p, ChronoUnit.DECADES);
    }

    static void testNullUnit() {
        ChronoPeriod p = IsoChronology.INSTANCE.period(1, 2, 3);
        expectUnsupported(p, null);
    }

    static void expectUnsupported(ChronoPeriod p, java.time.temporal.TemporalUnit unit) {
        try {
            long v = p.get(unit);
            throw new RuntimeException("Expected UnsupportedTemporalTypeException for " + unit + " but got " + v);
        } catch (UnsupportedTemporalTypeException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
