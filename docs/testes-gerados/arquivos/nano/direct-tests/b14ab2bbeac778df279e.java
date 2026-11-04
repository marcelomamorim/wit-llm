/*
 * @test
 * @summary Tests for LocalTime.from(TemporalAccessor)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.time.DateTimeException;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testFromWithValidTemporalAccessor();
        testFromWithNull();
        testFromWithInvalidTemporalAccessor();
    }

    static void testFromWithValidTemporalAccessor() {
        LocalTime expectedTime = LocalTime.of(12, 30, 45);
        TemporalAccessor ta = expectedTime;
        LocalTime result = LocalTime.from(ta);
        if (!result.equals(expectedTime)) {
            throw new AssertionError("Expected: " + expectedTime + ", but got: " + result);
        }
    }

    static void testFromWithNull() {
        try {
            LocalTime.from(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testFromWithInvalidTemporalAccessor() {
        class DummyTemporal implements TemporalAccessor {
            @Override
            public boolean isSupported(TemporalField field) {
                return false;
            }
            @Override
            public Object query(TemporalQuery<?> query) {
                return null;
            }
        }
        TemporalAccessor ta = new DummyTemporal();
        try {
            LocalTime.from(ta);
            throw new AssertionError("Expected DateTimeException for invalid TemporalAccessor");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
