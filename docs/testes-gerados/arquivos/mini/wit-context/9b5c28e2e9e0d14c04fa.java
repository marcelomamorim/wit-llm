/*
 * @test
 * @summary Test range(TemporalField) method of HijrahEra enum
 * @run main HijrahEraWitupTest
 */

import java.time.chrono.HijrahEra;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.time.temporal.ChronoField;
import java.time.chrono.Era;

public class HijrahEraWitupTest {
    public static void main(String[] args) {
        testRangeWithEraField();
        testRangeWithNonEraField();
    }

    private static void testRangeWithEraField() {
        HijrahEra era = HijrahEra.AH;
        TemporalField field = ChronoField.ERA;
        ValueRange range = era.range(field);
        // The HijrahEra has only one era with value 1, so range should be 1 to 1
        if (range.getMinimum() != 1 || range.getMaximum() != 1) {
            throw new AssertionError("Expected range 1 to 1 for ERA field, but got: " + range);
        }
    }

    private static void testRangeWithNonEraField() {
        HijrahEra era = HijrahEra.AH;
        // Use a TemporalField that is not ERA, for example DAY_OF_MONTH
        TemporalField field = ChronoField.DAY_OF_MONTH;
        ValueRange range = era.range(field);
        // The default implementation from Era.super.range(field) returns a non-null ValueRange
        if (range == null) {
            throw new AssertionError("Expected non-null ValueRange for non-ERA field");
        }
        // We do not assert exact values because Era.super.range(field) behavior depends on Era implementation
    }
}
