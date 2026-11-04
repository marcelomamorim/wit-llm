/*
 * @test
 * @summary Witup-generated checks for ZoneOffsetTransition.of argument validation
 * @run main ZoneOffsetTransitionWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {

    public static void main(String[] args) {
        testNullTransitionThrowsNPE();
        testNullOffsetBeforeThrowsNPE();
        testNullOffsetAfterThrowsNPE();
        testEqualOffsetsThrowsIAE();
        testNonZeroNanoThrowsIAE();
        testValidGapCreatesTransition();
    }

    static void testNullTransitionThrowsNPE() {
        try {
            ZoneOffsetTransition.of(null, ZoneOffset.ofHours(3), ZoneOffset.ofHours(4));
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null transition");
    }

    static void testNullOffsetBeforeThrowsNPE() {
        LocalDateTime t = LocalDateTime.of(2012, 10, 28, 2, 0);
        try {
            ZoneOffsetTransition.of(t, null, ZoneOffset.ofHours(4));
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null offsetBefore");
    }

    static void testNullOffsetAfterThrowsNPE() {
        LocalDateTime t = LocalDateTime.of(2012, 10, 28, 2, 0);
        try {
            ZoneOffsetTransition.of(t, ZoneOffset.ofHours(3), null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null offsetAfter");
    }

    static void testEqualOffsetsThrowsIAE() {
        LocalDateTime t = LocalDateTime.of(2012, 10, 28, 2, 0);
        try {
            ZoneOffsetTransition.of(t, ZoneOffset.ofHours(3), ZoneOffset.ofHours(3));
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for equal offsets");
    }

    static void testNonZeroNanoThrowsIAE() {
        LocalDateTime t = LocalDateTime.of(2012, 10, 28, 2, 0, 0, 1);
        try {
            ZoneOffsetTransition.of(t, ZoneOffset.ofHours(3), ZoneOffset.ofHours(4));
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for non-zero nano-of-second");
    }

    static void testValidGapCreatesTransition() {
        LocalDateTime t = LocalDateTime.of(2012, 10, 28, 2, 0);
        ZoneOffsetTransition result =
                ZoneOffsetTransition.of(t, ZoneOffset.ofHours(3), ZoneOffset.ofHours(4));
        if (result == null) {
            throw new AssertionError("expected non-null transition for valid arguments");
        }
    }
}
