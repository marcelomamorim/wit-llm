/*
 * @test
 * @summary ZonedDateTime.withZoneSameLocal: null zone, same zone identity, zone change keeping local date-time, and DST gap adjustment
 * @run main ZonedDateTimeWitupTest
 */
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) throws Exception {
        testNullZoneThrowsNPE();
        testSameZoneReturnsSameInstance();
        testZoneChangeKeepsLocalDateTime();
        testDstGapAdjustsLocalDateTime();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullZoneThrowsNPE() {
        ZonedDateTime z = ZonedDateTime.of(2018, 1, 15, 10, 30, 0, 0, ZoneId.of("UTC"));
        boolean thrown = false;
        try {
            z.withZoneSameLocal(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null zone");
    }

    static void testSameZoneReturnsSameInstance() {
        ZoneId paris = ZoneId.of("Europe/Paris");
        ZonedDateTime z = ZonedDateTime.of(2018, 1, 15, 10, 30, 0, 0, paris);
        ZonedDateTime result = z.withZoneSameLocal(ZoneId.of("Europe/Paris"));
        check(result == z, "same zone should return this instance");
    }

    static void testZoneChangeKeepsLocalDateTime() {
        ZoneId paris = ZoneId.of("Europe/Paris");
        ZonedDateTime z = ZonedDateTime.of(2018, 1, 15, 10, 30, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime result = z.withZoneSameLocal(paris);
        check(result.toLocalDateTime().equals(LocalDateTime.of(2018, 1, 15, 10, 30)),
              "local date-time should be preserved, was " + result.toLocalDateTime());
        check(result.getZone().equals(paris), "zone should be Europe/Paris");
        check(result.getOffset().equals(ZoneOffset.ofHours(1)),
              "January offset in Paris should be +01:00, was " + result.getOffset());
    }

    static void testDstGapAdjustsLocalDateTime() {
        ZoneId paris = ZoneId.of("Europe/Paris");
        ZonedDateTime z = ZonedDateTime.of(LocalDate.of(2018, 3, 25), LocalTime.of(2, 30), ZoneId.of("UTC"));
        ZonedDateTime result = z.withZoneSameLocal(paris);
        check(result.getZone().equals(paris), "zone should be Europe/Paris");
        check(result.toLocalDateTime().equals(LocalDateTime.of(2018, 3, 25, 3, 30)),
              "gap local time should shift forward to 03:30, was " + result.toLocalDateTime());
        check(result.getOffset().equals(ZoneOffset.ofHours(2)),
              "offset after gap should be +02:00, was " + result.getOffset());
    }
}
