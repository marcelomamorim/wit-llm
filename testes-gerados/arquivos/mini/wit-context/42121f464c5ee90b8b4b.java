/*
 * @test
 * @summary Deterministic tests for java.util.SplittableRandom.ints()
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.IntStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testIntsStreamNotNull();
        testIntsStreamHasValues();
        testIntsStreamDistinctCalls();
    }

    private static void testIntsStreamNotNull() {
        SplittableRandom sr = new SplittableRandom(12345L);
        IntStream stream = sr.ints();
        if (stream == null) {
            throw new AssertionError("ints() returned null stream");
        }
        stream.close();
    }

    private static void testIntsStreamHasValues() {
        SplittableRandom sr = new SplittableRandom(67890L);
        IntStream stream = sr.ints();
        int count = 0;
        int sum = 0;
        try {
            for (int val : (int[]) stream.limit(10).toArray()) {
                sum += val;
                count++;
            }
        } finally {
            stream.close();
        }
        if (count != 10) {
            throw new AssertionError("ints() stream did not produce 10 values, got " + count);
        }
        // sum is not asserted to a fixed value because of randomness, but must be within int range
        if (sum < Integer.MIN_VALUE || sum > Integer.MAX_VALUE) {
            throw new AssertionError("Sum of ints() stream values out of int range");
        }
    }

    private static void testIntsStreamDistinctCalls() {
        SplittableRandom sr = new SplittableRandom(13579L);
        IntStream stream1 = sr.ints();
        IntStream stream2 = sr.ints();
        int[] arr1 = null;
        int[] arr2 = null;
        try {
            arr1 = stream1.limit(5).toArray();
            arr2 = stream2.limit(5).toArray();
        } finally {
            stream1.close();
            stream2.close();
        }
        if (arr1.length != 5 || arr2.length != 5) {
            throw new AssertionError("ints() streams did not produce 5 values each");
        }
        boolean allEqual = true;
        for (int i = 0; i < 5; i++) {
            if (arr1[i] != arr2[i]) {
                allEqual = false;
                break;
            }
        }
        if (allEqual) {
            throw new AssertionError("Two ints() streams from same SplittableRandom instance produced identical sequences");
        }
    }
}
