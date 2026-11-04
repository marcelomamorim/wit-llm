/*
 * @test
 * @summary Test for ZoneOffsetTransition.getInstant() method
 * @run main ZoneOffsetTransitionWitupTest
 */

import java.time.ZoneOffset;
import java.time.LocalDateTime;
import java.time.Instant;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {

    public static void main(String[] args) {
        testGetInstantWithValidEpochSecond();
        testGetInstantWithNegativeEpochSecond();
        testGetInstantWithZeroEpochSecond();
    }

    static void testGetInstantWithValidEpochSecond() {
        long epochSecond = 1625097600L; // 2021-07-01T00:00:00Z
        ZoneOffset offset = ZoneOffset.UTC;
        ZoneOffsetTransition transition = ZoneOffsetTransition.of(LocalDateTime.of(2021, 7, 1, 0, 0), offset, offset);
        Instant expected = Instant.ofEpochSecond(epochSecond);
        Instant result = transition.getInstant();
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }

    static void testGetInstantWithNegativeEpochSecond() {
        long epochSecond = -123456789L; // negative epoch second
        ZoneOffset offset = ZoneOffset.UTC;
        ZoneOffsetTransition transition = ZoneOffsetTransition.of(LocalDateTime.of(1966, 2, 2, 0, 0), offset, offset);
        Instant expected = Instant.ofEpochSecond(epochSecond);
        Instant result = transition.getInstant();
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
}
    }

    static void testGetInstantWithZeroEpochSecond() {
        long epochSecond = 0L; // epoch second zero
        ZoneOffset offset = ZoneOffset.UTC;
        ZoneOffsetTransition transition = ZoneOffsetTransition.of(LocalDateTime.of(1970, 1, 1, 0, 0), offset, offset);
        Instant expected = Instant.ofEpochSecond(epochSecond);
        Instant result = transition.getInstant();
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }
}
