/*
 * @test
 * @summary Test ZoneOffsetTransition.of method for null checks and argument validation
 * @run main ZoneOffsetTransitionWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {
    public static void main(String[] args) {
        testNullTransition();
        testNullOffsetBefore();
        testNullOffsetAfter();
        testEqualOffsets();
        testNonZeroNano();
        testValidCreation();
        System.out.println("All tests passed");
    }

    private static void testNullTransition() {
        try {
            ZoneOffsetTransition.of(null, ZoneOffset.UTC, ZoneOffset.ofHours(1));
            throw new AssertionError("Expected NullPointerException for null transition");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullOffsetBefore() {
        try {
            ZoneOffsetTransition.of(LocalDateTime.of(2018, 1, 1, 0, 0), null, ZoneOffset.ofHours(1));
            throw new AssertionError("Expected NullPointerException for null offsetBefore");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullOffsetAfter() {
        try {
            ZoneOffsetTransition.of(LocalDateTime.of(2018, 1, 1, 0, 0), ZoneOffset.UTC, null);
            throw new AssertionError("Expected NullPointerException for null offsetAfter");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testEqualOffsets() {
        try {
            ZoneOffsetTransition.of(LocalDateTime.of(2018, 1, 1, 0, 0), ZoneOffset.UTC, ZoneOffset.UTC);
            throw new AssertionError("Expected IllegalArgumentException for equal offsets");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNonZeroNano() {
        try {
            LocalDateTime dt = LocalDateTime.of(2018, 1, 1, 0, 0).withNano(1);
            ZoneOffsetTransition.of(dt, ZoneOffset.UTC, ZoneOffset.ofHours(1));
            throw new AssertionError("Expected IllegalArgumentException for non-zero nano");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testValidCreation() {
        LocalDateTime dt = LocalDateTime.of(2018, 1, 1, 0, 0);
        ZoneOffsetTransition zot = ZoneOffsetTransition.of(dt, ZoneOffset.UTC, ZoneOffset.ofHours(1));
        if (!zot.getDateTimeBefore().equals(dt)) {
            throw new AssertionError("Transition date-time mismatch");
        }
        if (!zot.getOffsetBefore().equals(ZoneOffset.UTC)) {
            throw new AssertionError("OffsetBefore mismatch");
        }
        if (!zot.getOffsetAfter().equals(ZoneOffset.ofHours(1))) {
            throw new AssertionError("OffsetAfter mismatch");
        }
    }
}
