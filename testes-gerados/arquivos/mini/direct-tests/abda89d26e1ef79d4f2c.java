/*
 * @test
 * @summary Test ZoneOffsetTransition.getInstant() returns correct Instant
 * @run main ZoneOffsetTransitionWitupTest
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {
    public static void main(String[] args) {
        // Create a transition at 2018-03-25T02:00 with offset before +01:00 and offset after +02:00
        LocalDateTime transition = LocalDateTime.of(2018, 3, 25, 2, 0);
        ZoneOffset offsetBefore = ZoneOffset.ofHours(1);
        ZoneOffset offsetAfter = ZoneOffset.ofHours(2);

        ZoneOffsetTransition zot = ZoneOffsetTransition.of(transition, offsetBefore, offsetAfter);

        Instant instant = zot.getInstant();

        // The instant should correspond to the epochSecond stored internally
        // We verify that the instant equals the expected instant computed manually
        long expectedEpochSecond = transition.toEpochSecond(offsetBefore);
        Instant expectedInstant = Instant.ofEpochSecond(expectedEpochSecond);

        if (!instant.equals(expectedInstant)) {
            throw new AssertionError("getInstant() returned " + instant + ", but expected " + expectedInstant);
        }

        // Additional check: the instant's epochSecond matches the transition's epochSecond
        if (instant.getEpochSecond() != expectedEpochSecond) {
            throw new AssertionError("Instant epochSecond mismatch: " + instant.getEpochSecond() + " vs " + expectedEpochSecond);
        }

        System.out.println("Test passed.");
    }
}
