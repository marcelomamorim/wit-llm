/*
 * @test
 * @summary ZoneOffset.from(TemporalAccessor) returns the offset, rejects null and rejects accessors without an offset
 * @run main ZoneOffsetWitupTest
 */

import java.time.DateTimeException;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;

public class ZoneOffsetWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testNullTemporalThrowsNpe() {
        try {
            ZoneOffset.from(null);
            check(false, "from(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testOffsetReturnedFromZoneOffset() {
        ZoneOffset offset = ZoneOffset.of("+02:00");
        ZoneOffset result = ZoneOffset.from(offset);
        check(result.getTotalSeconds() == 7200, "from(+02:00) should return +02:00");
        check(result == offset, "from(ZoneOffset) should return the same offset instance");
    }

    static void testNegativeOffsetReturned() {
        ZoneOffset result = ZoneOffset.from(ZoneOffset.of("-05:30"));
        check(result.getTotalSeconds() == -19800, "from(-05:30) should return -05:30");
    }

    static void testAccessorWithoutOffsetThrows() {
        TemporalAccessor noOffset = new TemporalAccessor() {
            @Override
            public boolean isSupported(TemporalField field) {
                return false;
            }

            @Override
            public long getLong(TemporalField field) {
                throw new UnsupportedOperationException("not supported");
            }
        };
        try {
            ZoneOffset.from(noOffset);
            check(false, "from(accessor without offset) should throw DateTimeException");
        } catch (DateTimeException e) {
            check(e.getMessage() != null
                    && e.getMessage().contains("Unable to obtain ZoneOffset"),
                    "exception message should mention ZoneOffset: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        testNullTemporalThrowsNpe();
        testOffsetReturnedFromZoneOffset();
        testNegativeOffsetReturned();
        testAccessorWithoutOffsetThrows();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
