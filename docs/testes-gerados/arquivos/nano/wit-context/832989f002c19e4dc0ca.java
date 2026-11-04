/*
 * @test
 * @summary Test for Spliterators.forEachRemaining(LongConsumer) method.
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    private static void testForEachRemainingWithNullAction() {
        Spliterators.OfLong spliterator = new Spliterators.OfLong() {
            @Override
            public boolean tryAdvance(LongConsumer action) {
                return false;
            }
        };
        try {
            spliterator.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testForEachRemainingWithValidAction() {
        final int[] counter = {0};
        LongConsumer consumer = new LongConsumer() {
            @Override
            public void accept(long value) {
                counter[0]++;
            }
        };
        Spliterators.OfLong spliterator = new Spliterators.OfLong() {
            private boolean done = false;
            @Override
            public boolean tryAdvance(LongConsumer action) {
                if (done) return false;
                action.accept(1L);
                done = true;
                return true;
            }
        };
        spliterator.forEachRemaining(consumer);
        if (counter[0] == 1) {
            System.out.println("PASS: forEachRemaining processed element correctly");
        } else {
            System.out.println("FAIL: forEachRemaining did not process element as expected");
        }
    }
}
