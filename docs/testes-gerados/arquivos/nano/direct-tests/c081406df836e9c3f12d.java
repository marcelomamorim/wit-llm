/*
 * @test
 * @summary Test of ZonedDateTime.ofInstant(Instant, ZoneId)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testOfInstantWithUTC();
        testOfInstantWithSpecificZone();
        testOfInstantWithNullInstant();
        testOfInstantWithNullZone();
    }

    static void testOfInstantWithUTC() {
        Instant instant = Instant.ofEpochSecond(1609459200L, 123456789); // 2021-01-01T00:00:00.123456789Z
        ZoneId zone = ZoneId.of("UTC");
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zone);
        assert zdt != null : "ZonedDateTime should not be null";
        assert zdt.getZone().equals(zone) : "Zone should be UTC";
        assert zdt.toEpochSecond() == instant.getEpochSecond() : "Epoch seconds should match";
        assert zdt.getNano() == instant.getNano() : "Nano part should match";
    }

    static void testOfInstantWithSpecificZone() {
        Instant instant = Instant.ofEpochSecond(1612137600L, 987654321); // 2021-02-01T00:00:00.987654321Z
        ZoneId zone = ZoneId.of("America/New_York");
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zone);
        assert zdt != null : "ZonedDateTime should not be null";
        assert zdt.getZone().equals(zone) : "Zone should be America/New_York";
        assert zdt.toEpochSecond() == instant.getEpochSecond() : "Epoch seconds should match";
        assert zdt.getNano() == instant.getNano() : "Nano part should match";
    }

    static void testOfInstantWithNullInstant() {
        try {
            ZonedDateTime.ofInstant(null, ZoneId.of("UTC"));
            assert false : "Expected NullPointerException for null instant";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testOfInstantWithNullZone() {
        Instant instant = Instant.ofEpochSecond(1609459200L);
        try {
            ZonedDateTime.ofInstant(instant, null);
            assert false : "Expected NullPointerException for null zone";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
