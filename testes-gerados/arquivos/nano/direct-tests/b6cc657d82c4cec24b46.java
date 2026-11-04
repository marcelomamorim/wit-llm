/* @test    @summary Test OffsetDateTime.atZoneSameInstant method for different ZoneId inputs    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testAtZoneSameInstantWithValidZone();
        testAtZoneSameInstantWithUTCZone();
        testAtZoneSameInstantWithInvalidZone();
    }

    static void testAtZoneSameInstantWithValidZone() {
        OffsetDateTime odt = OffsetDateTime.of(2022, 3, 15, 10, 30, 0, 0, ZoneId.of("+02:00"));
        ZoneId zone = ZoneId.of("America/New_York");
        ZonedDateTime zdt = odt.atZoneSameInstant(zone);
        assert zdt != null : "ZonedDateTime should not be null";
        assert zdt.toOffsetDateTime().equals(odt) : "OffsetDateTime should be unchanged in instant";
        assert zdt.getZone().equals(zone) : "Zone should be as specified";
    }

    static void testAtZoneSameInstantWithUTCZone() {
        OffsetDateTime odt = OffsetDateTime.of(2022, 12, 25, 15, 0, 0, 0, ZoneId.of("+05:00"));
        ZoneId zone = ZoneId.of("UTC");
        ZonedDateTime zdt = odt.atZoneSameInstant(zone);
        assert zdt != null : "ZonedDateTime should not be null";
        assert zdt.toOffsetDateTime().equals(odt) : "OffsetDateTime should be unchanged in instant";
        assert zdt.getZone().equals(zone) : "Zone should be UTC";
    }

    static void testAtZoneSameInstantWithInvalidZone() {
        OffsetDateTime odt = OffsetDateTime.now();
        try {
            ZoneId invalidZone = ZoneId.of("Invalid/Zone");
            odt.atZoneSameInstant(invalidZone);
            assert false : "Expected DateTimeException for invalid zone";
        } catch (java.time.DateTimeException e) {
            // expected exception
        }
    }
}
