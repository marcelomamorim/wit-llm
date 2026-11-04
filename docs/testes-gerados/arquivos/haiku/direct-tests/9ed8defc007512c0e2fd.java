/*
 * @test
 * @summary Behavioral tests for Duration.plusNanos using only public observable results
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void checkSecondsNanos(Duration d, long seconds, int nanos, String label) {
        check(d.getSeconds() == seconds, label + " seconds expected " + seconds + " but was " + d.getSeconds());
        check(d.getNano() == nanos, label + " nanos expected " + nanos + " but was " + d.getNano());
    }

    public static void main(String[] args) {
        // Zero plus one nano
        check(Duration.ZERO.plusNanos(1).equals(Duration.ofNanos(1)), "ZERO.plusNanos(1) should equal ofNanos(1)");

        // Adding zero keeps the value
        check(Duration.ofSeconds(3).plusNanos(0).equals(Duration.ofSeconds(3)), "plusNanos(0) should keep value");

        // Adding nanos within the same second
        checkSecondsNanos(Duration.ofSeconds(1).plusNanos(500_000_000L), 1, 500_000_000, "1s + 0.5s");

        // Adding exactly one second worth of nanos carries into seconds
        checkSecondsNanos(Duration.ofSeconds(1).plusNanos(1_000_000_000L), 2, 0, "1s + 1e9 ns");

        // Negative nanos borrow from seconds
        checkSecondsNanos(Duration.ofSeconds(2).plusNanos(-1L), 1, 999_999_999, "2s - 1ns");

        // Cancelling nanos yields zero
        check(Duration.ofNanos(5).plusNanos(-5L).equals(Duration.ZERO), "5ns + (-5ns) should be ZERO");

        // Mixed seconds and nanos with negative adjustment
        checkSecondsNanos(Duration.ofSeconds(1, 1).plusNanos(-2L), 0, 999_999_999, "1s+1ns - 2ns");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
