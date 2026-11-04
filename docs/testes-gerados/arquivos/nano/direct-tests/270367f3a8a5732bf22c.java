/*
 * @test
 * @summary Test tryAdvance method of java.util.Spliterators with LongConsumer
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNextLong();
        testTryAdvanceWithNull();
        testTryAdvanceWhenNoNext();
    }

    static void testTryAdvanceWithNextLong() {
        java.util.Iterator<Long> iterator = java.util.Arrays.asList(1L, 2L, 3L).iterator();
        boolean result;
        result = Spliterators.tryAdvance(action -> {
            if (iterator.hasNext()) {
                long val = iterator.next();
                action.accept(val);
            }
        });
        assert result : "Expected true when iterator has next";
    }

    static void testTryAdvanceWithNull() {
        try {
            Spliterators.tryAdvance(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testTryAdvanceWhenNoNext() {
        java.util.Iterator<Long> emptyIterator = java.util.Collections.emptyIterator();
        boolean result;
        result = Spliterators.tryAdvance(action -> {
            if (emptyIterator.hasNext()) {
                long val = emptyIterator.next();
                action.accept(val);
            }
        });
        assert !result : "Expected false when iterator has no next";
    }
}
