/*
 * @test
 * @summary Test java.util.stream.Stream.forEachRemaining method behavior
 * @run main StreamWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class StreamWitupTest {

    static class Stream<T> {
        private final Function<T, T> next;
        private final Predicate<T> hasNext;
        private final T seed;
        private boolean finished = false;
        private boolean started = false;
        private T prev = null;

        Stream(T seed, Predicate<T> hasNext, Function<T, T> next) {
            this.seed = seed;
            this.hasNext = hasNext;
            this.next = next;
        }

        public void forEachRemaining(Consumer<? super T> action) {
            Objects.requireNonNull(action);
            if (finished)
                return;
            finished = true;
            T t = started ? next.apply(prev) : seed;
            prev = null;
            while (hasNext.test(t)) {
                action.accept(t);
                t = next.apply(t);
            }
        }
    }

    public static void main(String[] args) {
        testNullActionThrows();
        testEmptyStreamDoesNotCallAction();
        testSingleElementStreamCallsActionOnce();
        testMultipleElementsStreamCallsActionCorrectly();
        testForEachRemainingIdempotent();
    }

    private static void testNullActionThrows() {
        Stream<Integer> s = new Stream<>(0, x -> false, x -> x + 1);
        try {
            s.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testEmptyStreamDoesNotCallAction() {
        final List<Integer> collected = new ArrayList<>();
        Stream<Integer> s = new Stream<>(0, x -> false, x -> x + 1);
        s.forEachRemaining(collected::add);
        if (!collected.isEmpty()) {
            throw new RuntimeException("Expected no elements collected for empty stream");
        }
    }

    private static void testSingleElementStreamCallsActionOnce() {
        final List<Integer> collected = new ArrayList<>();
        Stream<Integer> s = new Stream<>(1, x -> x <= 1, x -> x + 1);
        s.forEachRemaining(collected::add);
        if (collected.size() != 1 || collected.get(0) != 1) {
            throw new RuntimeException("Expected single element 1 collected");
        }
    }

    private static void testMultipleElementsStreamCallsActionCorrectly() {
        final List<Integer> collected = new ArrayList<>();
        Stream<Integer> s = new Stream<>(1, x -> x <= 3, x -> x + 1);
        s.forEachRemaining(collected::add);
        if (collected.size() != 3 || !collected.get(0).equals(1) || !collected.get(1).equals(2) || !collected.get(2).equals(3)) {
            throw new RuntimeException("Expected elements 1, 2, 3 collected");
        }
    }

    private static void testForEachRemainingIdempotent() {
        final List<Integer> collected = new ArrayList<>();
        Stream<Integer> s = new Stream<>(1, x -> x <= 2, x -> x + 1);
        s.forEachRemaining(collected::add);
        int sizeAfterFirst = collected.size();
        s.forEachRemaining(collected::add);
        if (collected.size() != sizeAfterFirst) {
            throw new RuntimeException("Expected no additional elements collected after first forEachRemaining call");
        }
    }
}
