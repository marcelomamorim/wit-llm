/* @test    @summary Test for forEachRemaining(DoubleConsumer) method in java.util.SplittableRandom    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.function.DoubleConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
    }

    private static void testForEachRemainingWithNullConsumer() {
        SplittableRandom sr = new SplittableRandom();
        try {
            sr.forEachRemaining(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testForEachRemainingWithValidConsumer() {
        SplittableRandom sr = new SplittableRandom(42L);
        final int[] count = {0};
        DoubleConsumer consumer = new DoubleConsumer() {
            @Override
            public void accept(double value) {
                count[0]++;
            }
        };
        try {
            sr.forEachRemaining(consumer);
            if (count[0] > 0) {
                System.out.println("Passed: Consumer accepted values, count = " + count[0]);
            } else {
                System.err.println("Failed: Consumer did not accept any values");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception during valid consumer test: " + t);
        }
    }
}
