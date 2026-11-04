/*
 * @test
 * @summary Test java.time.LocalDate.from(TemporalAccessor) method for valid and invalid inputs
 * @run main java.time.LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.time.DateTimeException;
import java.util.Objects;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testFromWithNull();
        testFromWithValidTemporalAccessor();
        testFromWithInvalidTemporalAccessor();
    }

    private static void testFromWithNull() {
        try {
            LocalDate.from(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }

    private static void testFromWithValidTemporalAccessor() {
        // Create a TemporalAccessor that returns a LocalDate via query
        TemporalAccessor validAccessor = new TemporalAccessor() {
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
                    return (R) LocalDate.of(2020, 1, 2);
                }
                return null;
            }
        };

        LocalDate result = LocalDate.from(validAccessor);
        if (!result.equals(LocalDate.of(2020, 1, 2))) {
            throw new AssertionError("Expected LocalDate.of(2020,1,2) but got " + result);
        }
    }

    private static void testFromWithInvalidTemporalAccessor() {
        // Create a TemporalAccessor that returns null for localDate query
        TemporalAccessor invalidAccessor = new TemporalAccessor() {
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
                return null; // Always returns null for any query
            }
        };

        try {
            LocalDate.from(invalidAccessor);
            throw new AssertionError("Expected DateTimeException for TemporalAccessor with null LocalDate query");
        } catch (DateTimeException dte) {
            // Expected exception
        }
    }
}
