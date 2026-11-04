/*
 * @test
 * @summary Witup-generated checks for OffsetDateTime.atZoneSameInstant
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        OffsetDateTime odt = OffsetDateTime.of(2020, 1, 1, 12, 0, 0, 0, ZoneOffset.ofHours(2));

        ZoneId utc = ZoneId.of("UTC");
        ZonedDateTime inUtc = odt.atZoneSameInstant(utc);
        check(inUtc.toInstant().equals(odt.toInstant()), "instant must be preserved for UTC");
        check(inUtc.getZone().equals(utc), "zone must be the requested zone");
        check(inUtc.toLocalDateTime().equals(LocalDateTime.of(2020, 1, 1, 10, 0)), "local date-time in UTC");
        check(inUtc.getOffset().equals(ZoneOffset.UTC), "offset in UTC");

        ZoneId paris = ZoneId.of("Europe/Paris");
        ZonedDateTime inParis = odt.atZoneSameInstant(paris);
        check(inParis.toInstant().equals(odt.toInstant()), "instant must be preserved for Paris");
        check(inParis.getZone().equals(paris), "zone must be Europe/Paris");
        check(inParis.toLocalTime().equals(LocalTime.of(11, 0)), "local time in Paris in January is +01:00");

        boolean thrown = false;
        try {
            odt.atZoneSameInstant(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null zone must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
