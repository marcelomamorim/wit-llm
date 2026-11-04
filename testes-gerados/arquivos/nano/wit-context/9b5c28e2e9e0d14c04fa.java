/* @test    @summary Test for HijrahEra.range method behavior and exception paths    @run main HijrahEraWitupTest */

import static java.time.temporal.ChronoField.ERA;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Locale;

public class HijrahEraWitupTest {

    public static void main(String[] args) {
        testRangeWithEraField();
        testRangeWithUnsupportedField();
    }

    private static void testRangeWithEraField() {
        try {
            java.time.chrono.HijrahEra era = java.time.chrono.HijrahEra.AH;
            ValueRange vr = era.range(ERA);
            if (vr.getMinimum() != 1 || vr.getMaximum() != 1) {
                throw new AssertionError("Expected range 1 to 1 for ERA, but got: " + vr);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for ERA field: " + e, e);
        }
    }

    private static void testRangeWithUnsupportedField() {
        try {
            java.time.chrono.HijrahEra era = java.time.chrono.HijrahEra.AH;
            // Using a dummy unsupported field for testing
            TemporalField unsupportedField = new TemporalField() {
                @Override
                public boolean isSupportedBy(java.time.temporal.Temporal temporal) { return false; }
                @Override
                public ValueRange range() { throw new UnsupportedTemporalTypeException("unsupported"); }
                @Override
                public ValueRange rangeRefinedBy(java.time.temporal.TemporalAccessor temporal) { throw new UnsupportedTemporalTypeException("unsupported"); }
                @Override
                public boolean isDateBased() { return false; }
                @Override
                public boolean isTimeBased() { return false; }
                @Override
                public Class<?> getBaseUnit() { return null; }
                @Override
                public Class<?> getRangeUnit() { return null; }
                @Override
                public String getDisplayName(Locale locale) { return "unsupported"; }
            };
            era.range(unsupportedField);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for unsupported field");
        } catch (UnsupportedTemporalTypeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
