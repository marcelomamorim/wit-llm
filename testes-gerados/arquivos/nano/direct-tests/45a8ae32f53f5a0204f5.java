/*
 * @test
 * @summary Test LocalDateTime.now(Clock) method for correct behavior.
 * @run main LocalDateTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testNowWithSystemDefaultZone();
        testNowWithFixedClock();
        testNowWithCustomZone();
    }

    static void testNowWithSystemDefaultZone() {
        try {
            LocalDateTime result = LocalDateTime.now(Clock.systemDefaultZone());
            if (result == null) {
                throw new AssertionError("Result should not be null");
            }
            System.out.println("testNowWithSystemDefaultZone passed: " + result);
        } catch (Exception e) {
            throw new AssertionError("Exception in testNowWithSystemDefaultZone", e);
        }
    }

    static void testNowWithFixedClock() {
        try {
            Instant fixedInstant = Instant.ofEpochSecond(1609459200L, 123456789); // 2021-01-01T00:00:00.123456789Z
            Clock fixedClock = Clock.fixed(fixedInstant, ZoneOffset.UTC);
            LocalDateTime result = LocalDateTime.now(fixedClock);
            LocalDateTime expected = LocalDateTime.ofEpochSecond(1609459200L, 123456789, ZoneOffset.UTC.getRules().getOffset(fixedInstant));
            if (!result.equals(expected)) {
                throw new AssertionError("Expected: " + expected + ", but got: " + result);
            }
            System.out.println("testNowWithFixedClock passed: " + result);
        } catch (Exception e) {
            throw new AssertionError("Exception in testNowWithFixedClock", e);
        }
    }

    static void testNowWithCustomZone() {
        try {
            ZoneOffset offset = ZoneOffset.ofHoursMinutes(5, 30); // UTC+5:30
            Instant instant = Instant.ofEpochSecond(1609459200L, 987654321); // 2021-01-01T00:00:00.987654321Z
            Clock clock = Clock.fixed(instant, offset);
            LocalDateTime result = LocalDateTime.now(clock);
            LocalDateTime expected = LocalDateTime.ofEpochSecond(1609459200L, 987654321, offset);
            if (!result.equals(expected)) {
                throw new AssertionError("Expected: " + expected + ", but got: " + result);
            }
            System.out.println("testNowWithCustomZone passed: " + result);
        } catch (Exception e) {
            throw new AssertionError("Exception in testNowWithCustomZone", e);
        }
    }
}
