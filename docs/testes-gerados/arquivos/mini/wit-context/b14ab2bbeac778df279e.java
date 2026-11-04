/*
 * @test
 * @summary Test java.time.LocalTime.from(TemporalAccessor) method behavior
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.time.DateTimeException;
import java.util.Objects;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testFromWithNull();
        testFromWithValidTemporalAccessor();
        testFromWithInvalidTemporalAccessor();
    }

    private static void testFromWithNull() {
        try {
            LocalTime.from(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testFromWithValidTemporalAccessor() {
        // Create a TemporalAccessor that returns a LocalTime via query
        TemporalAccessor ta = new TemporalAccessor() {
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
                if (query == TemporalQueries.localTime()) {
                    return (R) LocalTime.of(12, 34, 56, 789);
                }
                return null;
            }
        };

        LocalTime result = LocalTime.from(ta);
        if (!result.equals(LocalTime.of(12, 34, 56, 789))) {
            throw new AssertionError("Expected LocalTime.of(12,34,56,789) but got " + result);
        }
    }

    private static void testFromWithInvalidTemporalAccessor() {
        // Create a TemporalAccessor that returns null for localTime query
        TemporalAccessor ta = new TemporalAccessor() {
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
                return null; // always returns null
            }
        };

        try {
            LocalTime.from(ta);
            throw new AssertionError("Expected DateTimeException for TemporalAccessor with no LocalTime");
        } catch (DateTimeException dte) {
            // expected
        }
    }
}
