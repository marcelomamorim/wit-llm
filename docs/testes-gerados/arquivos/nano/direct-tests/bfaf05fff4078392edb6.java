/*
 * @test
 * @summary Test LocalTime.now(Clock) method for correct behavior.
 * @run main LocalTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testNowWithSystemDefaultClock();
        testNowWithFixedClock();
        testNowWithOffsetClock();
    }

    static void testNowWithSystemDefaultClock() {
        LocalTime time = LocalTime.now(Clock.systemDefaultZone());
        if (time == null) {
            throw new AssertionError("LocalTime.now(Clock) returned null with system default clock");
        }
        System.out.println("testNowWithSystemDefaultClock passed: " + time);
    }

    static void testNowWithFixedClock() {
        Instant fixedInstant = Instant.parse("2023-01-01T12:34:56.789Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneId.of("UTC"));
        LocalTime time = LocalTime.now(fixedClock);
        LocalTime expectedTime = LocalTime.of(12, 34, 56, 789_000_000);
        if (!time.equals(expectedTime)) {
            throw new AssertionError("Expected " + expectedTime + " but got " + time);
        }
        System.out.println("testNowWithFixedClock passed: " + time);
    }

    static void testNowWithOffsetClock() {
        Instant fixedInstant = Instant.parse("2023-01-01T15:00:00Z");
        Clock offsetClock = Clock.offset(Clock.fixed(fixedInstant, ZoneId.of("UTC")), java.time.Duration.ofHours(3));
        LocalTime time = LocalTime.now(offsetClock);
        LocalTime expectedTime = LocalTime.of(18, 0);
        if (!time.equals(expectedTime)) {
            throw new AssertionError("Expected " + expectedTime + " but got " + time);
        }
        System.out.println("testNowWithOffsetClock passed: " + time);
    }
}
