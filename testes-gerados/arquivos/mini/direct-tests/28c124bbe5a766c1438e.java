/*
 * @test
 * @summary Test java.time.ZoneOffset.from(TemporalAccessor) method behavior
 * @run main ZoneOffsetWitupTest
 */

import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalQueries;
import java.time.DateTimeException;

public class ZoneOffsetWitupTest {
    public static void main(String[] args) {
        testFromWithValidOffset();
        testFromWithNullThrowsNPE();
        testFromWithNoOffsetThrowsDateTimeException();
    }

    private static void testFromWithValidOffset() {
        // TemporalAccessor that returns a fixed ZoneOffset of +02:00
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
            public <R> R query(TemporalQuery<R> query) {
                if (query == TemporalQueries.offset()) {
                    return (R) ZoneOffset.ofHours(2);
                }
                return null;
            }
        };

        ZoneOffset offset = ZoneOffset.from(ta);
        if (!offset.equals(ZoneOffset.ofHours(2))) {
            throw new AssertionError("Expected ZoneOffset.ofHours(2), got " + offset);
        }
    }

    private static void testFromWithNullThrowsNPE() {
        try {
            ZoneOffset.from(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testFromWithNoOffsetThrowsDateTimeException() {
        // TemporalAccessor that returns null for offset query
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
            public <R> R query(TemporalQuery<R> query) {
                return null;
            }
        };

        try {
            ZoneOffset.from(ta);
            throw new AssertionError("Expected DateTimeException when offset is null");
        } catch (DateTimeException dte) {
            // expected
        }
    }
}
