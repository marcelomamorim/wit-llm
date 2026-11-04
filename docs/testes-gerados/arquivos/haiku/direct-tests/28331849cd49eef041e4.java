/*
 * @test
 * @summary ZonedDateTime.toOffsetDateTime keeps local date-time and offset, dropping the zone
 * @run main ZonedDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testWinterOffsetPreserved();
        testOverlapUsesEarlierOffset();
        testInstantIsPreserved();
        testUtcZone();
    }

    static void testWinterOffsetPreserved() {
        ZonedDateTime zdt = ZonedDateTime.of(2020, 1, 15, 10, 30, 45, 123, ZoneId.of("Europe/Paris"));
        OffsetDateTime odt = zdt.toOffsetDateTime();
        check(odt.getOffset().equals(ZoneOffset.ofHours(1)), "winter offset must be +01:00");
        check(odt.toLocalDateTime().equals(LocalDateTime.of(2020, 1, 15, 10, 30, 45, 123)),
              "local date-time must be preserved");
        check(odt.getOffset().equals(zdt.getOffset()), "offset must match source offset");
    }

    static void testOverlapUsesEarlierOffset() {
        ZonedDateTime zdt = ZonedDateTime.of(2018, 10, 28, 2, 30, 0, 0, ZoneId.of("Europe/Paris"));
        OffsetDateTime odt = zdt.toOffsetDateTime();
        check(odt.getOffset().equals(ZoneOffset.ofHours(2)), "overlap must keep earlier offset +02:00");
        check(odt.toLocalDateTime().equals(LocalDateTime.of(2018, 10, 28, 2, 30)),
              "local date-time must be 2018-10-28T02:30");
    }

    static void testInstantIsPreserved() {
        ZonedDateTime zdt = ZonedDateTime.of(2021, 6, 1, 12, 0, 0, 0, ZoneId.of("America/Sao_Paulo"));
        OffsetDateTime odt = zdt.toOffsetDateTime();
        check(odt.toInstant().equals(zdt.toInstant()), "instant must be the same");
        check(odt.toEpochSecond() == zdt.toEpochSecond(), "epoch seconds must be the same");
    }

    static void testUtcZone() {
        ZonedDateTime zdt = ZonedDateTime.of(2000, 2, 29, 23, 59, 59, 999999999, ZoneOffset.UTC);
        OffsetDateTime odt = zdt.toOffsetDateTime();
        check(odt.getOffset().equals(ZoneOffset.UTC), "offset must be UTC");
        check(odt.getNano() == 999999999, "nanoseconds must be preserved");
        check(odt.toLocalDate().getDayOfMonth() == 29, "leap day must be preserved");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
