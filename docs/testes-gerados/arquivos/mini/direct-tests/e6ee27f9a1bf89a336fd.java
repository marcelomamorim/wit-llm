/*
 * @test
 * @summary Test ZoneOffsetTransition.getDateTimeAfter() returns correct LocalDateTime
 * @run main ZoneOffsetTransitionWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {
    public static void main(String[] args) {
        // Create a transition at 2018-03-25T02:00 from +01:00 to +02:00 (typical DST gap)
        LocalDateTime transition = LocalDateTime.of(2018, 3, 25, 2, 0);
        ZoneOffset offsetBefore = ZoneOffset.ofHours(1);
        ZoneOffset offsetAfter = ZoneOffset.ofHours(2);

        ZoneOffsetTransition zot = ZoneOffsetTransition.of(transition, offsetBefore, offsetAfter);

        // Expected dateTimeAfter is transition plus duration in seconds (offsetAfter - offsetBefore)
        LocalDateTime expected = transition.plusSeconds(zot.getDurationSeconds());

        LocalDateTime actual = zot.getDateTimeAfter();

        if (!expected.equals(actual)) {
            throw new AssertionError("getDateTimeAfter() returned " + actual + ", but expected " + expected);
        }

        // Additional check: duration seconds should be 3600 (1 hour)
        if (zot.getDurationSeconds() != 3600) {
            throw new AssertionError("Duration seconds expected to be 3600 but was " + zot.getDurationSeconds());
        }

        System.out.println("Test passed.");
    }
}
