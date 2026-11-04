/*
 * @test
 * @summary Verifies Clock.millis() for offset clocks, including ArithmeticException on overflow
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

public class ClockWitupTest {

    public static void main(String[] args) {
        Clock base = Clock.fixed(Instant.ofEpochMilli(1000L), ZoneOffset.UTC);

        check(Clock.offset(base, Duration.ofMillis(500)).millis() == 1500L, "positive offset");
        check(Clock.offset(base, Duration.ofMillis(-250)).millis() == 750L, "negative offset");
        check(Clock.offset(base, Duration.ZERO).millis() == 1000L, "zero offset");

        Clock max = Clock.fixed(Instant.ofEpochMilli(Long.MAX_VALUE), ZoneOffset.UTC);
        boolean overflow = false;
        try {
            Clock.offset(max, Duration.ofMillis(1)).millis();
        } catch (ArithmeticException e) {
            overflow = true;
        }
        check(overflow, "overflow must raise ArithmeticException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
