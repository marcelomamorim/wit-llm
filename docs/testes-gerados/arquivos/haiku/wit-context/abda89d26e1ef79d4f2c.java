/*
 * @test
 * @summary Verifies the instant returned by ZoneOffsetTransition.getInstant() for gap and overlap transitions
 * @run main ZoneOffsetTransitionWitupTest
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {

    public static void main(String[] args) {
        testGapInstantUsesOffsetBefore();
        testOverlapInstantUsesOffsetBefore();
        testNegativeOffsetsInstant();
        testRepeatedCallsAreStable();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testGapInstantUsesOffsetBefore() {
        LocalDateTime ldt = LocalDateTime.of(2020, 3, 29, 1, 0);
        ZoneOffset before = ZoneOffset.UTC;
        ZoneOffset after = ZoneOffset.ofHours(1);
        ZoneOffsetTransition t = ZoneOffsetTransition.of(ldt, before, after);
        Instant expected = Instant.ofEpochSecond(ldt.toEpochSecond(before));
        check(expected.equals(t.getInstant()),
              "gap: expected " + expected + " but was " + t.getInstant());
        Instant wrong = Instant.ofEpochSecond(ldt.toEpochSecond(after));
        check(!wrong.equals(t.getInstant()),
              "gap: instant must not be computed with offsetAfter");
    }

    static void testOverlapInstantUsesOffsetBefore() {
        LocalDateTime ldt = LocalDateTime.of(2020, 10, 25, 2, 0);
        ZoneOffset before = ZoneOffset.ofHours(2);
        ZoneOffset after = ZoneOffset.ofHours(1);
        ZoneOffsetTransition t = ZoneOffsetTransition.of(ldt, before, after);
        Instant expected = Instant.ofEpochSecond(ldt.toEpochSecond(before));
        check(expected.equals(t.getInstant()),
              "overlap: expected " + expected + " but was " + t.getInstant());
        Instant wrong = Instant.ofEpochSecond(ldt.toEpochSecond(after));
        check(!wrong.equals(t.getInstant()),
              "overlap: instant must not be computed with offsetAfter");
    }

    static void testNegativeOffsetsInstant() {
        LocalDateTime ldt = LocalDateTime.of(2021, 3, 14, 2, 0);
        ZoneOffset before = ZoneOffset.ofHours(-5);
        ZoneOffset after = ZoneOffset.ofHours(-4);
        ZoneOffsetTransition t = ZoneOffsetTransition.of(ldt, before, after);
        Instant expected = Instant.ofEpochSecond(ldt.toEpochSecond(before));
        check(expected.equals(t.getInstant()),
              "negative offsets: expected " + expected + " but was " + t.getInstant());
    }

    static void testRepeatedCallsAreStable() {
        LocalDateTime ldt = LocalDateTime.of(2020, 3, 29, 1, 0);
        ZoneOffsetTransition t = ZoneOffsetTransition.of(ldt, ZoneOffset.UTC, ZoneOffset.ofHours(1));
        Instant first = t.getInstant();
        Instant second = t.getInstant();
        check(first.equals(second), "repeated getInstant() calls differ");
    }
}
