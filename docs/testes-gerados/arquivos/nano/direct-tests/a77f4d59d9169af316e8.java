/*
 * @test
 * @summary Test Instant.minusNanos for correct subtraction behavior
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testMinusNanosNormal();
        testMinusNanosMinValue();
        testMinusNanosMaxValue();
    }

    static void testMinusNanosNormal() {
        Instant original = Instant.ofEpochSecond(1000L, 500L);
        Instant result = original.minusNanos(200L);
        long expectedSeconds = 1000L - (200L / 1_000_000_000L);
        int expectedNanos = (int) (500L - (200L % 1_000_000_000L));
        if (result.getEpochSecond() != expectedSeconds || result.getNano() != expectedNanos) {
            throw new AssertionError("Failed normal minusNanos test: " + result);
        }
    }

    static void testMinusNanosMinValue() {
        Instant original = Instant.ofEpochSecond(0L, 0);
        Instant result = original.minusNanos(Long.MIN_VALUE);
        Instant expected = original.plusNanos(Long.MAX_VALUE).plusNanos(1);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed Long.MIN_VALUE minusNanos test: " + result);
        }
    }

    static void testMinusNanosMaxValue() {
        Instant original = Instant.ofEpochSecond(0L, 0);
        Instant result = original.minusNanos(Long.MAX_VALUE);
        Instant expected = original.plusNanos(-Long.MAX_VALUE);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed Long.MAX_VALUE minusNanos test: " + result);
        }
    }
}
