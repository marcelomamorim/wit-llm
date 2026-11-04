/*
 * @test
 * @summary Witup-generated checks for HijrahEra.range(TemporalField)
 * @run main HijrahEraWitupTest
 */

import java.time.chrono.HijrahEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;

public class HijrahEraWitupTest {

    public static void main(String[] args) {
        testEraFieldRangeIsOneToOne();
        testOfReturnsAh();
        testUnsupportedChronoFieldThrows();
        testSupportedChronoFieldRangeIsNotEraRange();
    }

    static void testEraFieldRangeIsOneToOne() {
        ValueRange range = HijrahEra.AH.range(ChronoField.ERA);
        if (!range.equals(ValueRange.of(1, 1))) {
            throw new RuntimeException("Expected ERA range 1..1 but got " + range);
        }
    }

    static void testOfReturnsAh() {
        if (HijrahEra.of(1) != HijrahEra.AH) {
            throw new RuntimeException("HijrahEra.of(1) should return AH");
        }
    }

    static void testUnsupportedChronoFieldThrows() {
        TemporalField field = ChronoField.YEAR;
        try {
            HijrahEra.AH.range(field);
        } catch (UnsupportedTemporalTypeException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedTemporalTypeException for YEAR");
    }

    static void testSupportedChronoFieldRangeIsNotEraRange() {
        TemporalField field = ChronoField.ERA;
        ValueRange eraRange = HijrahEra.AH.range(field);
        ValueRange fieldRange = field.range();
        if (eraRange.equals(fieldRange)) {
            throw new RuntimeException("HijrahEra ERA range should override the default ERA range");
        }
    }
}
