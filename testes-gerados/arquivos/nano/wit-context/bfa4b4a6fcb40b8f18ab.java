/*
 * @test
 * @summary Test for ThreadLocalRandom.forEachRemaining(DoubleConsumer)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
    }

    private static void testForEachRemainingWithNullConsumer() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        try {
            rand.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testForEachRemainingWithValidConsumer() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        final int[] count = {0};
        DoubleConsumer consumer = new DoubleConsumer() {
            @Override
            public void accept(double value) {
                count[0]++;
            }
        };
        try {
            rand.forEachRemaining(consumer);
            if (count[0] > 0) {
                System.out.println("PASS: Consumer accepted values, count = " + count[0]);
            } else {
                System.out.println("FAIL: Consumer did not accept any values");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
