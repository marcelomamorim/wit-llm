/*
 * @test
 * @summary Verifies observable behavior of LinkedBlockingDeque.clear(): emptying, capacity restoration, reuse, and waking blocked producers
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        testClearEmptiesUnbounded();
        testClearOnEmptyDeque();
        testClearRestoresBoundedCapacity();
        testClearAllowsReuse();
        testClearWakesBlockedProducer();
    }

    static void testClearEmptiesUnbounded() {
        LinkedBlockingDeque<String> d =
            new LinkedBlockingDeque<>(Arrays.asList("a", "b", "c"));
        d.clear();
        check(d.isEmpty(), "deque should be empty after clear");
        check(d.size() == 0, "size should be 0 after clear");
        check(d.peekFirst() == null, "peekFirst should be null after clear");
        check(d.peekLast() == null, "peekLast should be null after clear");
        Iterator<String> it = d.iterator();
        check(!it.hasNext(), "iterator should be exhausted after clear");
        check(d.remainingCapacity() == Integer.MAX_VALUE,
              "unbounded capacity should be preserved");
    }

    static void testClearOnEmptyDeque() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.clear();
        check(d.isEmpty(), "empty deque should remain empty");
        check(d.size() == 0, "size should remain 0");
        d.clear();
        check(d.size() == 0, "second clear should be a no-op");
    }

    static void testClearRestoresBoundedCapacity() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(2);
        check(d.offerLast(1), "offerLast should succeed");
        check(d.offerLast(2), "offerLast should succeed");
        check(!d.offerLast(3), "offerLast should fail when full");
        check(d.remainingCapacity() == 0, "remaining capacity should be 0 when full");
        d.clear();
        check(d.remainingCapacity() == 2, "remaining capacity should be restored to 2");
        check(d.size() == 0, "size should be 0 after clear");
    }

    static void testClearAllowsReuse() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(3);
        d.offerFirst("x");
        d.offerLast("y");
        d.clear();
        check(d.offerFirst("p"), "offerFirst should succeed after clear");
        check(d.offerLast("q"), "offerLast should succeed after clear");
        check(d.size() == 2, "size should be 2 after reuse");
        check("p".equals(d.peekFirst()), "first element should be p");
        check("q".equals(d.peekLast()), "last element should be q");
        check("p".equals(d.pollFirst()), "pollFirst should return p");
        check("q".equals(d.pollFirst()), "pollFirst should return q");
        check(d.isEmpty(), "deque should be empty after polling all");
    }

    static void testClearWakesBlockedProducer() throws InterruptedException {
        final LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(1);
        d.put(1);
        Thread t = new Thread(() -> {
            try {
                d.put(2);
            } catch (InterruptedException e) {
                // ignored: test verifies completion below
            }
        });
        t.start();
        Thread.sleep(100);
        d.clear();
        t.join(10000);
        check(!t.isAlive(), "blocked producer should be released by clear");
        check(d.size() == 1, "size should be 1 after producer inserted");
        check(d.peekFirst() == 2, "producer element should be present after clear");
    }
}
