/*
 * @test
 * @summary Tests for java.time.Year.get(TemporalField)
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.temporal.TemporalField;
import java.time.temporal.ChronoField;

public class YearWitupTest {

    public static void main(String[] args) {
        testGetWithValidField();
        testGetWithInvalidField();
    }

    static void testGetWithValidField() {
        Year year = Year.of(2023);
        try {
            int value = year.get(ChronoField.YEAR);
            if (value != 2023) {
                throw new AssertionError("Expected 2023, got " + value);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid field: " + e);
        }
    }

    static void testGetWithInvalidField() {
        Year year = Year.of(2023);
        // Test with ChronoField.YEAR_OF_ERA, which can throw IllegalArgumentException
        try {
            year.get(ChronoField.YEAR_OF_ERA);
            // No exception expected here, so no assertion
        } catch (IllegalArgumentException e) {
            // Expected for invalid value, but in this context, should not throw
            throw new AssertionError("Unexpected IllegalArgumentException for YEAR_OF_ERA");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
        // Test with an invalid field that is not supported
        try {
            // Creating a dummy TemporalField that is unsupported
            TemporalField invalidField = new TemporalField() {
                @Override public String getDisplayName() { return "Invalid"; }
                @Override public Class<?> getRangeRefinedBy(TemporalAccessor temporal) { return null; }
                @Override public ValueRange rangeRefinedBy(TemporalAccessor temporal) { return null; }
                @Override public boolean isSupportedBy(TemporalAccessor temporal) { return false; }
                @Override public <R extends Temporal> R adjustInto(R temporal, long newValue) { return null; }
                @Override public String toString() { return "DummyField"; }
            };
            year.get(invalidField);
            throw new AssertionError("Expected exception for unsupported field");
        } catch (UnsupportedOperationException | NullPointerException | ClassCastException e) {
            // Expected exceptions for unsupported or invalid fields
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
