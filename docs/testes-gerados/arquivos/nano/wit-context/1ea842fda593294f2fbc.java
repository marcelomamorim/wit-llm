/*
 * @test
 * @summary Tests for java.time.zone.ZoneOffsetTransition
 * @run main ZoneOffsetTransitionWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {

    public static void main(String[] args) {
        testOfValidInputs();
        testOfNullTransition();
        testOfNullOffsetBefore();
        testOfNullOffsetAfter();
        testOfEqualOffsets();
        testOfNanoSecond();
    }

    static void testOfValidInputs() {
        LocalDateTime transition = LocalDateTime.of(2022, 3, 13, 2, 30);
        ZoneOffset offsetBefore = ZoneOffset.ofHours(-5);
        ZoneOffset offsetAfter = ZoneOffset.ofHours(-4);
        ZoneOffsetTransition transitionObj = ZoneOffsetTransition.of(transition, offsetBefore, offsetAfter);
        if (transitionObj == null) {
            throw new AssertionError("Expected non-null ZoneOffsetTransition");
        }
    }

    static void testOfNullTransition() {
        try {
            ZoneOffsetTransition.of(null, ZoneOffset.ofHours(0), ZoneOffset.ofHours(1));
            throw new AssertionError("Expected NullPointerException for null transition");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testOfNullOffsetBefore() {
        try {
            LocalDateTime transition = LocalDateTime.of(2022, 3, 13, 2, 30);
            ZoneOffsetTransition.of(transition, null, ZoneOffset.ofHours(1));
            throw new AssertionError("Expected NullPointerException for null offsetBefore");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testOfNullOffsetAfter() {
        try {
            LocalDateTime transition = LocalDateTime.of(2022, 3, 13, 2, 30);
            ZoneOffsetTransition.of(transition, ZoneOffset.ofHours(0), null);
            throw new AssertionError("Expected NullPointerException for null offsetAfter");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testOfEqualOffsets() {
        try {
            LocalDateTime transition = LocalDateTime.of(2022, 3, 13, 2, 30);
            ZoneOffset offset = ZoneOffset.ofHours(0);
            ZoneOffsetTransition.of(transition, offset, offset);
            throw new AssertionError("Expected IllegalArgumentException for equal offsets");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testOfNanoSecond() {
        try {
            LocalDateTime transition = LocalDateTime.of(2022, 3, 13, 2, 30, 1);
            ZoneOffset offsetBefore = ZoneOffset.ofHours(-5);
            ZoneOffset offsetAfter = ZoneOffset.ofHours(-4);
            LocalDateTime invalidTransition = transition.withNano(1);
            ZoneOffsetTransition.of(invalidTransition, offsetBefore, offsetAfter);
            throw new AssertionError("Expected IllegalArgumentException for non-zero nano");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
