/*
 * @test
 * @summary Test ZonedDateTime.withZoneSameInstant(ZoneId)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testWithZoneSameInstantSameZone();
        testWithZoneSameInstantDifferentZone();
        testWithZoneSameInstantNullZone();
    }

    static void testWithZoneSameInstantSameZone() {
        ZoneId zone = ZoneId.of("America/New_York");
        ZonedDateTime zdt = ZonedDateTime.of(2023, 3, 15, 10, 0, 0, 0, zone);
        ZonedDateTime result = zdt.withZoneSameInstant(zone);
        assert result.equals(zdt) : "Expected result to be equal to original ZonedDateTime when same zone";
    }

    static void testWithZoneSameInstantDifferentZone() {
        ZoneId zone1 = ZoneId.of("America/New_York");
        ZoneId zone2 = ZoneId.of("Europe/London");
        ZonedDateTime zdt = ZonedDateTime.of(2023, 3, 15, 10, 0, 0, 0, zone1);
        ZonedDateTime result = zdt.withZoneSameInstant(zone2);
        long secondsDifference = result.toEpochSecond() - zdt.toEpochSecond();
        assert secondsDifference == 0 : "Epoch seconds should be equal for withZoneSameInstant";
        assert result.getZone().equals(zone2) : "Zone should be updated to Europe/London";
    }

    static void testWithZoneSameInstantNullZone() {
        try {
            ZonedDateTime zdt = ZonedDateTime.now();
            zdt.withZoneSameInstant(null);
            assert false : "Expected NullPointerException when zone is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
