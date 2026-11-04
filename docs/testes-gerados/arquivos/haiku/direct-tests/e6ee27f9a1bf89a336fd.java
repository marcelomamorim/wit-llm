/*
 * @test
 * @summary Verifies ZoneOffsetTransition.getDateTimeAfter() returns the transition local date-time shifted by the offset difference
 * @run main ZoneOffsetTransitionWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {

    public static void main(String[] args) {
        testSpringGap();
        testAutumnOverlap();
        testFractionalOffsets();
        testNegativeOffsets();
        testRepeatedCallsAreStable();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSpringGap() {
        ZoneOffsetTransition t = ZoneOffsetTransition.of(
                LocalDateTime.of(2024, 3, 31, 1, 0),
                ZoneOffset.ofHours(1),
                ZoneOffset.ofHours(2));
        check(t.getDateTimeAfter().equals(LocalDateTime.of(2024, 3, 31, 2, 0)),
                "spring gap: expected 02:00 but was " + t.getDateTimeAfter());
    }

    static void testAutumnOverlap() {
        ZoneOffsetTransition t = ZoneOffsetTransition.of(
                LocalDateTime.of(2024, 10, 27, 3, 0),
                ZoneOffset.ofHours(2),
                ZoneOffset.ofHours(1));
        check(t.getDateTimeAfter().equals(LocalDateTime.of(2024, 10, 27, 2, 0)),
                "autumn overlap: expected 02:00 but was " + t.getDateTimeAfter());
    }

    static void testFractionalOffsets() {
        ZoneOffsetTransition t = ZoneOffsetTransition.of(
                LocalDateTime.of(2020, 1, 1, 0, 0),
                ZoneOffset.UTC,
                ZoneOffset.ofHoursMinutesSeconds(1, 30, 15));
        check(t.getDateTimeAfter().equals(LocalDateTime.of(2020, 1, 1, 1, 30, 15)),
                "fractional offsets: expected 01:30:15 but was " + t.getDateTimeAfter());
    }

    static void testNegativeOffsets() {
        ZoneOffsetTransition t = ZoneOffsetTransition.of(
                LocalDateTime.of(2021, 3, 14, 2, 0),
                ZoneOffset.ofHours(-5),
                ZoneOffset.ofHours(-4));
        check(t.getDateTimeAfter().equals(LocalDateTime.of(2021, 3, 14, 3, 0)),
                "negative offsets: expected 03:00 but was " + t.getDateTimeAfter());
    }

    static void testRepeatedCallsAreStable() {
        ZoneOffsetTransition t = ZoneOffsetTransition.of(
                LocalDateTime.of(2024, 3, 31, 1, 0),
                ZoneOffset.ofHours(1),
                ZoneOffset.ofHours(2));
        LocalDateTime first = t.getDateTimeAfter();
        LocalDateTime second = t.getDateTimeAfter();
        check(first.equals(second), "repeated calls returned different values");
    }
}
