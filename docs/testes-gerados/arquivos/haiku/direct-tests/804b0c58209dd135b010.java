/*
 * @test
 * @summary Behavioral tests for Stream.iterate(seed, UnaryOperator)
 * @run main StreamWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class StreamWitupTest {

    public static void main(String[] args) {
        testSequenceAppliesFunctionToPrevious();
        testStringSequence();
        testNullSeedIsAllowed();
        testCreationIsLazy();
        testNullFunctionThrowsNpe();
        testTryAdvanceNullActionThrowsNpe();
    }

    static void testSequenceAppliesFunctionToPrevious() {
        Object[] values = Stream.iterate(1, x -> x * 2).limit(5).toArray();
        check(Arrays.equals(values, new Object[]{1, 2, 4, 8, 16}), "doubling sequence");
    }

    static void testStringSequence() {
        Object[] values = Stream.iterate("a", s -> s + "a").limit(3).toArray();
        check(Arrays.equals(values, new Object[]{"a", "aa", "aaa"}), "string sequence");
    }

    static void testNullSeedIsAllowed() {
        Object[] values = Stream.iterate((String) null, s -> "x").limit(1).toArray();
        check(values.length == 1 && values[0] == null, "null seed as first element");
    }

    static void testCreationIsLazy() {
        AtomicInteger calls = new AtomicInteger();
        Stream<Integer> s = Stream.iterate(0, x -> {
            calls.incrementAndGet();
            return x + 1;
        });
        check(calls.get() == 0, "function invoked before terminal operation");
        s.limit(1).toArray();
        check(calls.get() == 0, "function invoked for the seed element");
    }

    static void testNullFunctionThrowsNpe() {
        try {
            Stream.iterate(1, null);
            check(false, "no NPE for null function");
        } catch (NullPointerException expected) {
        }
    }

    static void testTryAdvanceNullActionThrowsNpe() {
        try {
            Stream.iterate(1, x -> x).spliterator().tryAdvance(null);
            check(false, "no NPE for null action");
        } catch (NullPointerException expected) {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
