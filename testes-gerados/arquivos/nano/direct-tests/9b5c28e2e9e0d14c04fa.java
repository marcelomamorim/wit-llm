/*
 * @test
 * @summary Test the range method of HijrahEra for ERA field
 * @run main HijrahEraWitupTest
 */

import static java.time.temporal.ChronoField.ERA;
import java.time.chrono.HijrahEra;
import java.time.temporal.ValueRange;

public class HijrahEraWitupTest {

    public static void main(String[] args) {
        testRangeForEra();
        testRangeForOtherField();
    }

    static void testRangeForEra() {
        HijrahEra era = HijrahEra.AH;
        ValueRange range = era.range(ERA);
        assert range.equals(ValueRange.of(1, 1)) : "Expected range of 1 to 1 for ERA";
    }

    static void testRangeForOtherField() {
        HijrahEra era = HijrahEra.AH;
        try {
            era.range(java.time.temporal.ChronoField.YEAR);
            assert false : "Expected UnsupportedTemporalTypeException for YEAR";
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
