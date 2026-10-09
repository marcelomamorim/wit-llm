/* @test    @summary Test Random.ints() method for observable behavior.    @run main RandomWitupTest_P2e5995d3e294 */

import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest_P2e5995d3e294 {
    public static void main(String[] args) {
        testDefaultInts();
        testIntsWithSeed();
    }

    private static void testDefaultInts() {
        Random random = new Random();
        IntStream stream = random.ints();
        int[] result = stream.limit(5).toArray();
        boolean allInRange = true;
        for (int val : result) {
            if (val < Integer.MIN_VALUE || val > Integer.MAX_VALUE) {
                allInRange = false;
                break;
            }
        }
        if (!allInRange || result.length != 5) {
            throw new AssertionError("ints() produced values out of expected range or wrong length: " + java.util.Arrays.toString(result));
        }
    }

    private static void testIntsWithSeed() {
        long seed = 12345L;
        Random random1 = new Random(seed);
        Random random2 = new Random(seed);
        IntStream stream1 = random1.ints();
        IntStream stream2 = random2.ints();
        int[] arr1 = stream1.limit(5).toArray();
        int[] arr2 = stream2.limit(5).toArray();
        if (!java.util.Arrays.equals(arr1, arr2)) {
            throw new AssertionError("Randoms with same seed produced different sequences: " + java.util.Arrays.toString(arr1) + " vs " + java.util.Arrays.toString(arr2));
        }
    }
}
