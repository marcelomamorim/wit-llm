/*
 * @test
 * @summary Witup-generated tests for ZonedDateTime.withZoneSameLocal
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testNullZoneThrowsNullPointerException();
        testSameZoneReturnsSameInstance();
        testDifferentZoneKeepsLocalDateTime();
    }

    static void testNullZoneThrowsNullPointerException() {
        ZonedDateTime zdt = ZonedDateTime.of(2020, 1, 15, 10, 30, 0, 0, ZoneId.of("UTC"));
        try {
            zdt.withZoneSameLocal(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null zone");
    }

    static void testSameZoneReturnsSameInstance() {
        ZoneId utc = ZoneId.of("UTC");
        ZonedDateTime zdt = ZonedDateTime.of(2020, 1, 15, 10, 30, 0, 0, utc);
        ZonedDateTime result = zdt.withZoneSameLocal(utc);
        if (result != zdt) {
            throw new AssertionError("expected same instance when zone is unchanged");
        }
    }

    static void testDifferentZoneKeepsLocalDateTime() {
        ZoneId utc = ZoneId.of("UTC");
        ZoneId paris = ZoneId.of("Europe/Paris");
        ZonedDateTime zdt = ZonedDateTime.of(2020, 1, 15, 10, 30, 0, 0, utc);
        ZonedDateTime result = zdt.withZoneSameLocal(paris);
        if (!result.getZone().equals(paris)) {
            throw new AssertionError("expected zone to be Europe/Paris");
        }
        if (!result.toLocalDateTime().equals(zdt.toLocalDateTime())) {
            throw new AssertionError("expected local date-time to be preserved");
        }
    }
}
