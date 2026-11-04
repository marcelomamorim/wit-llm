/*
 * @test
 * @summary Tests the 'ints()' method of java.util.concurrent.ThreadLocalRandom.
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testIntsMethod();
    }

    public static void testIntsMethod() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        IntStream stream = rand.ints();
        int[] sample = stream.limit(10).toArray();
        boolean allInRange = true;
        for (int val : sample) {
            if (val < 0 || val > Integer.MAX_VALUE) {
                allInRange = false;
                break;
            }
        }
        if (!allInRange) {
            throw new AssertionError("Generated int values are out of expected range.");
        }
        // Additional check: ensure stream produces different values on subsequent calls
        IntStream stream2 = rand.ints();
        int[] sample2 = stream2.limit(10).toArray();
        boolean different = false;
        for (int i = 0; i < sample.length; i++) {
            if (sample[i] != sample2[i]) {
                different = true;
                break;
            }
        }
        if (!different) {
            System.out.println("Warning: Consecutive calls to ints() produced identical samples.");
        }
    }
}
