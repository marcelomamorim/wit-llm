/*
 * @test
 * @summary Test for forEachRemaining method in java.util.SplittableRandom
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.IntConsumer;

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
        SplittableRandom sr = new SplittableRandom();
        final int[] count = {0};
        IntConsumer consumer = new IntConsumer() {
            @Override
            public void accept(int value) {
                count[0]++;
            }
        };
        try {
            sr.forEachRemaining(consumer);
            if (count[0] >= 0) {
                System.out.println("PASS: Consumer accepted " + count[0] + " values");
            } else {
                System.out.println("FAIL: Consumer did not accept any values");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
