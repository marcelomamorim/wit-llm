/*
 * @test
 * @summary Test for IsoChronology.zonedDateTime(Instant, ZoneId)
 * @run main IsoChronologyWitupTest
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        testZonedDateTimeWithUTC();
        testZonedDateTimeWithSpecificZone();
    }

    static void testZonedDateTimeWithUTC() {
        Instant instant = Instant.ofEpochSecond(0L);
        ZoneId zone = ZoneId.of("UTC");
        ZonedDateTime zdt = IsoChronology.INSTANCE.zonedDateTime(instant, zone);
        assert zdt != null : "zonedDateTime should not be null";
        assert zdt.getZone().equals(zone) : "Zone should be UTC";
        assert zdt.toInstant().equals(instant) : "Instant should match";
    }

    static void testZonedDateTimeWithSpecificZone() {
        Instant instant = Instant.ofEpochSecond(1625097600L); // 2021-07-01T00:00:00Z
        ZoneId zone = ZoneId.of("America/New_York");
        ZonedDateTime zdt = IsoChronology.INSTANCE.zonedDateTime(instant, zone);
        assert zdt != null : "zonedDateTime should not be null";
        assert zdt.getZone().equals(zone) : "Zone should be America/New_York";
        assert zdt.toInstant().equals(instant) : "Instant should match";
    }
}
