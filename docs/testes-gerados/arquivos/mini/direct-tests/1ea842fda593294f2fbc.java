/*
 * @test
 * @summary Test ZoneOffsetTransition.of factory method for valid and invalid inputs
 * @run main ZoneOffsetTransitionWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {
    public static void main(String[] args) {
        testValidCreation();
        testNullTransition();
        testNullOffsetBefore();
        testNullOffsetAfter();
        testEqualOffsets();
        testNonZeroNano();
    }

    private static void testValidCreation() {
        LocalDateTime transition = LocalDateTime.of(2018, 10, 28, 2, 0, 0, 0);
        ZoneOffset offsetBefore = ZoneOffset.ofHours(2);
        ZoneOffset offsetAfter = ZoneOffset.ofHours(3);
        ZoneOffsetTransition zot = ZoneOffsetTransition.of(transition, offsetBefore, offsetAfter);
        if (!zot.getDateTimeBefore().equals(transition)) {
            throw new AssertionError("Transition LocalDateTime mismatch");
        }
        if (!zot.getOffsetBefore().equals(offsetBefore)) {
            throw new AssertionError("OffsetBefore mismatch");
        }
        if (!zot.getOffsetAfter().equals(offsetAfter)) {
            throw new AssertionError("OffsetAfter mismatch");
        }
    }

    private static void testNullTransition() {
        try {
            ZoneOffsetTransition.of(null, ZoneOffset.UTC, ZoneOffset.ofHours(1));
            throw new AssertionError("Expected NullPointerException for null transition");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullOffsetBefore() {
        try {
            ZoneOffsetTransition.of(LocalDateTime.now(), null, ZoneOffset.UTC);
            throw new AssertionError("Expected NullPointerException for null offsetBefore");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullOffsetAfter() {
        try {
            ZoneOffsetTransition.of(LocalDateTime.now(), ZoneOffset.UTC, null);
            throw new AssertionError("Expected NullPointerException for null offsetAfter");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testEqualOffsets() {
        LocalDateTime transition = LocalDateTime.of(2018, 10, 28, 2, 0, 0, 0);
        ZoneOffset offset = ZoneOffset.ofHours(2);
        try {
            ZoneOffsetTransition.of(transition, offset, offset);
            throw new AssertionError("Expected IllegalArgumentException for equal offsets");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testNonZeroNano() {
        LocalDateTime transition = LocalDateTime.of(2018, 10, 28, 2, 0, 0, 1);
        ZoneOffset offsetBefore = ZoneOffset.ofHours(2);
        ZoneOffset offsetAfter = ZoneOffset.ofHours(3);
        try {
            ZoneOffsetTransition.of(transition, offsetBefore, offsetAfter);
            throw new AssertionError("Expected IllegalArgumentException for non-zero nano");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
