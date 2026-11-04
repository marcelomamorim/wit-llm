/*
 * @test
 * @summary Verifies ZonedDateTime.of(LocalDateTime, ZoneId) for fixed offset, regular, gap and overlap local times
 * @run main ZonedDateTimeWitupTest
 */
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testFixedOffset();
        testRegularLocalTime();
        testGapShiftsForward();
        testOverlapUsesEarlierOffset();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFixedOffset() {
        LocalDateTime ldt = LocalDateTime.of(2020, 1, 1, 0, 0);
        ZoneId zone = ZoneOffset.ofHours(3);
        ZonedDateTime z = ZonedDateTime.of(ldt, zone);
        check(z.toLocalDateTime().equals(ldt), "fixed offset: local date-time changed");
        check(z.getOffset().equals(ZoneOffset.ofHours(3)), "fixed offset: wrong offset");
        check(z.toEpochSecond() == ldt.toEpochSecond(ZoneOffset.ofHours(3)), "fixed offset: wrong epoch second");
    }

    static void testRegularLocalTime() {
        LocalDateTime ldt = LocalDateTime.of(2020, 6, 15, 10, 30);
        ZoneId zone = ZoneId.of("Europe/Paris");
        ZonedDateTime z = ZonedDateTime.of(ldt, zone);
        check(z.toLocalDateTime().equals(ldt), "regular: local date-time changed");
        check(z.getZone().equals(zone), "regular: wrong zone");
        check(z.getOffset().equals(ZoneOffset.ofHours(2)), "regular: expected CEST offset +02:00");
    }

    static void testGapShiftsForward() {
        // 2018-03-11 02:30 does not exist in America/New_York (DST gap)
        LocalDateTime ldt = LocalDateTime.of(2018, 3, 11, 2, 30);
        ZonedDateTime z = ZonedDateTime.of(ldt, ZoneId.of("America/New_York"));
        check(z.toLocalDateTime().equals(LocalDateTime.of(2018, 3, 11, 3, 30)),
              "gap: expected local time shifted forward to 03:30");
        check(z.getOffset().equals(ZoneOffset.ofHours(-4)), "gap: expected EDT offset -04:00");
    }

    static void testOverlapUsesEarlierOffset() {
        // 2018-11-04 01:30 occurs twice in America/New_York; earlier offset (EDT) is chosen
        LocalDateTime ldt = LocalDateTime.of(2018, 11, 4, 1, 30);
        ZonedDateTime z = ZonedDateTime.of(ldt, ZoneId.of("America/New_York"));
        check(z.toLocalDateTime().equals(ldt), "overlap: local date-time changed");
        check(z.getOffset().equals(ZoneOffset.ofHours(-4)), "overlap: expected earlier offset -04:00");
    }
}
