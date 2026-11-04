/*
 * @test
 * @summary Witup-generated checks for Clock.fixed(Instant, ZoneId) null guards and observable fixed behavior
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class ClockWitupTest {

    public static void main(String[] args) {
        Instant instant = Instant.ofEpochSecond(1000000000L, 123456789L);
        ZoneId zone = ZoneId.of("UTC");

        Clock clock = Clock.fixed(instant, zone);
        check(instant.equals(clock.instant()), "instant mismatch");
        check(zone.equals(clock.getZone()), "zone mismatch");
        check(clock.millis() == instant.toEpochMilli(), "millis mismatch");
        check(instant.equals(clock.instant()), "instant not fixed on second call");

        Clock offsetClock = Clock.fixed(instant, ZoneOffset.ofHours(2));
        check(ZoneOffset.ofHours(2).equals(offsetClock.getZone()), "offset zone mismatch");
        check(instant.equals(offsetClock.instant()), "offset clock instant mismatch");

        expectNpe(() -> Clock.fixed(null, zone), "null fixedInstant");
        expectNpe(() -> Clock.fixed(instant, null), "null zone");
        expectNpe(() -> Clock.fixed(null, null), "both null");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void expectNpe(Runnable r, String message) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException: " + message);
    }
}
