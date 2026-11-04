/*
 * @test
 * @summary Witup-derived tests for OffsetTime.toEpochSecond(LocalDate)
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testNullDateThrowsNPE();
        testEpochSecondPositiveOffset();
        testEpochSecondNegativeOffset();
        testEpochSecondNextDay();
    }

    static void testNullDateThrowsNPE() {
        OffsetTime ot = OffsetTime.of(LocalTime.of(10, 0), ZoneOffset.ofHours(2));
        boolean thrown = false;
        try {
            ot.toEpochSecond(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null date");
        }
    }

    static void testEpochSecondPositiveOffset() {
        OffsetTime ot = OffsetTime.of(LocalTime.of(10, 0), ZoneOffset.ofHours(2));
        long actual = ot.toEpochSecond(LocalDate.of(1970, 1, 1));
        long expected = 36000L - 7200L;
        if (actual != expected) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }

    static void testEpochSecondNegativeOffset() {
        OffsetTime ot = OffsetTime.of(LocalTime.of(10, 0), ZoneOffset.ofHours(-5));
        long actual = ot.toEpochSecond(LocalDate.of(1970, 1, 1));
        long expected = 36000L + 18000L;
        if (actual != expected) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }

    static void testEpochSecondNextDay() {
        OffsetTime ot = OffsetTime.of(LocalTime.of(10, 0), ZoneOffset.ofHours(2));
        long actual = ot.toEpochSecond(LocalDate.of(1970, 1, 2));
        long expected = 86400L + 36000L - 7200L;
        if (actual != expected) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
