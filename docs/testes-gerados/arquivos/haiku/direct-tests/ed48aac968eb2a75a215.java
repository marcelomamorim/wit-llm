/*
 * @test
 * @summary White-box-free checks of PriorityBlockingQueue iterator forEachRemaining via public iterator()
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrows();
        testEmptyQueueVisitsNothing();
        testVisitsAllRemainingElements();
        testSecondCallVisitsNothing();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrows() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        Iterator<Integer> it = q.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testEmptyQueueVisitsNothing() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        Iterator<Integer> it = q.iterator();
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.isEmpty(), "empty queue must visit no elements");
    }

    static void testVisitsAllRemainingElements() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(4);
        q.add(2);
        q.add(3);
        Iterator<Integer> it = q.iterator();
        Integer first = it.next();
        List<Integer> rest = new ArrayList<>();
        it.forEachRemaining(rest::add);
        check(rest.size() == 4, "forEachRemaining must visit the 4 remaining elements, got " + rest.size());
        List<Integer> all = new ArrayList<>(rest);
        all.add(first);
        Collections.sort(all);
        check(all.equals(List.of(1, 2, 3, 4, 5)), "visited elements must be the queue contents, got " + all);
    }

    static void testSecondCallVisitsNothing() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(7);
        q.add(8);
        Iterator<Integer> it = q.iterator();
        List<Integer> first = new ArrayList<>();
        it.forEachRemaining(first::add);
        check(first.size() == 2, "first call must visit 2 elements");
        List<Integer> second = new ArrayList<>();
        it.forEachRemaining(second::add);
        check(second.isEmpty(), "second call must visit no elements");
    }
}
