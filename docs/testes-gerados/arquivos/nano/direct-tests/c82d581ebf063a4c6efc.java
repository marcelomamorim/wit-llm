/*
 * @test
 * @summary Test withZoneSameLocal method of ZonedDateTime for different zones
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testWithZoneSameLocalSameZone();
        testWithZoneSameLocalDifferentZone();
        testWithZoneSameLocalNullZone();
    }

    static void testWithZoneSameLocalSameZone() {
        ZonedDateTime zdt = ZonedDateTime.of(2023, 3, 15, 10, 30, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime result = zdt.withZoneSameLocal(ZoneId.of("America/New_York"));
        assert result.equals(zdt) : "Expected same ZonedDateTime when zone is unchanged";
    }

    static void testWithZoneSameLocalDifferentZone() {
        ZonedDateTime zdt = ZonedDateTime.of(2023, 3, 15, 10, 30, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime result = zdt.withZoneSameLocal(ZoneId.of("Europe/Paris"));
        assert result.getZone().equals(ZoneId.of("Europe/Paris")) : "Zone should be updated to Europe/Paris";
        assert result.toLocalDateTime().equals(zdt.toLocalDateTime()) : "Local date-time should remain the same";
    }

    static void testWithZoneSameLocalNullZone() {
        try {
            ZonedDateTime zdt = ZonedDateTime.of(2023, 3, 15, 10, 30, 0, 0, ZoneId.of("America/New_York"));
            zdt.withZoneSameLocal(null);
            assert false : "Expected NullPointerException for null zone";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
