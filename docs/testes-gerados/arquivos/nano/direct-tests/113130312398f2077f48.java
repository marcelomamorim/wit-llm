/*
 * @test
 * @summary Test of LocalTime.ofInstant method for correct behavior
 * @run main LocalTimeWitupTest
 */

import java.time.*;
import static java.time.temporal.ChronoField.*;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testOfInstantWithUTC();
        testOfInstantWithNonUTC();
        testOfInstantWithNullInstant();
        testOfInstantWithNullZone();
    }

    static void testOfInstantWithUTC() {
        Instant instant = Instant.ofEpochSecond(1609459200, 500000000); // 2021-01-01T00:00:00.5Z
        ZoneId zone = ZoneOffset.UTC;
        LocalTime time = LocalTime.ofInstant(instant, zone);
        long expectedSeconds = instant.getEpochSecond() + zone.getRules().getOffset(instant).getTotalSeconds();
        int expectedNano = instant.getNano();
        int expectedSecondsOfDay = Math.floorMod(expectedSeconds, 86400);
        long expectedNanoOfDay = expectedSecondsOfDay * 1_000_000_000L + expectedNano;
        LocalTime expectedTime = LocalTime.ofNanoOfDay(expectedNanoOfDay);
        assert time.equals(expectedTime) : "UTC test failed";
    }

    static void testOfInstantWithNonUTC() {
        Instant instant = Instant.ofEpochSecond(1609459200, 0); // 2021-01-01T00:00:00Z
        ZoneId zone = ZoneId.of("America/New_York");
        ZoneOffset offset = zone.getRules().getOffset(instant);
        LocalTime time = LocalTime.ofInstant(instant, zone);
        long localSeconds = instant.getEpochSecond() + offset.getTotalSeconds();
        int expectedSecondsOfDay = Math.floorMod(localSeconds, 86400);
        LocalTime expectedTime = LocalTime.ofSecondOfDay(expectedSecondsOfDay, instant.getNano());
        assert time.equals(expectedTime) : "Non-UTC test failed";
    }

    static void testOfInstantWithNullInstant() {
        try {
            LocalTime.ofInstant(null, ZoneOffset.UTC);
            assert false : "Expected NullPointerException for null instant";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testOfInstantWithNullZone() {
        Instant instant = Instant.ofEpochSecond(0);
        try {
            LocalTime.ofInstant(instant, null);
            assert false : "Expected NullPointerException for null zone";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
