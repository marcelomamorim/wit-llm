/*
 * @test
 * @summary Tests for java.time.ZoneOffset.from(TemporalAccessor)
 * @run main ZoneOffsetWitupTest
 */

import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.UnsupportedTemporalTypeException;

public class ZoneOffsetWitupTest {

    public static void main(String[] args) {
        testNullInput();
        testValidInput();
        testInvalidInput();
    }

    private static void testNullInput() {
        try {
            ZoneOffset.from(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testValidInput() {
        // Use a TemporalAccessor that returns a valid ZoneOffset
        // Since we cannot instantiate TemporalAccessor directly, use a mock or a simple implementation
        // For simplicity, use an anonymous class that returns a fixed offset
        TemporalAccessor ta = new TemporalAccessor() {
            @Override
            public boolean isSupported(TemporalField field) {
                return false;
            }
            @Override
            public Object getLong(TemporalField field) {
                if (field == null) {
                    throw new NullPointerException();
                }
                if (field == java.time.temporal.ChronoField.OFFSET_SECONDS) {
                    return 3600L; // +01:00
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
            }
        };
        ZoneOffset offset = ZoneOffset.from(ta);
        if (offset == null || offset.getTotalSeconds() != 3600) {
            throw new AssertionError("Failed to parse valid TemporalAccessor");
        }
    }

    private static void testInvalidInput() {
        // Use a TemporalAccessor that returns null for query(TemporalQueries.offset())
        TemporalAccessor ta = new TemporalAccessor() {
            @Override
            public boolean isSupported(TemporalField field) {
                return false;
            }
            @Override
            public Object getLong(TemporalField field) {
                throw new UnsupportedTemporalTypeException("Unsupported field")
            }
            @Override
            public <R> R query(java.time.temporal.TemporalQuery<R> query) {
                if (query == java.time.temporal.TemporalQueries.offset()) {
                    return null;
                }
                return null;
            }
        };
        try {
            ZoneOffset.from(ta);
            throw new AssertionError("Expected DateTimeException for null offset query");
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
    }
}
