/*
 * @test
 * @summary Tests for ZoneOffset.from(TemporalAccessor)
 * @run main ZoneOffsetWitupTest
 */

import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.time.DateTimeException;

public class ZoneOffsetWitupTest {

    public static void main(String[] args) {
        testValidOffset();
        testNullInput();
        testInvalidTemporalAccessor();
    }

    static void testValidOffset() {
        // Create a TemporalAccessor with an offset query returning a valid ZoneOffset
        TemporalAccessor ta = new TemporalAccessor() {
            @Override
            public <R> R query(TemporalQueries.Query<R> query) {
                if (query == TemporalQueries.offset()) {
                    return (R) ZoneOffset.ofHours(2);
                }
                return null;
            }
            @Override
            public boolean isSupported(TemporalField field) {
                return false;
            }
        };
        ZoneOffset offset = ZoneOffset.from(ta);
        assert offset.equals(ZoneOffset.ofHours(2)) : "Expected ZoneOffset of +02:00";
    }

    static void testNullInput() {
        try {
            ZoneOffset.from(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testInvalidTemporalAccessor() {
        // Create a TemporalAccessor that returns null for offset query
        TemporalAccessor ta = new TemporalAccessor() {
            @Override
            public <R> R query(TemporalQueries.Query<R> query) {
                if (query == TemporalQueries.offset()) {
                    return null;
                }
                return null;
            }
            @Override
            public boolean isSupported(TemporalField field) {
                return false;
            }
        };
        try {
            ZoneOffset.from(ta);
            assert false : "Expected DateTimeException";
        } catch (DateTimeException e) {
            // expected
        }
    }
}
