/*
 * @test
 * @summary Witup-style checks for ZonedDateTime.of(LocalDateTime, ZoneId)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testBasicUtc();
        testNullLocalDateTime();
        testNullZone();
        testGapShiftsForward();
        testOverlapUsesEarlierOffset();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBasicUtc() {
        ZoneId utc = ZoneId.of("UTC");
        ZonedDateTime z = ZonedDateTime.of(LocalDateTime.of(2020, 1, 15, 10, 30), utc);
        check(z.getYear() == 2020, "year mismatch");
        check(z.toLocalDateTime().equals(LocalDateTime.of(2020, 1, 15, 10, 30)), "local date-time mismatch");
        check(z.getZone().equals(utc), "zone mismatch");
        check(z.getOffset().equals(ZoneOffset.UTC), "offset mismatch");
    }

    static void testNullLocalDateTime() {
        try {
            ZonedDateTime.of((LocalDateTime) null, ZoneId.of("UTC"));
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null localDateTime");
    }

    static void testNullZone() {
        try {
            ZonedDateTime.of(LocalDateTime.of(2020, 1, 15, 10, 30), (ZoneId) null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null zone");
    }

    static void testGapShiftsForward() {
        ZoneId ny = ZoneId.of("America/New_York");
        ZonedDateTime z = ZonedDateTime.of(LocalDateTime.of(2018, 3, 11, 2, 30), ny);
        check(z.toLocalDateTime().equals(LocalDateTime.of(2018, 3, 11, 3, 30)),
              "gap local time should shift forward to 03:30");
        check(z.getOffset().equals(ZoneOffset.of("-04:00")), "gap offset should be -04:00");
    }

    static void testOverlapUsesEarlierOffset() {
        ZoneId ny = ZoneId.of("America/New_York");
        ZonedDateTime z = ZonedDateTime.of(LocalDateTime.of(2018, 11, 4, 1, 30), ny);
        check(z.toLocalDateTime().equals(LocalDateTime.of(2018, 11, 4, 1, 30)),
              "overlap local time should be preserved");
        check(z.getOffset().equals(ZoneOffset.of("-04:00")),
              "overlap should choose earlier offset -04:00");
    }
}
