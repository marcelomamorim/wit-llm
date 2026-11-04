/*
 * @test
 * @summary Test for Random.forEachRemaining(IntConsumer)
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.function.IntConsumer;

public class RandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
    }

    static void testForEachRemainingWithNullConsumer() {
        Random rand = new Random(12345L);
        try {
            rand.forEachRemaining(null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testForEachRemainingWithValidConsumer() {
        Random rand = new Random(12345L);
        final int[] collected = new int[10];
        IntConsumer consumer = new IntConsumer() {
            int count = 0;
            @Override
            public void accept(int value) {
                if (count < collected.length) {
                    collected[count++] = value;
                }
            }
        };
        rand.forEachRemaining(consumer);
        boolean allZero = true;
        for (int v : collected) {
            if (v != 0) {
                allZero = false;
                break;
            }
        }
        if (allZero) {
            System.err.println("Consumer did not process any elements");
            System.exit(1);
        }
    }
}
