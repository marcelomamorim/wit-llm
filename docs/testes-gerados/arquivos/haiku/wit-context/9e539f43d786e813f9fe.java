/*
 * @test
 * @summary WITUP-derived tests for Period.get(TemporalUnit) observable behavior
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class PeriodWitupTest {

    private static final Period P = Period.of(1, 2, 3);

    public static void main(String[] args) {
        checkSupported();
        checkUnsupported(ChronoUnit.HOURS);
        checkUnsupported(ChronoUnit.WEEKS);
        checkUnsupported(null);
        checkNegativeComponents();
    }

    private static void checkSupported() {
        if (P.get(ChronoUnit.YEARS) != 1) {
            throw new RuntimeException("YEARS expected 1");
        }
        if (P.get(ChronoUnit.MONTHS) != 2) {
            throw new RuntimeException("MONTHS expected 2");
        }
        if (P.get(ChronoUnit.DAYS) != 3) {
            throw new RuntimeException("DAYS expected 3");
        }
    }

    private static void checkNegativeComponents() {
        Period n = Period.of(-4, 5, -6);
        if (n.get(ChronoUnit.YEARS) != -4) {
            throw new RuntimeException("negative YEARS expected -4");
        }
        if (n.get(ChronoUnit.MONTHS) != 5) {
            throw new RuntimeException("MONTHS expected 5");
        }
        if (n.get(ChronoUnit.DAYS) != -6) {
            throw new RuntimeException("negative DAYS expected -6");
        }
    }

    private static void checkUnsupported(TemporalUnit unit) {
        try {
            long value = P.get(unit);
            throw new RuntimeException("expected UnsupportedTemporalTypeException, got " + value);
        } catch (UnsupportedTemporalTypeException expected) {
            // expected
        }
    }
}
