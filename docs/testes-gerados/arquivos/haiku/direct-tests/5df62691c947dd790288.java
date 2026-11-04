/*
 * @test
 * @summary Checks public Iterator.forEachRemaining behavior of LinkedTransferQueue iterators
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Consumer;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testEmptyQueueNoCalls();
        testAllElementsInOrder();
        testRemainingAfterNext();
        testExhaustedAfterCall();
        testSecondCallIsNoOp();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNpe() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>(Arrays.asList("a"));
        Iterator<String> it = q.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testEmptyQueueNoCalls() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        Iterator<Integer> it = q.iterator();
        List<Integer> collected = new ArrayList<>();
        Consumer<Integer> action = x -> collected.add(x);
        it.forEachRemaining(action);
        check(collected.isEmpty(), "empty queue must not invoke action");
        check(!it.hasNext(), "iterator of empty queue must have no next");
    }

    static void testAllElementsInOrder() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = q.iterator();
        List<Integer> collected = new ArrayList<>();
        Consumer<Integer> action = x -> collected.add(x);
        it.forEachRemaining(action);
        check(collected.toString().equals("[1, 2, 3]"),
              "expected [1, 2, 3] but got " + collected);
    }

    static void testRemainingAfterNext() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = q.iterator();
        check(it.next() == 1, "first next() must return 1");
        List<Integer> collected = new ArrayList<>();
        Consumer<Integer> action = x -> collected.add(x);
        it.forEachRemaining(action);
        check(collected.toString().equals("[2, 3]"),
              "expected remaining [2, 3] but got " + collected);
    }

    static void testExhaustedAfterCall() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(4, 5));
        Iterator<Integer> it = q.iterator();
        List<Integer> collected = new ArrayList<>();
        Consumer<Integer> action = x -> collected.add(x);
        it.forEachRemaining(action);
        check(!it.hasNext(), "iterator must be exhausted after forEachRemaining");
    }

    static void testSecondCallIsNoOp() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(7, 8));
        Iterator<Integer> it = q.iterator();
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();
        Consumer<Integer> firstAction = x -> first.add(x);
        Consumer<Integer> secondAction = x -> second.add(x);
        it.forEachRemaining(firstAction);
        it.forEachRemaining(secondAction);
        check(first.toString().equals("[7, 8]"), "first call must visit [7, 8] but got " + first);
        check(second.isEmpty(), "second call must not invoke action but got " + second);
    }
}
