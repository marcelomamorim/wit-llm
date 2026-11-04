/*
 * @test
 * @summary Verifies Itr.remove() via PriorityBlockingQueue.iterator(): IllegalStateException before next(), removal after next(), and second remove() without next() throws.
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static void testRemoveBeforeNextThrows() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        Iterator<Integer> it = q.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() must throw IllegalStateException");
        check(q.size() == 1, "queue must be unchanged after failed remove()");
    }

    static void testRemoveAfterNextRemovesElement() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(3);
        q.add(8);
        Iterator<Integer> it = q.iterator();
        Integer returned = it.next();
        it.remove();
        check(q.size() == 2, "size must decrease by one after remove()");
        check(!q.contains(returned), "returned element must no longer be in queue");
    }

    static void testSecondRemoveThrows() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(10);
        q.add(20);
        Iterator<Integer> it = q.iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second remove() without next() must throw IllegalStateException");
        check(q.size() == 1, "second failed remove() must not alter queue");
    }

    static void testRemoveAllDuringIteration() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        for (int i = 1; i <= 5; i++) {
            q.add(i * 7);
        }
        Iterator<Integer> it = q.iterator();
        int count = 0;
        while (it.hasNext()) {
            it.next();
            it.remove();
            count++;
        }
        check(count == 5, "iteration must visit all 5 elements");
        check(q.isEmpty(), "queue must be empty after removing all via iterator");
    }

    public static void main(String[] args) {
        testRemoveBeforeNextThrows();
        testRemoveAfterNextRemovesElement();
        testSecondRemoveThrows();
        testRemoveAllDuringIteration();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
