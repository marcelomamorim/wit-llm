/*
 * @test
 * @summary Tests for java.time.temporal.JulianFields
 * @run main JulianFieldsWitupTest
 */

import java.time.temporal.JulianFields;
import java.time.temporal.ValueRange;
import java.time.temporal.TemporalAccessor;
import java.time.DateTimeException;

public class JulianFieldsWitupTest {

    public static void main(String[] args) {
        testRangeRefinedBySupported();
        testRangeRefinedByUnsupported();
    }

    private static void testRangeRefinedBySupported() {
        // Cria um objeto anônimo que implementa TemporalAccessor e suporta EPOCH_DAY
        TemporalAccessor supportedTemporal = new TemporalAccessor() {
            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                return field == java.time.temporal.ChronoField.EPOCH_DAY;
            }
            @Override
            public Object getLong(java.time.temporal.TemporalField field) {
                if (field == java.time.temporal.ChronoField.EPOCH_DAY) {
                    return 0L;
                }
                throw new UnsupportedOperationException();
            }
        };
        try {
            ValueRange range = JulianFields.rangeRefinedBy(supportedTemporal);
            if (range == null) {
                throw new AssertionError("Expected non-null ValueRange");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in supported case", e);
        }
    }

    private static void testRangeRefinedByUnsupported() {
        // Cria um objeto anônimo que implementa TemporalAccessor e NÃO suporta EPOCH_DAY
        TemporalAccessor unsupportedTemporal = new TemporalAccessor() {
            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                return false;
            }
            @Override
            public Object getLong(java.time.temporal.TemporalField field) {
                throw new UnsupportedOperationException();
            }
        };
        try {
            JulianFields.rangeRefinedBy(unsupportedTemporal);
            throw new AssertionError("Expected DateTimeException for unsupported TemporalAccessor");
        } catch (DateTimeException e) {
            // Esperado
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
