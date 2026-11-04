/*
 * @test
 * @summary Test ChronoZonedDateTimeImpl.withZoneSameInstant(ZoneId zone) method for different zone inputs.
 * @run main ChronoZonedDateTimeImplWitupTest
 */

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.ChronoZonedDateTimeImpl;

public class ChronoZonedDateTimeImplWitupTest {

    public static void main(String[] args) {
        testWithZoneSameInstantSameZone();
        testWithZoneSameInstantDifferentZone();
        testWithZoneSameInstantNullZone();
    }

    static void testWithZoneSameInstantSameZone() {
        ZoneId zone = ZoneId.of("America/New_York");
        ZonedDateTime zdt = ZonedDateTime.of(2023, 3, 15, 10, 0, 0, 0, zone);
        ChronoZonedDateTime<?> czt = (ChronoZonedDateTime<?>) zdt;
        ChronoZonedDateTimeImpl<?> czi = (ChronoZonedDateTimeImpl<?>) czt;
        ChronoZonedDateTime<?> result = czi.withZoneSameInstant(zone);
        assert result.equals(czi) : "Expected same object when zone is unchanged";
    }

    static void testWithZoneSameInstantDifferentZone() {
        ZoneId originalZone = ZoneId.of("Europe/Paris");
        ZoneId newZone = ZoneId.of("Asia/Tokyo");
        ZonedDateTime zdt = ZonedDateTime.of(2023, 3, 15, 12, 0, 0, 0, originalZone);
        ChronoZonedDateTime<?> czt = (ChronoZonedDateTime<?>) zdt;
        ChronoZonedDateTimeImpl<?> czi = (ChronoZonedDateTimeImpl<?>) czt;
        ChronoZonedDateTime<?> result = czi.withZoneSameInstant(newZone);
        assert !result.equals(czi) : "Expected different object when zone changes";
        ZonedDateTime resultZdt = (ZonedDateTime) result;
        assert resultZdt.getZone().equals(newZone) : "Zone should be updated to new zone";
        assert resultZdt.toInstant().equals(zdt.toInstant()) : "Instant should be unchanged";
    }

    static void testWithZoneSameInstantNullZone() {
        try {
            ZonedDateTime zdt = ZonedDateTime.of(2023, 3, 15, 10, 0, 0, 0, ZoneId.of("UTC"));
            ChronoZonedDateTime<?> czt = (ChronoZonedDateTime<?>) zdt;
            ChronoZonedDateTimeImpl<?> czi = (ChronoZonedDateTimeImpl<?>) czt;
            czi.withZoneSameInstant(null);
            assert false : "Expected NullPointerException when zone is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
