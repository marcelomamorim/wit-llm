/*
 * @test
 * @summary Basic observable behavior of LinkedBlockingDeque spliterator tryAdvance
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrows();
        testEmptyDequeReturnsFalse();
        testConsumesElementsInOrder();
        testSingleElement();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrows() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(Arrays.asList(1, 2));
        Spliterator<Integer> s = deque.spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) should throw NullPointerException");
    }

    static void testEmptyDequeReturnsFalse() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        Spliterator<Integer> s = deque.spliterator();
        final boolean[] called = {false};
        boolean result = s.tryAdvance(x -> called[0] = true);
        check(!result, "tryAdvance on empty deque should return false");
        check(!called[0], "action must not be invoked on empty deque");
    }

    static void testConsumesElementsInOrder() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(Arrays.asList(1, 2, 3));
        Spliterator<Integer> s = deque.spliterator();
        List<Integer> out = new ArrayList<>();
        while (s.tryAdvance(out::add)) {
            // consume until exhausted
        }
        check(out.equals(Arrays.asList(1, 2, 3)), "expected [1, 2, 3] but got " + out);
        check(!s.tryAdvance(out::add), "tryAdvance after exhaustion should return false");
        check(out.size() == 3, "no extra elements should be consumed");
    }

    static void testSingleElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("x");
        Spliterator<String> s = deque.spliterator();
        List<String> out = new ArrayList<>();
        check(s.tryAdvance(out::add), "first tryAdvance should return true");
        check(out.equals(Arrays.asList("x")), "expected [x] but got " + out);
        check(!s.tryAdvance(out::add), "second tryAdvance should return false");
    }
}
