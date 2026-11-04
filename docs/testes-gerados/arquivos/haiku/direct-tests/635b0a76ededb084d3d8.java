/*
 * @test
 * @summary Exercises StreamSpliterators.AbstractWrappingSpliterator.tryAdvance through the public Stream.spliterator() API
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

public class StreamSpliteratorsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        testAdvanceDeliversElementsInOrder();
        testReturnsFalseAndSkipsConsumerWhenExhausted();
        testNullConsumerThrowsNullPointerException();
    }

    static void testAdvanceDeliversElementsInOrder() {
        Spliterator<String> sp = Stream.of("a", "b", "c")
                .map(s -> s + "!")
                .spliterator();
        List<String> out = new ArrayList<>();
        check(sp.tryAdvance(out::add), "first tryAdvance should return true");
        check(sp.tryAdvance(out::add), "second tryAdvance should return true");
        check(sp.tryAdvance(out::add), "third tryAdvance should return true");
        check(out.size() == 3, "expected 3 elements, got " + out.size());
        check(out.get(0).equals("a!"), "element 0 mismatch: " + out.get(0));
        check(out.get(1).equals("b!"), "element 1 mismatch: " + out.get(1));
        check(out.get(2).equals("c!"), "element 2 mismatch: " + out.get(2));
    }

    static void testReturnsFalseAndSkipsConsumerWhenExhausted() {
        Spliterator<Integer> sp = Stream.of(1)
                .map(x -> x + 1)
                .spliterator();
        List<Integer> out = new ArrayList<>();
        check(sp.tryAdvance(out::add), "first tryAdvance should return true");
        check(!sp.tryAdvance(out::add), "tryAdvance after exhaustion should return false");
        check(out.size() == 1, "consumer must not be invoked when no element remains, size=" + out.size());
        check(out.get(0) == 2, "element mismatch: " + out.get(0));
    }

    static void testNullConsumerThrowsNullPointerException() {
        Spliterator<String> sp = Stream.of("x")
                .map(s -> s)
                .spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) should throw NullPointerException");
    }
}
