/*
 * @test
 * @summary Tests for OffsetTime.ofInstant(Instant, ZoneId)
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.LocalTime;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testOfInstantWithNullInstant();
        testOfInstantWithNullZone();
        testOfInstantWithValidInputs();
    }

    private static void testOfInstantWithNullInstant() {
        try {
            OffsetTime.ofInstant(null, ZoneId.systemDefault());
            throw new AssertionError("Expected NullPointerException for null instant");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testOfInstantWithNullZone() {
        try {
            Instant now = Instant.now();
            OffsetTime.ofInstant(now, null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testOfInstantWithValidInputs() {
        Instant instant = Instant.ofEpochSecond(1609459200, 123456789); // 2021-01-01T00:00:00.123456789Z
        ZoneId zone = ZoneId.of("UTC");
        OffsetTime ot = OffsetTime.ofInstant(instant, zone);
        if (ot == null) {
            throw new AssertionError("OffsetTime should not be null for valid inputs");
        }
        // Additional observable checks
        long epochSecond = instant.getEpochSecond();
        int nano = instant.getNano();
        ZoneOffset offset = ot.getOffset();
        // Verify that the offset matches the zone's offset at the instant
        if (!offset.equals(zone.getRules().getOffset(instant))) {
            throw new AssertionError("Offset mismatch");
        }
        // Verify that the time corresponds to the local time of the instant
        long localSeconds = epochSecond + offset.getTotalSeconds();
        int secsOfDay = Math.floorMod(localSeconds, 86400);
        LocalTime expectedTime = LocalTime.ofNanoOfDay(secsOfDay * 1000000000L + nano);
        if (!ot.toLocalTime().equals(expectedTime)) {
            throw new AssertionError("LocalTime mismatch");
        }
    }
}
