/*
 * @test
 * @summary Deterministic tests for java.time.zone.ZoneOffsetTransition.getInstant()
 * @run main ZoneOffsetTransitionWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.Instant;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {
    public static void main(String[] args) {
        testGetInstantNormal();
        testGetInstantEpochSecondBounds();
    }

    private static void testGetInstantNormal() {
        LocalDateTime ldt = LocalDateTime.of(2018, 3, 25, 2, 0);
        ZoneOffset offsetBefore = ZoneOffset.ofHours(1);
        ZoneOffset offsetAfter = ZoneOffset.ofHours(2);
        ZoneOffsetTransition transition = ZoneOffsetTransition.of(ldt, offsetBefore, offsetAfter);

        Instant instant = transition.getInstant();
        long epochSecond = instant.getEpochSecond();

        // The instant should correspond to the transition local date-time minus offsetBefore
        long expectedEpochSecond = ldt.toEpochSecond(offsetBefore);

        if (epochSecond != expectedEpochSecond) {
            throw new AssertionError("Expected epochSecond " + expectedEpochSecond + " but got " + epochSecond);
        }
    }

    private static void testGetInstantEpochSecondBounds() {
        // The method getInstant() calls Instant.ofEpochSecond(epochSecond)
        // which throws DateTimeException if epochSecond is out of valid range.
        // We cannot create ZoneOffsetTransition with invalid epochSecond directly,
        // so this test verifies that getInstant() returns a valid Instant for a valid transition.
        // This test is limited by the public API and does not attempt to create invalid states.

        LocalDateTime ldt = LocalDateTime.of(1970, 1, 1, 0, 0);
        ZoneOffset offsetBefore = ZoneOffset.UTC;
        ZoneOffset offsetAfter = ZoneOffset.UTC;
        ZoneOffsetTransition transition = ZoneOffsetTransition.of(ldt, offsetBefore, offsetAfter);

        Instant instant = transition.getInstant();
        if (instant == null) {
            throw new AssertionError("getInstant() returned null");
        }
    }
}
