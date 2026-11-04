/*
 * @test
 * @summary Test deterministic behavior of ZoneOffsetTransition.getDateTimeAfter()
 * @run main ZoneOffsetTransitionWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {
    public static void main(String[] args) {
        testGetDateTimeAfterBasic();
    }

    private static void testGetDateTimeAfterBasic() {
        // Create a transition at 2018-03-25T02:00 with offset before +01:00 and after +02:00
        LocalDateTime transition = LocalDateTime.of(2018, 3, 25, 2, 0);
        ZoneOffset offsetBefore = ZoneOffset.ofHours(1);
        ZoneOffset offsetAfter = ZoneOffset.ofHours(2);

        ZoneOffsetTransition zot = ZoneOffsetTransition.of(transition, offsetBefore, offsetAfter);

        // The duration should be offsetAfter - offsetBefore = 3600 seconds
        // getDateTimeAfter() should return transition plus duration seconds
        LocalDateTime expected = transition.plusSeconds(zot.getDurationSeconds());
        LocalDateTime actual = zot.getDateTimeAfter();

        if (!expected.equals(actual)) {
            throw new AssertionError("getDateTimeAfter() returned " + actual + ", expected " + expected);
        }

        // Additional check: durationSeconds should be 3600
        if (zot.getDurationSeconds() != 3600) {
            throw new AssertionError("Duration seconds expected 3600 but was " + zot.getDurationSeconds());
        }
    }
}
