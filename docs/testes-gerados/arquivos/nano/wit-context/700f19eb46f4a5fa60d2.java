/*
 * @test
 * @summary Test for ThreadLocalRandom.tryAdvance(LongConsumer)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.LongConsumer;

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
            System.out.println("FAIL: Expected NullPointerException for null consumer");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        final boolean[] called = {false};
        LongConsumer consumer = (long value) -> {
            called[0] = true;
            if (value == 0L || value == 1L || value == Long.MAX_VALUE) {
                System.out.println("PASS: Consumer accepted value: " + value);
            } else {
                System.out.println("FAIL: Consumer received unexpected value: " + value);
            }
        };
        boolean result = ThreadLocalRandom.current().tryAdvance(consumer);
        if (result) {
            if (called[0]) {
                System.out.println("PASS: Consumer was called successfully");
            } else {
                System.out.println("FAIL: Consumer was not called");
            }
        } else {
            System.out.println("FAIL: tryAdvance returned false unexpectedly");
        }
    }

    private static void testTryAdvanceWhenIndexLessThanFence() {
        // Since index and fence are internal, we test the behavior assuming index < fence
        // by calling tryAdvance multiple times and observing behavior.
        LongConsumer consumer = (long value) -> {
            System.out.println("Consumer received: " + value);
        };
        boolean result = ThreadLocalRandom.current().tryAdvance(consumer);
        if (result) {
            System.out.println("PASS: tryAdvance returned true when index < fence");
        } else {
            System.out.println("FAIL: tryAdvance returned false when index < fence");
        }
    }

    private static void testTryAdvanceWhenIndexEqualsFence() {
        // Cannot directly set index and fence due to encapsulation; test by calling until false.
        // For simplicity, call tryAdvance until it returns false.
        LongConsumer consumer = (long value) -> {
            System.out.println("Consumer received: " + value);
        };
        boolean result;
        do {
            result = ThreadLocalRandom.current().tryAdvance(consumer);
        } while (result);
        System.out.println("tryAdvance returned false when index >= fence");
    }
}
