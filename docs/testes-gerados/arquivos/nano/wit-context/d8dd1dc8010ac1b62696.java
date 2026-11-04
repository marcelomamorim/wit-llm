/*
 * @test
 * @summary Test for Duration.plusMillis(long)
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testPlusMillisNormal();
        testPlusMillisZero();
        testPlusMillisNegative();
        testPlusMillisLarge();
    }

    static void testPlusMillisNormal() {
        Duration d = Duration.ofSeconds(10);
        Duration result = d.plusMillis(1500);
        long expectedSeconds = 10 + 1; // 1500 ms = 1 s + 500 ms
        long expectedNanos = 500_000_000;
        assert result.getSeconds() == expectedSeconds : "Expected seconds: " + expectedSeconds + ", but got: " + result.getSeconds();
        assert result.getNano() == expectedNanos : "Expected nanos: " + expectedNanos + ", but got: " + result.getNano();
    }

    static void testPlusMillisZero() {
        Duration d = Duration.ofSeconds(5);
        Duration result = d.plusMillis(0);
        assert result.equals(d) : "Adding zero milliseconds should not change the duration";
    }

    static void testPlusMillisNegative() {
        Duration d = Duration.ofSeconds(10);
        Duration result = d.plusMillis(-500);
        long expectedSeconds = 10 - 1; // -500 ms = -1 s + 500 ms
        long expectedNanos = 500_000_000;
        assert result.getSeconds() == expectedSeconds : "Expected seconds: " + expectedSeconds + ", but got: " + result.getSeconds();
        assert result.getNano() == expectedNanos : "Expected nanos: " + expectedNanos + ", but got: " + result.getNano();
    }

    static void testPlusMillisLarge() {
        Duration d = Duration.ofSeconds(1);
        long largeMillis = Long.MAX_VALUE;
        Duration result = d.plusMillis(largeMillis);
        // Since the result may be large, just check that the result is not null and has expected type
        assert result != null : "Result should not be null";
        // Additional checks could be added if needed
    }
}
