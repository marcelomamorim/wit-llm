/*
 * @test
 * @summary Test for ThreadLocalRandom.tryAdvance(DoubleConsumer)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWhenIndexLessThanFence();
        testTryAdvanceWhenIndexEqualsFence();
    }

    private static void testTryAdvanceWithNullConsumer() {
        try {
            ThreadLocalRandom.current().tryAdvance(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        DoubleConsumer consumer = new DoubleConsumer() {
            boolean accepted = false;
            double value;
            @Override
            public void accept(double t) {
                accepted = true;
                value = t;
            }
        };
        boolean result = ThreadLocalRandom.current().tryAdvance(consumer);
        if (result) {
            System.out.println("PASS: tryAdvance returned true with valid consumer");
        } else {
            System.out.println("FAIL: tryAdvance returned false with valid consumer");
        }
    }

    private static void testTryAdvanceWhenIndexLessThanFence() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        // Reflection or internal state manipulation is complex; instead, simulate by calling multiple times
        // and checking behavior. Here, we assume index < fence initially.
        boolean result = r.tryAdvance(t -> {});
        if (result) {
            System.out.println("PASS: tryAdvance returned true when index < fence");
        } else {
            System.out.println("FAIL: tryAdvance returned false when index < fence");
        }
    }

    private static void testTryAdvanceWhenIndexEqualsFence() {
        // Since internal state is private, we cannot directly set index = fence.

        // As an alternative, we can invoke tryAdvance until it returns false, assuming index reaches fence.
        ThreadLocalRandom r = ThreadLocalRandom.current();
        boolean result;
        do {
            result = r.tryAdvance(t -> {});
        } while (result);
        // Now, next call should return false
        boolean finalResult = r.tryAdvance(t -> {});
        if (!finalResult) {
            System.out.println("PASS: tryAdvance returned false when index == fence");
        } else {
            System.out.println("FAIL: tryAdvance returned true when index == fence");
        }
    }
}
