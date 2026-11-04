/*
 * @test
 * @summary Verify Period.get(TemporalUnit) returns year, month and day components and rejects unsupported units
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class PeriodWitupTest {

    public static void main(String[] args) {
        Period p = Period.of(3, 14, 25);
        check(p.get(ChronoUnit.YEARS) == 3L, "years of (3,14,25)");
        check(p.get(ChronoUnit.MONTHS) == 14L, "months of (3,14,25)");
        check(p.get(ChronoUnit.DAYS) == 25L, "days of (3,14,25)");

        Period zero = Period.ZERO;
        check(zero.get(ChronoUnit.YEARS) == 0L, "years of ZERO");
        check(zero.get(ChronoUnit.MONTHS) == 0L, "months of ZERO");
        check(zero.get(ChronoUnit.DAYS) == 0L, "days of ZERO");

        Period neg = Period.of(-1, -2, -3);
        check(neg.get(ChronoUnit.YEARS) == -1L, "negative years");
        check(neg.get(ChronoUnit.MONTHS) == -2L, "negative months");
        check(neg.get(ChronoUnit.DAYS) == -3L, "negative days");

        Period noNormalize = Period.of(0, 14, 0);
        check(noNormalize.get(ChronoUnit.MONTHS) == 14L, "months must not be normalized into years");
        check(noNormalize.get(ChronoUnit.YEARS) == 0L, "years must remain zero");

        expectUnsupported(p, ChronoUnit.WEEKS);
        expectUnsupported(p, ChronoUnit.HOURS);
        expectUnsupported(p, null);
    }

    private static void expectUnsupported(Period period, TemporalUnit unit) {
        try {
            long value = period.get(unit);
            throw new RuntimeException("Expected UnsupportedTemporalTypeException for " + unit + " but got " + value);
        } catch (UnsupportedTemporalTypeException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.startsWith("Unsupported unit: ")) {
                throw new RuntimeException("Unexpected message: " + msg, e);
            }
        }
    }

    private static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + what);
        }
    }
}
