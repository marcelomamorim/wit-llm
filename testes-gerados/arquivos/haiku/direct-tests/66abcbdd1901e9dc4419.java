/*
 * @test
 * @summary Verifies ArrayBlockingQueue.drainTo(Collection) observable behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDrainEmptyQueueReturnsZero() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(4);
        List<Integer> target = new ArrayList<Integer>();
        int n = q.drainTo(target);
        check(n == 0, "expected 0 drained, got " + n);
        check(target.isEmpty(), "target should remain empty");
    }

    static void testDrainAllElementsFifoOrder() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(5);
        q.offer(1);
        q.offer(2);
        q.offer(3);
        List<Integer> target = new ArrayList<Integer>();
        int n = q.drainTo(target);
        check(n == 3, "expected 3 drained, got " + n);
        check(target.equals(Arrays.asList(1, 2, 3)), "unexpected target: " + target);
        check(q.isEmpty(), "queue should be empty after drain");
        check(q.size() == 0, "size should be 0 after drain");
    }

    static void testDrainFairQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(5, true);
        q.offer(10);
        q.offer(20);
        List<Integer> target = new ArrayList<Integer>();
        int n = q.drainTo(target);
        check(n == 2, "expected 2 drained, got " + n);
        check(target.equals(Arrays.asList(10, 20)), "unexpected target: " + target);
    }

    static void testDrainAppendsToExistingElements() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(3);
        q.offer(1);
        q.offer(2);
        List<Integer> target = new ArrayList<Integer>();
        target.add(0);
        int n = q.drainTo(target);
        check(n == 2, "expected 2 drained, got " + n);
        check(target.equals(Arrays.asList(0, 1, 2)), "unexpected target: " + target);
    }

    static void testDrainIntoSupertypeCollection() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(3);
        q.offer(7);
        q.offer(8);
        List<Object> target = new ArrayList<Object>();
        int n = q.drainTo(target);
        check(n == 2, "expected 2 drained, got " + n);
        check(target.equals(Arrays.asList((Object) 7, (Object) 8)), "unexpected target: " + target);
    }

    static void testQueueReusableAfterDrain() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(2);
        q.offer(1);
        q.drainTo(new ArrayList<Integer>());
        check(q.offer(4), "offer should succeed after drain");
        check(q.poll() == 4, "poll should return element offered after drain");
    }

    static void testDrainQueueCreatedWithCollection() {
        ArrayBlockingQueue<Integer> q =
            new ArrayBlockingQueue<Integer>(5, false, Arrays.asList(7, 8));
        List<Integer> target = new ArrayList<Integer>();
        int n = q.drainTo(target);
        check(n == 2, "expected 2 drained, got " + n);
        check(target.equals(Arrays.asList(7, 8)), "unexpected target: " + target);
    }

    public static void main(String[] args) {
        testDrainEmptyQueueReturnsZero();
        testDrainAllElementsFifoOrder();
        testDrainFairQueue();
        testDrainAppendsToExistingElements();
        testDrainIntoSupertypeCollection();
        testQueueReusableAfterDrain();
        testDrainQueueCreatedWithCollection();
    }
}
