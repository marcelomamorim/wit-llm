/*
 * @test
 * @summary Tests observable behavior of ZonedDateTime.ofInstant
 * @run main ZonedDateTimeWitupTest
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testEpochZeroWithOffset();
        testNanosArePreserved();
        testNegativeEpochSecond();
        testZoneIsPreserved();
        testNullInstantThrowsNpe();
        testNullZoneThrowsNpe();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNpe(Runnable action, String message) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(message);
    }

    static void testEpochZeroWithOffset() {
        ZonedDateTime z = ZonedDateTime.ofInstant(Instant.EPOCH, ZoneOffset.ofHours(2));
        check(z.toLocalDateTime().equals(LocalDateTime.of(1970, 1, 1, 2, 0)),
              "local date-time mismatch for epoch at +02:00");
        check(z.toInstant().equals(Instant.EPOCH),
              "round-trip instant mismatch for epoch");
    }

    static void testNanosArePreserved() {
        Instant instant = Instant.ofEpochSecond(1L, 123456789L);
        ZonedDateTime z = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
        check(z.getNano() == 123456789, "nano-of-second not preserved");
        check(z.toEpochSecond() == 1L, "epoch second not preserved");
        check(z.toInstant().equals(instant), "round-trip instant mismatch with nanos");
    }

    static void testNegativeEpochSecond() {
        Instant instant = Instant.ofEpochSecond(-1L, 999999999L);
        ZonedDateTime z = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
        check(z.toLocalDateTime().equals(LocalDateTime.of(1969, 12, 31, 23, 59, 59, 999999999)),
              "negative epoch local date-time mismatch");
        check(z.toInstant().equals(instant), "round-trip instant mismatch for negative epoch");
    }

    static void testZoneIsPreserved() {
        ZoneId paris = ZoneId.of("Europe/Paris");
        ZonedDateTime z = ZonedDateTime.ofInstant(Instant.EPOCH, paris);
        check(z.getZone().equals(paris), "zone not preserved");
        check(z.toInstant().equals(Instant.EPOCH), "instant changed when zone applied");
    }

    static void testNullInstantThrowsNpe() {
        expectNpe(() -> ZonedDateTime.ofInstant(null, ZoneOffset.UTC),
                  "null instant should throw NullPointerException");
    }

    static void testNullZoneThrowsNpe() {
        expectNpe(() -> ZonedDateTime.ofInstant(Instant.EPOCH, null),
                  "null zone should throw NullPointerException");
    }
}
