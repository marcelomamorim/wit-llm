/*
 * @test
 * @summary Tests the minusNanos method of Duration for various inputs.
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testMinusNanosZero();
        testMinusNanosPositive();
        testMinusNanosNegative();
        testMinusNanosLongMin();
        testMinusNanosLongMax();
    }

    static void testMinusNanosZero() {
        Duration d = Duration.ofSeconds(10);
        Duration result = d.minusNanos(0);
        assert result.equals(Duration.ofSeconds(10)) : "Failed: minusNanos(0)";
    }

    static void testMinusNanosPositive() {
        Duration d = Duration.ofSeconds(10);
        Duration result = d.minusNanos(1);
        assert result.equals(Duration.ofSeconds(9, 999999999)) : "Failed: minusNanos(1)";
    }

    static void testMinusNanosNegative() {
        Duration d = Duration.ofSeconds(10);
        Duration result = d.minusNanos(-1);
        assert result.equals(Duration.ofSeconds(11, 000000001)) : "Failed: minusNanos(-1)";
    }

    static void testMinusNanosLongMin() {
        Duration d = Duration.ofSeconds(0);
        Duration result = d.minusNanos(Long.MIN_VALUE);
        Duration expected = Duration.ofSeconds(Long.MAX_VALUE).plusNanos(1);
        assert result.equals(expected) : "Failed: minusNanos(Long.MIN_VALUE)";
    }

    static void testMinusNanosLongMax() {
        Duration d = Duration.ofSeconds(0);
        Duration result = d.minusNanos(Long.MAX_VALUE);
        Duration expected = Duration.ofSeconds(-Long.MAX_VALUE).minusNanos(0);
        assert result.equals(expected) : "Failed: minusNanos(Long.MAX_VALUE)";
    }
}
