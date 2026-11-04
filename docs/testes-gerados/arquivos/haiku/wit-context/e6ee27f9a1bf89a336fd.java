/*
 * @test
 * @summary Witup-generated tests for ZoneOffsetTransition.getDateTimeAfter
 * @run main ZoneOffsetTransitionWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {

    public static void main(String[] args) {
        testGapAddsPositiveDuration();
        testOverlapSubtractsDuration();
        testSubHourOffsetDuration();
        testOverflowThrowsDateTimeException();
    }

    static void testGapAddsPositiveDuration() {
        ZoneOffsetTransition t = ZoneOffsetTransition.of(
                LocalDateTime.of(2020, 3, 29, 1, 0),
                ZoneOffset.ofHours(0),
                ZoneOffset.ofHours(1));
        LocalDateTime after = t.getDateTimeAfter();
        check(LocalDateTime.of(2020, 3, 29, 2, 0).equals(after),
                "gap: expected 02:00 but was " + after);
    }

    static void testOverlapSubtractsDuration() {
        ZoneOffsetTransition t = ZoneOffsetTransition.of(
                LocalDateTime.of(2020, 10, 25, 2, 0),
                ZoneOffset.ofHours(1),
                ZoneOffset.ofHours(0));
        LocalDateTime after = t.getDateTimeAfter();
        check(LocalDateTime.of(2020, 10, 25, 1, 0).equals(after),
                "overlap: expected 01:00 but was " + after);
    }

    static void testSubHourOffsetDuration() {
        ZoneOffsetTransition t = ZoneOffsetTransition.of(
                LocalDateTime.of(2020, 1, 1, 0, 0),
                ZoneOffset.UTC,
                ZoneOffset.ofHoursMinutes(5, 30));
        LocalDateTime after = t.getDateTimeAfter();
        check(LocalDateTime.of(2020, 1, 1, 5, 30).equals(after),
                "sub-hour: expected 05:30 but was " + after);
    }

    static void testOverflowThrowsDateTimeException() {
        ZoneOffsetTransition t = ZoneOffsetTransition.of(
                LocalDateTime.of(999999999, 12, 31, 23, 0),
                ZoneOffset.UTC,
                ZoneOffset.ofHours(2));
        try {
            t.getDateTimeAfter();
        } catch (DateTimeException expected) {
            return;
        }
        throw new RuntimeException("overflow: expected DateTimeException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
