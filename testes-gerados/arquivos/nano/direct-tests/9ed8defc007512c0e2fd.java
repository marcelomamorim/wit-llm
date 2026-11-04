/*
 * @test    @summary Test Duration.plusNanos method for correct addition of nanoseconds.
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testPlusNanosPositive();
        testPlusNanosZero();
        testPlusNanosNegative();
    }

    static void testPlusNanosPositive() {
        Duration duration = Duration.ofSeconds(1);
        Duration result = duration.plusNanos(500);
        long expectedSeconds = 1;
        long expectedNanos = 500;
        if (result.getSeconds() != expectedSeconds || result.getNano() != expectedNanos) {
            throw new AssertionError("Failed: plusNanos with positive nanos. Expected seconds: " + expectedSeconds + ", nanos: " + expectedNanos + ", but got seconds: " + result.getSeconds() + ", nanos: " + result.getNano());
        }
    }

    static void testPlusNanosZero() {
        Duration duration = Duration.ofSeconds(2, 300);
        Duration result = duration.plusNanos(0);
        if (!result.equals(duration)) {
            throw new AssertionError("Failed: plusNanos with zero nanos. Expected duration: " + duration + ", but got: " + result);
        }
    }

    static void testPlusNanosNegative() {
        Duration duration = Duration.ofSeconds(2, 300);
        Duration result = duration.plusNanos(-300);
        long expectedSeconds = 2;
        long expectedNanos = 0;
        if (result.getSeconds() != expectedSeconds || result.getNano() != expectedNanos) {
            throw new AssertionError("Failed: plusNanos with negative nanos. Expected seconds: " + expectedSeconds + ", nanos: " + expectedNanos + ", but got seconds: " + result.getSeconds() + ", nanos: " + result.getNano());
        }
    }
}
