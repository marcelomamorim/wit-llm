/* @test    @summary Test rangeRefinedBy method of JulianFields for supported and unsupported TemporalAccessor instances    @run main JulianFieldsWitupTest */

import java.time.DateTimeException;
import java.time.temporal.JulianFields;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;

public class JulianFieldsWitupTest {

    public static void main(String[] args) {
        testRangeRefinedBySupported();
        testRangeRefinedByUnsupported();
    }

    static void testRangeRefinedBySupported() {
        // Create a mock TemporalAccessor that supports the field
        TemporalAccessor supportedTemporal = new TemporalAccessor() {
            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                return field == java.time.temporal.ChronoField.EPOCH_DAY;
            }

            @Override
            public Object getLong(java.time.temporal.TemporalField field) {
                if (field == java.time.temporal.ChronoField.EPOCH_DAY) {
                    return 100L;
                }
                throw new java.time.DateTimeException("Unsupported field") ;
            }
        };
        try {
            ValueRange range = JulianFields.rangeRefinedBy(supportedTemporal);
            if (range == null) {
                throw new AssertionError("rangeRefinedBy returned null for supported TemporalAccessor");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for supported TemporalAccessor: " + e);
        }
    }

    static void testRangeRefinedByUnsupported() {
        // Create a mock TemporalAccessor that does not support the field
        TemporalAccessor unsupportedTemporal = new TemporalAccessor() {
            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                return false;
            }

            @Override
            public Object getLong(java.time.temporal.TemporalField field) {
                throw new java.time.DateTimeException("Field not supported") ;
            }
        };
        try {
            JulianFields.rangeRefinedBy(unsupportedTemporal);
            throw new AssertionError("Expected DateTimeException was not thrown");
        } catch (DateTimeException e) {
            if (!e.getMessage().contains("Unsupported field")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
