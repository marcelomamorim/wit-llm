/*
 * @test
 * @summary Test the ints() method of java.util.Random for determinism and correctness.
 * @run main RandomWitupTest_P2e5995d3e294
 */

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest_P2e5995d3e294 {

    public static void main(String[] args) {
        testIntsMethod();
    }

    public static void testIntsMethod() {
        Random random1 = new Random(12345L);
        Random random2 = new Random(12345L);
        IntStream stream1 = random1.ints();
        IntStream stream2 = random2.ints();
        int[] array1 = stream1.limit(100).toArray();
        int[] array2 = stream2.limit(100).toArray();
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                throw new AssertionError("Mismatch at index " + i + ": " + array1[i] + " != " + array2[i]);
            }
        }
        System.out.println("ints() method produces deterministic sequences for same seed.");
        // Additional test: ensure sequence differs with different seed
        Random random3 = new Random(54321L);
        IntStream stream3 = random3.ints();
        int[] array3 = stream3.limit(100).toArray();
        boolean allEqual = true;
        for (int i = 0; i < 100; i++) {
            if (array1[i] == array3[i]) {
                allEqual = false;
                break;
            }
        }
        if (allEqual) {
            throw new AssertionError("Different seeds should produce different sequences.");
        }
        System.out.println("Different seeds produce different sequences.");
    }
}
