/*
 * @test
 * @summary Verifies tryAdvance on the ArrayDeque spliterator: null action, empty deque, in-order consumption and late binding
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyDequeReturnsFalseAndDoesNotInvokeAction();
        testConsumesElementsInOrder();
        testLateBindingSeesElementsAddedBeforeFirstAdvance();
    }

    static void testNullActionThrowsNPE() {
        ArrayDeque<String> d = new ArrayDeque<>();
        d.add("a");
        Spliterator<String> sp = d.spliterator();
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null action");
    }

    static void testEmptyDequeReturnsFalseAndDoesNotInvokeAction() {
        ArrayDeque<String> d = new ArrayDeque<>();
        final int[] calls = {0};
        boolean r = d.spliterator().tryAdvance(x -> calls[0]++);
        check(!r, "tryAdvance on empty deque should return false");
        check(calls[0] == 0, "action must not be invoked on empty deque");
    }

    static void testConsumesElementsInOrder() {
        ArrayDeque<String> d = new ArrayDeque<>();
        d.add("a");
        d.add("b");
        Spliterator<String> sp = d.spliterator();
        List<String> seen = new ArrayList<>();
        check(sp.tryAdvance(seen::add), "first tryAdvance should return true");
        check(sp.tryAdvance(seen::add), "second tryAdvance should return true");
        check(!sp.tryAdvance(seen::add), "third tryAdvance should return false");
        check(seen.size() == 2, "expected 2 elements, got " + seen.size());
        check("a".equals(seen.get(0)), "first element should be a");
        check("b".equals(seen.get(1)), "second element should be b");
    }

    static void testLateBindingSeesElementsAddedBeforeFirstAdvance() {
        ArrayDeque<String> d = new ArrayDeque<>();
        Spliterator<String> sp = d.spliterator();
        d.add("x");
        d.add("y");
        List<String> seen = new ArrayList<>();
        while (sp.tryAdvance(seen::add)) {
        }
        check(seen.size() == 2, "late binding: expected 2 elements, got " + seen.size());
        check("x".equals(seen.get(0)), "late binding: first element should be x");
        check("y".equals(seen.get(1)), "late binding: second element should be y");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
