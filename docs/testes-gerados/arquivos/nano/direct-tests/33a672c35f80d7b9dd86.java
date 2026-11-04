/*
 * @test
 * @summary Test the ints() method of ThreadLocalRandom for correct stream generation.
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testIntsMethod();
    }

    public static void testIntsMethod() {
        IntStream stream = ThreadLocalRandom.current().ints();
        int count = 0;
        int sum = 0;
        for (int value : stream.limit(10).toArray()) {
            // Values should be within Integer range; no exception expected.
            // Sum to verify values are accessible.
            sum += value;
            count++;
        }
        if (count != 10) {
            throw new AssertionError("Expected 10 elements, got " + count);
        }
        // Since values are random, just check that sum is within expected bounds.
        // For 10 random ints, sum should be within a reasonable range.
        int minExpectedSum = Integer.MIN_VALUE * 10;
        int maxExpectedSum = Integer.MAX_VALUE * 10;
        if (sum < minExpectedSum || sum > maxExpectedSum) {
            throw new AssertionError("Sum out of expected range: " + sum);
        }
        // Additional check: stream should be finite and not null.
        if (stream == null) {
            throw new AssertionError("Stream is null");
        }
    }
}
