/*
 * @test
 * @summary Verify ArrayDeque iterator forEachRemaining behavior: null action rejection, ordered traversal, empty deque, and exhaustion afterwards
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testIteratesAllElementsInOrder();
        testEmptyDequeDoesNotInvokeAction();
        testNoRemainingElementsAfterForEach();
    }

    static void testNullActionThrowsNPE() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.add(1);
        d.add(2);
        Iterator<Integer> it = d.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null action");
        }
    }

    static void testIteratesAllElementsInOrder() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.addLast(10);
        d.addLast(20);
        d.addLast(30);
        final List<Integer> seen = new ArrayList<>();
        Consumer<Integer> collector = x -> seen.add(x);
        Iterator<Integer> it = d.iterator();
        it.forEachRemaining(collector);
        List<Integer> expected = new ArrayList<>();
        expected.add(10);
        expected.add(20);
        expected.add(30);
        if (!expected.equals(seen)) {
            throw new RuntimeException("unexpected traversal: " + seen);
        }
    }

    static void testEmptyDequeDoesNotInvokeAction() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        final int[] count = {0};
        Consumer<Integer> counter = x -> count[0]++;
        Iterator<Integer> it = d.iterator();
        it.forEachRemaining(counter);
        if (count[0] != 0) {
            throw new RuntimeException("action invoked " + count[0] + " times on empty deque");
        }
    }

    static void testNoRemainingElementsAfterForEach() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.addLast(1);
        d.addLast(2);
        final int[] count = {0};
        Consumer<Integer> counter = x -> count[0]++;
        Iterator<Integer> it = d.iterator();
        it.forEachRemaining(counter);
        if (count[0] != 2) {
            throw new RuntimeException("expected 2 invocations but got " + count[0]);
        }
        if (it.hasNext()) {
            throw new RuntimeException("iterator still has elements after forEachRemaining");
        }
    }
}
