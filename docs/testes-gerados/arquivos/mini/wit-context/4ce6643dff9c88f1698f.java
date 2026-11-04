/*
 * @test
 * @summary Test IsoChronology.zonedDateTime(Instant, ZoneId) for null argument handling and normal behavior
 * @run main IsoChronologyWitupTest
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {
    public static void main(String[] args) {
        IsoChronology iso = IsoChronology.INSTANCE;

        // Test normal behavior with valid Instant and ZoneId
        Instant now = Instant.now();
        ZoneId zone = ZoneId.of("UTC");
        ZonedDateTime zdt = iso.zonedDateTime(now, zone);
        if (zdt == null) {
            throw new AssertionError("zonedDateTime returned null for valid inputs");
        }
        if (!zdt.toInstant().equals(now)) {
            throw new AssertionError("zonedDateTime result instant does not match input instant");
        }
        if (!zdt.getZone().equals(zone)) {
            throw new AssertionError("zonedDateTime result zone does not match input zone");
        }

        // Test null Instant argument triggers NullPointerException
        try {
            iso.zonedDateTime(null, zone);
            throw new AssertionError("Expected NullPointerException for null instant");
        } catch (NullPointerException expected) {
        }

        // Test null ZoneId argument triggers NullPointerException
        try {
            iso.zonedDateTime(now, null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException expected) {
        }
    }
}
