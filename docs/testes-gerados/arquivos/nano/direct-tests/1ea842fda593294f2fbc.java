/*
 * @test
 * @summary Tests for ZoneOffsetTransition.of method
 * @run main ZoneOffsetTransitionWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {

    public static void main(String[] args) {
        testValidTransition();
        testNullTransition();
        testNullOffsetBefore();
        testNullOffsetAfter();
        testEqualOffsets();
        testNanoSecondNotZero();
    }

    static void testValidTransition() {
        LocalDateTime transition = LocalDateTime.of(2022, 3, 13, 2, 30);
        ZoneOffset offsetBefore = ZoneOffset.ofHours(-5);
        ZoneOffset offsetAfter = ZoneOffset.ofHours(-4);
        ZoneOffsetTransition zott = ZoneOffsetTransition.of(transition, offsetBefore, offsetAfter);
        assert zott != null : "Transition should not be null";
        assert zott.getTransition().equals(transition) : "Transition LocalDateTime mismatch";
        assert zott.getOffsetBefore().equals(offsetBefore) : "Offset before mismatch";
        assert zott.getOffsetAfter().equals(offsetAfter) : "Offset after mismatch";
    }

    static void testNullTransition() {
        try {
            ZoneOffsetTransition.of(null, ZoneOffset.ofHours(0), ZoneOffset.ofHours(1));
            assert false : "Expected NullPointerException for null transition";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullOffsetBefore() {
        try {
            ZoneOffsetTransition.of(LocalDateTime.of(2022, 3, 13, 2, 30), null, ZoneOffset.ofHours(1));
            assert false : "Expected NullPointerException for null offsetBefore";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullOffsetAfter() {
        try {
            ZoneOffsetTransition.of(LocalDateTime.of(2022, 3, 13, 2, 30), ZoneOffset.ofHours(0), null);
            assert false : "Expected NullPointerException for null offsetAfter";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testEqualOffsets() {
        try {
            ZoneOffsetTransition.of(LocalDateTime.of(2022, 3, 13, 2, 30), ZoneOffset.ofHours(1), ZoneOffset.ofHours(1));
            assert false : "Expected IllegalArgumentException for equal offsets";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNanoSecondNotZero() {
        try {
            LocalDateTime invalidNano = LocalDateTime.of(2022, 3, 13, 2, 30, 15, 1);
            ZoneOffsetTransition.of(invalidNano, ZoneOffset.ofHours(0), ZoneOffset.ofHours(1));
            assert false : "Expected IllegalArgumentException for non-zero nanos";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
