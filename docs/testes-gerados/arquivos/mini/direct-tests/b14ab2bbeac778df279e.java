/*
 * @test
 * @summary Test java.time.LocalTime.from(TemporalAccessor) method behavior
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.time.DateTimeException;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testFromReturnsLocalTimeWhenQuerySucceeds();
        testFromThrowsWhenQueryReturnsNull();
        testFromThrowsWhenTemporalIsNull();
    }

    private static void testFromReturnsLocalTimeWhenQuerySucceeds() {
        TemporalAccessor ta = new TemporalAccessor() {
            @Override
            public <R> R query(java.time.temporal.TemporalQuery<R> query) {
                if (query == TemporalQueries.localTime()) {
                    return (R) LocalTime.of(10, 20, 30);
                }
                return null;
            }

            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                return false;
            }

            @Override
            public long getLong(java.time.temporal.TemporalField field) {
                throw new UnsupportedOperationException();
            }
        };

        LocalTime result = LocalTime.from(ta);
        if (!result.equals(LocalTime.of(10, 20, 30))) {
            throw new AssertionError("Expected LocalTime.of(10,20,30) but got " + result);
        }
    }

    private static void testFromThrowsWhenQueryReturnsNull() {
        TemporalAccessor ta = new TemporalAccessor() {
            @Override
            public <R> R query(java.time.temporal.TemporalQuery<R> query) {
                return null; // simulate no LocalTime available
            }

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
            LocalTime.from(ta);
            throw new AssertionError("Expected DateTimeException not thrown");
        } catch (DateTimeException e) {
            // expected
            String msg = e.getMessage();
            if (!msg.contains("Unable to obtain LocalTime from TemporalAccessor")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testFromThrowsWhenTemporalIsNull() {
        try {
            LocalTime.from(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
            String msg = e.getMessage();
            if (!"temporal".equals(msg)) {
                throw new AssertionError("Unexpected NullPointerException message: " + msg);
            }
        }
    }
}
