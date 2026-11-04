/*
 * @test
 * @summary Test java.time.chrono.IsoChronology.zonedDateTime(Instant, ZoneId) method
 * @run main IsoChronologyWitupTest
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {
    public static void main(String[] args) {
        testZonedDateTimeBasic();
        testZonedDateTimeEpoch();
        testZonedDateTimeWithUtcZone();
    }

    private static void testZonedDateTimeBasic() {
        IsoChronology iso = IsoChronology.INSTANCE;
        Instant instant = Instant.parse("2018-09-01T10:15:30.00Z");
        ZoneId zone = ZoneId.of("Europe/London");
        ZonedDateTime zdt = iso.zonedDateTime(instant, zone);
        if (!zdt.toInstant().equals(instant)) {
            throw new AssertionError("Returned ZonedDateTime instant does not match input instant");
        }
        if (!zdt.getZone().equals(zone)) {
            throw new AssertionError("Returned ZonedDateTime zone does not match input zone");
        }
    }

    private static void testZonedDateTimeEpoch() {
        IsoChronology iso = IsoChronology.INSTANCE;
        Instant instant = Instant.EPOCH;
        ZoneId zone = ZoneId.of("UTC");
        ZonedDateTime zdt = iso.zonedDateTime(instant, zone);
        if (!zdt.toInstant().equals(instant)) {
            throw new AssertionError("ZonedDateTime instant should be epoch");
        }
        if (!zdt.getZone().equals(zone)) {
            throw new AssertionError("ZonedDateTime zone should be UTC");
        }
    }

    private static void testZonedDateTimeWithUtcZone() {
        IsoChronology iso = IsoChronology.INSTANCE;
        Instant instant = Instant.parse("2000-01-01T00:00:00Z");
        ZoneId zone = ZoneId.of("UTC");
        ZonedDateTime zdt = iso.zonedDateTime(instant, zone);
        if (!zdt.toInstant().equals(instant)) {
            throw new AssertionError("ZonedDateTime instant does not match input instant");
        }
        if (!zdt.getZone().equals(zone)) {
            throw new AssertionError("ZonedDateTime zone does not match input zone");
        }
    }
}
