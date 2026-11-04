/*
 * @test
 * @summary Test java.util.Random.ints() produces a stream of ints and is deterministic with seed
 * @run main java.util.RandomWitupTest
 */

import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        // Create Random with fixed seed
        Random r1 = new Random(12345L);
        Random r2 = new Random(12345L);

        // Obtain IntStreams
        IntStream s1 = r1.ints();
        IntStream s2 = r2.ints();

        // Take first 10 ints from each stream
        int[] arr1 = s1.limit(10).toArray();
        int[] arr2 = s2.limit(10).toArray();

        // Check that both arrays have length 10
        if (arr1.length != 10) {
            throw new AssertionError("Expected 10 ints from stream, got " + arr1.length);
        }
        if (arr2.length != 10) {
            throw new AssertionError("Expected 10 ints from stream, got " + arr2.length);
        }

        // Check that both arrays are equal (deterministic output)
        for (int i = 0; i < 10; i++) {
            if (arr1[i] != arr2[i]) {
                throw new AssertionError("Mismatch at index " + i + ": " + arr1[i] + " != " + arr2[i]);
            }
        }

        // Check that values are within int range (always true, but sanity check)
        for (int v : arr1) {
            // No need to check range since int[] elements are int
            // Just check no exception and values are int
        }

        // Check that stream is not parallel
        if (s1.isParallel()) {
            throw new AssertionError("Stream should not be parallel");
        }

        System.out.println("Random.ints() test passed");
    }
}
