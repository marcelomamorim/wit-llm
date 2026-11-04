/*
 * @test
 * @summary Tests for java.util.Random class, specifically for forEachRemaining(LongConsumer)
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.function.LongConsumer;

public class RandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
    }

    static void testForEachRemainingWithNullConsumer() {
        Random rand = new Random(12345L);
        try {
            rand.forEachRemaining(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }

    static void testForEachRemainingWithValidConsumer() {
        Random rand = new Random(12345L);
        final long[] collected = {0L};
        LongConsumer consumer = new LongConsumer() {
            @Override
            public void accept(long value) {
                collected[0] += value;
            }
        };
        try {
            rand.forEachRemaining(consumer);
            System.out.println("Passed: forEachRemaining executed without exceptions, sum: " + collected[0]);
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception during valid consumer: " + t);
            System.exit(1);
        }
    }
}
