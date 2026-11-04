/*
 * @test
 * @summary Test rangeRefinedBy method of JulianFields.Field for supported and unsupported TemporalAccessor
 * @run main JulianFieldsWitupTest
 */

import java.time.DateTimeException;
import java.time.temporal.JulianFields;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;

public class JulianFieldsWitupTest {

    public static void main(String[] args) {
        testRangeRefinedBySupported();
        testRangeRefinedByUnsupported();
    }

    private static void testRangeRefinedBySupported() {
        // Create a TemporalAccessor that supports the field
        TemporalAccessor supportedTemporal = new TemporalAccessor() {
            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                // Support only JulianFields.JULIAN_DAY for test
                return field == JulianFields.JULIAN_DAY;
            }

            @Override
            public long getLong(java.time.temporal.TemporalField field) {
                if (field == JulianFields.JULIAN_DAY) {
                    return 2451545L; // arbitrary valid Julian Day
                }
                throw new UnsupportedOperationException();
            }
        };

        JulianFields.Field field = JulianFields.JULIAN_DAY;
        ValueRange range = field.rangeRefinedBy(supportedTemporal);
        if (range == null) {
            throw new AssertionError("rangeRefinedBy returned null for supported temporal");
        }
        // The range should be equal to field.range() as per method contract
        if (!range.equals(field.range())) {
            throw new AssertionError("rangeRefinedBy returned unexpected range");
        }
    }

    private static void testRangeRefinedByUnsupported() {
        // Create a TemporalAccessor that does not support the field
        TemporalAccessor unsupportedTemporal = new TemporalAccessor() {
            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                return false; // supports no fields
            }

            @Override
            public long getLong(java.time.temporal.TemporalField field) {
                throw new UnsupportedOperationException();
            }
        };

        JulianFields.Field field = JulianFields.JULIAN_DAY;
        try {
            field.rangeRefinedBy(unsupportedTemporal);
            throw new AssertionError("Expected DateTimeException not thrown for unsupported temporal");
        } catch (DateTimeException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Unsupported field")) {
                throw new AssertionError("DateTimeException message unexpected: " + msg);
            }
        }
    }
}
