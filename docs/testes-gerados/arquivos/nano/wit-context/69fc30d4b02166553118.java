/*
 * @test
 * @summary Tests for java.util.Spliterators.tryAdvance(IntConsumer)
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithValidAction();
        testTryAdvanceWhenIndexExceedsFence();
    }

    private static void testTryAdvanceWithNullAction() {
        try {
            boolean result = Spliterators.tryAdvance((IntConsumer) null);
            System.out.println("testTryAdvanceWithNullAction: FAILED - no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testTryAdvanceWithNullAction: PASSED");
        } catch (Throwable t) {
            System.out.println("testTryAdvanceWithNullAction: FAILED - wrong exception: " + t);
        }
    }

    private static void testTryAdvanceWithValidAction() {
        final int[] array = {1, 2, 3};
        final int[] index = {0};
        IntConsumer consumer = value -> {
            if (value != array[index[0]]) {
                throw new AssertionError("Value mismatch at index " + index[0]);
            }
            index[0]++;
        };
        boolean result = Spliterators.tryAdvance(consumer);
        if (result && index[0] == 1) {
            System.out.println("testTryAdvanceWithValidAction: PASSED");
        } else {
            System.out.println("testTryAdvanceWithValidAction: FAILED");
        }
    }

    private static void testTryAdvanceWhenIndexExceedsFence() {
        final int[] array = {1, 2, 3};
        final int[] index = {array.length};
        IntConsumer consumer = value -> {
            throw new AssertionError("Should not be called");
        };
        boolean result = false;
        try {
            result = Spliterators.tryAdvance(consumer);
        } catch (Exception e) {
            System.out.println("testTryAdvanceWhenIndexExceedsFence: FAILED - exception: " + e);
            return;
        }
        if (!result) {
            System.out.println("testTryAdvanceWhenIndexExceedsFence: PASSED");
        } else {
            System.out.println("testTryAdvanceWhenIndexExceedsFence: FAILED - returned true unexpectedly");
        }
    }
}
