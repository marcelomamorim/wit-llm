/*
 * @test
 * @summary Verifies Clock.millis() for offset clocks: normal sums and ArithmeticException on long overflow via Math.addExact.
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

public class ClockWitupTest {

    public static void main(String[] args) {
        testNormalOffsetSum();
        testNegativeOffsetSum();
        testZeroOffsetAtMaxBoundary();
        testPositiveOverflowThrows();
        testNegativeOverflowThrows();
    }

    static void testNormalOffsetSum() {
        Clock base = Clock.fixed(Instant.ofEpochMilli(1000L), ZoneOffset.UTC);
        Clock offset = Clock.offset(base, Duration.ofMillis(500L));
        long actual = offset.millis();
        if (actual != 1500L) {
            throw new RuntimeException("Expected 1500 but was " + actual);
        }
    }

    static void testNegativeOffsetSum() {
        Clock base = Clock.fixed(Instant.ofEpochMilli(1000L), ZoneOffset.UTC);
        Clock offset = Clock.offset(base, Duration.ofMillis(-250L));
        long actual = offset.millis();
        if (actual != 750L) {
            throw new RuntimeException("Expected 750 but was " + actual);
        }
    }

    static void testZeroOffsetAtMaxBoundary() {
        Clock base = Clock.fixed(Instant.ofEpochMilli(Long.MAX_VALUE), ZoneOffset.UTC);
        Clock offset = Clock.offset(base, Duration.ZERO);
        long actual = offset.millis();
        if (actual != Long.MAX_VALUE) {
            throw new RuntimeException("Expected Long.MAX_VALUE but was " + actual);
        }
    }

    static void testPositiveOverflowThrows() {
        Clock base = Clock.fixed(Instant.ofEpochMilli(Long.MAX_VALUE), ZoneOffset.UTC);
        Clock offset = Clock.offset(base, Duration.ofMillis(1L));
        try {
            offset.millis();
            throw new RuntimeException("Expected ArithmeticException on positive overflow");
        } catch (ArithmeticException expected) {
            // expected
        }
    }

    static void testNegativeOverflowThrows() {
        Clock base = Clock.fixed(Instant.ofEpochMilli(Long.MIN_VALUE), ZoneOffset.UTC);
        Clock offset = Clock.offset(base, Duration.ofMillis(-1L));
        try {
            offset.millis();
            throw new RuntimeException("Expected ArithmeticException on negative overflow");
        } catch (ArithmeticException expected) {
            // expected
        }
    }
}
