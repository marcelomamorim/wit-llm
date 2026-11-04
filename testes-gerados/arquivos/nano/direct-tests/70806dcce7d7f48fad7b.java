/*
 * @test
 * @summary Test Instant.ofEpochSecond(long) for various epochSecond values
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testOfEpochSecondZero();
        testOfEpochSecondPositive();
        testOfEpochSecondNegative();
        testOfEpochSecondLarge();
    }

    static void testOfEpochSecondZero() {
        Instant result = Instant.ofEpochSecond(0L);
        if (result.getEpochSecond() != 0L) {
            throw new AssertionError("Expected epochSecond 0, but got " + result.getEpochSecond());
        }
    }

    static void testOfEpochSecondPositive() {
        long epochSecond = 123456789L;
        Instant result = Instant.ofEpochSecond(epochSecond);
        if (result.getEpochSecond() != epochSecond) {
            throw new AssertionError("Expected epochSecond " + epochSecond + ", but got " + result.getEpochSecond());
        }
    }

    static void testOfEpochSecondNegative() {
        long epochSecond = -987654321L;
        Instant result = Instant.ofEpochSecond(epochSecond);
        if (result.getEpochSecond() != epochSecond) {
            throw new AssertionError("Expected epochSecond " + epochSecond + ", but got " + result.getEpochSecond());
        }
    }

    static void testOfEpochSecondLarge() {
        long epochSecond = Long.MAX_VALUE - 1;
        Instant result = Instant.ofEpochSecond(epochSecond);
        if (result.getEpochSecond() != epochSecond) {
            throw new AssertionError("Expected epochSecond " + epochSecond + ", but got " + result.getEpochSecond());
        }
    }
}
