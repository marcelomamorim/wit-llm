/*
 * @test
 * @summary Test java.time.LocalDate.from(TemporalAccessor) method behavior
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.time.DateTimeException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testFromWithValidTemporalAccessor();
        testFromWithNull();
        testFromWithInvalidTemporalAccessor();
    }

    private static void testFromWithValidTemporalAccessor() {
        TemporalAccessor validTemporal = new TemporalAccessor() {
            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                return false;
            }

            @Override
            public long getLong(java.time.temporal.TemporalField field) {
                throw new UnsupportedOperationException();
            }

            @Override
            public <R> R query(java.time.temporal.TemporalQuery<R> query) {
                if (query == TemporalQueries.localDate()) {
                    return (R) LocalDate.of(2020, 5, 20);
                }
                return null;
            }
        };

        LocalDate result = LocalDate.from(validTemporal);
        if (!result.equals(LocalDate.of(2020, 5, 20))) {
            throw new AssertionError("Expected LocalDate.of(2020, 5, 20) but got " + result);
        }
    }

    private static void testFromWithNull() {
        try {
            LocalDate.from(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testFromWithInvalidTemporalAccessor() {
        TemporalAccessor invalidTemporal = new TemporalAccessor() {
            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                return false;
            }

            @Override
            public long getLong(java.time.temporal.TemporalField field) {
                throw new UnsupportedOperationException();
            }

            @Override
            public <R> R query(java.time.temporal.TemporalQuery<R> query) {
                return null; // always returns null for localDate query
            }
        };

        try {
            LocalDate.from(invalidTemporal);
            throw new AssertionError("Expected DateTimeException for invalid TemporalAccessor");
        } catch (DateTimeException dte) {
            // expected
        }
    }
}
