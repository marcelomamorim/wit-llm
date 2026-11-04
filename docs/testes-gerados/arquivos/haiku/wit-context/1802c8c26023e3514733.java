/*
 * @test
 * @summary Verify InfiniteSupplyingSpliterator.OfInt.tryAdvance rejects a null action and delivers supplied values via the public IntStream.generate API
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNonNullActionReceivesSuppliedValue();
        testReturnsTrueOnEachAdvance();
    }

    static void testNullActionThrowsNPE() {
        Spliterator.OfInt sp = IntStream.generate(() -> 42).spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action");
        }
    }

    static void testNonNullActionReceivesSuppliedValue() {
        Spliterator.OfInt sp = IntStream.generate(() -> 42).spliterator();
        int[] holder = new int[1];
        boolean result = sp.tryAdvance((IntConsumer) v -> holder[0] = v);
        if (!result) {
            throw new RuntimeException("tryAdvance should return true");
        }
        if (holder[0] != 42) {
            throw new RuntimeException("Expected 42 but got " + holder[0]);
        }
    }

    static void testReturnsTrueOnEachAdvance() {
        int[] counter = new int[1];
        Spliterator.OfInt sp = IntStream.generate(() -> ++counter[0]).spliterator();
        int[] last = new int[1];
        for (int i = 1; i <= 3; i++) {
            boolean result = sp.tryAdvance((IntConsumer) v -> last[0] = v);
            if (!result) {
                throw new RuntimeException("tryAdvance should return true on iteration " + i);
            }
            if (last[0] != i) {
                throw new RuntimeException("Expected " + i + " but got " + last[0]);
            }
        }
    }
}
