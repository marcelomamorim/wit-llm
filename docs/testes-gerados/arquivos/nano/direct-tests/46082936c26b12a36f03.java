/* @test    @summary Test for Random.forEachRemaining method to ensure it processes remaining elements correctly.    @run main RandomWitupTest */

import java.util.Random;
import java.util.function.IntConsumer;

public class RandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithNoRemaining();
    }

    static void testForEachRemainingWithValidConsumer() {
        Random rng = new Random(12345L);
        int[] results = new int[5];
        IntConsumer consumer = new IntConsumer() {
            int index = 0;
            public void accept(int value) {
                if (index < results.length) {
                    results[index++] = value;
                }
            }
        };
        // Advance the generator to a known state
        for (int i = 0; i < 10; i++) {
            rng.nextInt();
        }
        // Call forEachRemaining
        rng.forEachRemaining(consumer);
        boolean allProcessed = true;
        for (int val : results) {
            if (val == 0) {
                allProcessed = false;
                break;
            }
        }
        if (!allProcessed) {
            throw new AssertionError("Not all remaining elements were processed.");
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        Random rng = new Random(54321L);
        boolean exceptionThrown = false;
        try {
            rng.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException was not thrown.");
        }
    }

    static void testForEachRemainingWithNoRemaining() {
        Random rng = new Random(67890L);
        // Advance to a known state where no remaining elements
        for (int i = 0; i < 100; i++) {
            rng.nextInt();
        }
        // Save current index to simulate no remaining
        // Since index is private, we simulate no remaining by calling nextInt directly
        // and then calling forEachRemaining
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                throw new AssertionError("Consumer should not be called when no remaining elements.");
            }
        };
        // Call forEachRemaining; should not invoke consumer
        rng.forEachRemaining(consumer); // Should not throw or call consumer
    }
}
