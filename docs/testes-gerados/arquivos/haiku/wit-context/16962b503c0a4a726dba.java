/*
 * @test
 * @summary Verify PriorityQueue.add rejects null elements and preserves heap ordering
 * @run main PriorityQueueWitupTest
 */

import java.util.Collections;
import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testAddNullThrowsNPE();
        testAddReturnsTrueAndOrdersNaturally();
        testAddWithReverseComparator();
        testAddManyElementsPollsSorted();
    }

    static void testAddNullThrowsNPE() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        boolean thrown = false;
        try {
            pq.add(null);
        } catch (NullPointerException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("add(null) did not throw NullPointerException");
        }
        if (!pq.isEmpty()) {
            throw new RuntimeException("queue modified after rejected add(null)");
        }
    }

    static void testAddReturnsTrueAndOrdersNaturally() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        if (!pq.add(5)) {
            throw new RuntimeException("add(5) returned false");
        }
        if (!pq.add(1)) {
            throw new RuntimeException("add(1) returned false");
        }
        if (!pq.add(3)) {
            throw new RuntimeException("add(3) returned false");
        }
        if (pq.size() != 3) {
            throw new RuntimeException("unexpected size: " + pq.size());
        }
        if (pq.peek() != 1) {
            throw new RuntimeException("unexpected head: " + pq.peek());
        }
        if (pq.poll() != 1 || pq.poll() != 3 || pq.poll() != 5) {
            throw new RuntimeException("natural ordering violated");
        }
        if (pq.poll() != null) {
            throw new RuntimeException("poll on empty queue did not return null");
        }
    }

    static void testAddWithReverseComparator() {
        PriorityQueue<String> pq = new PriorityQueue<>(Collections.reverseOrder());
        pq.add("a");
        pq.add("c");
        pq.add("b");
        if (!"c".equals(pq.peek())) {
            throw new RuntimeException("unexpected head with reverse comparator: " + pq.peek());
        }
        if (!"c".equals(pq.poll()) || !"b".equals(pq.poll()) || !"a".equals(pq.poll())) {
            throw new RuntimeException("reverse comparator ordering violated");
        }
    }

    static void testAddManyElementsPollsSorted() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int n = 1000;
        for (int i = 0; i < n; i++) {
            pq.add((i * 37) % n);
        }
        if (pq.size() != n) {
            throw new RuntimeException("unexpected size: " + pq.size());
        }
        int prev = -1;
        for (int i = 0; i < n; i++) {
            Integer v = pq.poll();
            if (v == null || v <= prev) {
                throw new RuntimeException("elements not polled in ascending order at index " + i);
            }
            prev = v;
        }
        if (!pq.isEmpty()) {
            throw new RuntimeException("queue not empty after polling all elements");
        }
    }
}
