/*
 * @test
 * @summary WITUP-generated checks for ConcurrentLinkedDeque spliterator tryAdvance
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testEmptyDequeReturnsFalseAndDoesNotAccept();
        testAdvancesInOrderUntilExhausted();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNpe() {
        Spliterator<String> sp = new ConcurrentLinkedDeque<String>().spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
    }

    static void testEmptyDequeReturnsFalseAndDoesNotAccept() {
        Spliterator<String> sp = new ConcurrentLinkedDeque<String>().spliterator();
        List<String> out = new ArrayList<>();
        Consumer<String> c = out::add;
        boolean result = sp.tryAdvance(c);
        check(!result, "tryAdvance on empty deque must return false");
        check(out.isEmpty(), "consumer must not be invoked on empty deque");
    }

    static void testAdvancesInOrderUntilExhausted() {
        ConcurrentLinkedDeque<String> deque =
            new ConcurrentLinkedDeque<>(Arrays.asList("a", "b", "c"));
        Spliterator<String> sp = deque.spliterator();
        List<String> out = new ArrayList<>();
        Consumer<String> c = out::add;
        check(sp.tryAdvance(c), "first tryAdvance must return true");
        check(sp.tryAdvance(c), "second tryAdvance must return true");
        check(sp.tryAdvance(c), "third tryAdvance must return true");
        check(!sp.tryAdvance(c), "tryAdvance after exhaustion must return false");
        check(out.equals(Arrays.asList("a", "b", "c")),
              "elements must be delivered in order, got " + out);
    }
}
