/*
 * @test
 * @summary Verifies ChronoPeriod.get(TemporalUnit) observable behavior for ChronoPeriodImpl instances created through IsoChronology.period, including unsupported and null units
 * @run main ChronoPeriodImplWitupTest
 */

import java.time.chrono.ChronoPeriod;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class ChronoPeriodImplWitupTest {

    public static void main(String[] args) throws Exception {
        ChronoPeriod p = IsoChronology.INSTANCE.period(1, 2, 3);
        checkEquals(1L, p.get(ChronoUnit.YEARS), "years");
        checkEquals(2L, p.get(ChronoUnit.MONTHS), "months");
        checkEquals(3L, p.get(ChronoUnit.DAYS), "days");

        ChronoPeriod neg = IsoChronology.INSTANCE.period(-4, 0, -7);
        checkEquals(-4L, neg.get(ChronoUnit.YEARS), "negative years");
        checkEquals(0L, neg.get(ChronoUnit.MONTHS), "zero months");
        checkEquals(-7L, neg.get(ChronoUnit.DAYS), "negative days");

        ChronoPeriod zero = IsoChronology.INSTANCE.period(0, 0, 0);
        checkEquals(0L, zero.get(ChronoUnit.YEARS), "zero years");
        checkEquals(0L, zero.get(ChronoUnit.MONTHS), "zero months 2");
        checkEquals(0L, zero.get(ChronoUnit.DAYS), "zero days");

        checkUnsupported(p, ChronoUnit.WEEKS);
        checkUnsupported(p, ChronoUnit.HOURS);
        checkUnsupported(p, ChronoUnit.FOREVER);
        checkUnsupported(neg, ChronoUnit.MILLIS);
        checkUnsupported(p, null);
    }

    private static void checkEquals(long expected, long actual, String what) {
        if (expected != actual) {
            throw new AssertionError(what + ": expected " + expected + " but was " + actual);
        }
    }

    private static void checkUnsupported(ChronoPeriod period, TemporalUnit unit) {
        try {
            long value = period.get(unit);
            throw new AssertionError("expected UnsupportedTemporalTypeException for " + unit + " but got " + value);
        } catch (UnsupportedTemporalTypeException expected) {
            // expected path
        }
    }
}
