/*
 * @test
 * @summary Tests for java.time.LocalTime
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.DateTimeException;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testFromWithNull();
        testFromWithValidTemporalAccessor();
        testFromWithInvalidTemporalAccessor();
    }

    public static void testFromWithNull() {
        try {
            LocalTime.from(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testFromWithValidTemporalAccessor() {
        // Using LocalTime as a valid TemporalAccessor
        LocalTime expectedTime = LocalTime.of(12, 30, 45);
        TemporalAccessor ta = expectedTime;
        LocalTime result = LocalTime.from(ta);
        if (!expectedTime.equals(result)) {
            throw new AssertionError("Expected: " + expectedTime + ", but got: " + result);
        }
    }

    public static void testFromWithInvalidTemporalAccessor() {
        // Using an object that is not a LocalTime and does not query localTime()
        class DummyTemporal implements TemporalAccessor {
            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                return false;
            }
            @Override
            public Object getLong(java.time.temporal.TemporalField field) {
                throw new UnsupportedOperationException();
            }
            @Override
            public <R> R query(java.time.temporal.TemporalQuery<R> query) {
                if (query == java.time.temporal.TemporalQueries.localTime()) {
                    return null;
                }
                return null;
            }
        }
        DummyTemporal dt = new DummyTemporal();
        try {
            LocalTime.from(dt);
            throw new AssertionError("Expected DateTimeException for invalid TemporalAccessor");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }
}
