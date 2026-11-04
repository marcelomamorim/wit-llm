/*
 * @test
 * @summary Checks ZoneId.from rejects temporal accessors without zone information and ZoneId.of still resolves region IDs
 * @run main ZoneIdWitupTest
 */

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class ZoneIdWitupTest {

    public static void main(String[] args) {
        testFromRejectsAccessorWithoutZone();
        testOfRegionStillWorks();
    }

    static void testFromRejectsAccessorWithoutZone() {
        TemporalAccessor accessor = new TemporalAccessor() {
            @Override
            public boolean isSupported(TemporalField field) {
                return false;
            }

            @Override
            public long getLong(TemporalField field) {
                throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
            }
        };
        boolean thrown = false;
        try {
            ZoneId.from(accessor);
        } catch (DateTimeException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected DateTimeException from ZoneId.from");
        }
    }

    static void testOfRegionStillWorks() {
        ZoneId zone = ZoneId.of("Europe/Paris");
        if (!"Europe/Paris".equals(zone.getId())) {
            throw new AssertionError("unexpected id: " + zone.getId());
        }
    }
}
