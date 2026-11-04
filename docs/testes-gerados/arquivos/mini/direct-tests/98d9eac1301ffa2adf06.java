/*
 * @test
 * @summary Test rangeRefinedBy method of JulianFields for supported and unsupported TemporalAccessor
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
        JulianFields field = JulianFields.JULIAN_DAY;
        TemporalAccessor supportedTemporal = new TemporalAccessor() {
            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                return field == JulianFields.JULIAN_DAY;
            }

            @Override
            public long getLong(java.time.temporal.TemporalField field) {
                if (field == JulianFields.JULIAN_DAY) {
                    return 2451545L; // example Julian Day
                }
                throw new UnsupportedOperationException();
            }
        };

        ValueRange range = field.rangeRefinedBy(supportedTemporal);
        if (range == null) {
            throw new AssertionError("rangeRefinedBy returned null for supported temporal");
        }
    }

    private static void testRangeRefinedByUnsupported() {
        JulianFields field = JulianFields.JULIAN_DAY;
        TemporalAccessor unsupportedTemporal = new TemporalAccessor() {
            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                return false;
            }

            @Override
            public long getLong(java.time.temporal.TemporalField field) {
                throw new UnsupportedOperationException();
            }
        };

        try {
            field.rangeRefinedBy(unsupportedTemporal);
            throw new AssertionError("Expected DateTimeException not thrown");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
