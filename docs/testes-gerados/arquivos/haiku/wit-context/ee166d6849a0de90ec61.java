/*
 * @test
 * @summary Checks that ZoneId.from rejects a TemporalAccessor that exposes no zone information
 * @run main ZoneIdWitupTest
 */

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;

public class ZoneIdWitupTest {

    static class EmptyTemporal implements TemporalAccessor {
        @Override
        public boolean isSupported(TemporalField field) {
            return false;
        }

        @Override
        public long getLong(TemporalField field) {
            throw new UnsupportedOperationException("Unsupported field: " + field);
        }
    }

    public static void main(String[] args) {
        boolean thrown = false;
        try {
            ZoneId.from(new EmptyTemporal());
        } catch (DateTimeException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected DateTimeException from ZoneId.from with no zone information");
        }

        ZoneId utc = ZoneId.of("UTC");
        if (!ZoneId.from(utc.getRules() == null ? utc : utc).equals(utc)) {
            throw new RuntimeException("ZoneId.from should return an equal ZoneId for a ZoneId input");
        }
    }
}
