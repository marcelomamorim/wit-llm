/*
 * @test
 * @summary Verifies HijrahEra.range for ChronoField.ERA and unsupported ChronoField values
 * @run main HijrahEraWitupTest
 */

import java.time.chrono.HijrahEra;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;

public class HijrahEraWitupTest {

    public static void main(String[] args) {
        ValueRange r = HijrahEra.AH.range(ChronoField.ERA);
        check(r.getMinimum() == 1 && r.getMaximum() == 1, "ERA range must be 1..1");
        check(r.isFixed(), "ERA range must be fixed");
        check(r.equals(ValueRange.of(1, 1)), "ERA range must equal ValueRange.of(1, 1)");

        ValueRange viaOf = HijrahEra.of(1).range(ChronoField.ERA);
        check(viaOf.equals(ValueRange.of(1, 1)), "HijrahEra.of(1).range(ERA) must be 1..1");

        boolean thrown = false;
        try {
            HijrahEra.AH.range(ChronoField.YEAR);
        } catch (UnsupportedTemporalTypeException e) {
            thrown = true;
        }
        check(thrown, "range(YEAR) must throw UnsupportedTemporalTypeException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
