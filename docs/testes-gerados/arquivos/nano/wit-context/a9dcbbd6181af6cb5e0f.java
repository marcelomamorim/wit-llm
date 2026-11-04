/*
 * @test
 * @summary Test for forEachRemaining(LongConsumer) method in java.util.SplittableRandom
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.LongConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
    }

    private static void testForEachRemainingWithNullConsumer() {
        SplittableRandom sr = new SplittableRandom();
        try {
            sr.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testForEachRemainingWithValidConsumer() {
        SplittableRandom sr = new SplittableRandom(42L);
        final long[] sum = {0};
        LongConsumer consumer = new LongConsumer() {
            @Override
            public void accept(long value) {
                sum[0] += value;
            }
        };
        try {
            sr.forEachRemaining(consumer);
            System.out.println("PASS: forEachRemaining executed, sum = " + sum[0]);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception during valid consumer execution: " + t);
        }
    }
}
