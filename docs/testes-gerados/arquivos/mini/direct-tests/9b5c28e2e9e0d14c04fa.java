/*
 * @test
 * @summary Test java.time.chrono.HijrahEra.range(TemporalField) method
 * @run main HijrahEraWitupTest
 */

import java.time.chrono.HijrahEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.time.temporal.UnsupportedTemporalTypeException;

public class HijrahEraWitupTest {
    public static void main(String[] args) {
        testRangeWithEraField();
        testRangeWithUnsupportedField();
    }

    private static void testRangeWithEraField() {
        ValueRange range = HijrahEra.AH.range(ChronoField.ERA);
        if (range == null) {
            throw new AssertionError("range returned null for ERA field");
        }
        if (range.getMinimum() != 1 || range.getMaximum() != 1) {
            throw new AssertionError("range for ERA field expected min=1 and max=1 but got min="
                + range.getMinimum() + " max=" + range.getMaximum());
        }
    }

    private static void testRangeWithUnsupportedField() {
        // Use a TemporalField that is not ERA and not supported by HijrahEra
        TemporalField unsupportedField = ChronoField.DAY_OF_MONTH;
        try {
            HijrahEra.AH.range(unsupportedField);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for unsupported field");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
