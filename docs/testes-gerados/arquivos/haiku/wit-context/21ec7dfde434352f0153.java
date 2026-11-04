/*
 * @test
 * @summary Witup-derived behavioral tests for ArrayBlockingQueue.poll()
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPollOnEmptyQueueReturnsNull();
        testPollOnEmptyQueueRepeatedlyKeepsQueueUsable();
        testPollReturnsElementsInFifoOrder();
        testPollFreesCapacityForOffer();
        testPollOnPrefilledQueue();
        testPollOnFairQueue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPollOnEmptyQueueReturnsNull() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(2);
        check(q.poll() == null, "poll() on empty queue must return null");
        check(q.size() == 0, "size must remain 0 after poll on empty queue");
    }

    static void testPollOnEmptyQueueRepeatedlyKeepsQueueUsable() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(2);
        check(q.poll() == null, "first poll on empty queue must return null");
        check(q.poll() == null, "second poll on empty queue must return null");
        check(q.poll() == null, "third poll on empty queue must return null");
        check(q.offer(Integer.valueOf(7)), "offer must succeed after empty polls");
        check(Integer.valueOf(7).equals(q.poll()), "poll must return the offered element");
        check(q.poll() == null, "poll after draining must return null");
    }

    static void testPollReturnsElementsInFifoOrder() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(3);
        check(q.offer(Integer.valueOf(1)), "offer 1 failed");
        check(q.offer(Integer.valueOf(2)), "offer 2 failed");
        check(q.offer(Integer.valueOf(3)), "offer 3 failed");
        check(Integer.valueOf(1).equals(q.poll()), "first poll must return 1");
        check(Integer.valueOf(2).equals(q.poll()), "second poll must return 2");
        check(Integer.valueOf(3).equals(q.poll()), "third poll must return 3");
        check(q.poll() == null, "poll on drained queue must return null");
    }

    static void testPollFreesCapacityForOffer() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(1);
        check(q.offer(Integer.valueOf(10)), "initial offer must succeed");
        check(!q.offer(Integer.valueOf(11)), "offer on full queue must return false");
        check(Integer.valueOf(10).equals(q.poll()), "poll must return 10");
        check(q.remainingCapacity() == 1, "poll must free capacity");
        check(q.offer(Integer.valueOf(11)), "offer must succeed after poll freed capacity");
        check(Integer.valueOf(11).equals(q.poll()), "poll must return 11");
        check(q.poll() == null, "poll on empty queue must return null");
    }

    static void testPollOnPrefilledQueue() {
        ArrayBlockingQueue<Integer> q =
            new ArrayBlockingQueue<Integer>(5, false, Arrays.asList(Integer.valueOf(1), Integer.valueOf(2)));
        check(Integer.valueOf(1).equals(q.poll()), "poll must return first prefilled element");
        check(q.size() == 1, "size must be 1 after one poll");
        check(Integer.valueOf(2).equals(q.poll()), "poll must return second prefilled element");
        check(q.poll() == null, "poll on drained prefilled queue must return null");
    }

    static void testPollOnFairQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(2, true);
        check(q.poll() == null, "poll on empty fair queue must return null");
        check(q.offer(Integer.valueOf(5)), "offer on fair queue must succeed");
        check(Integer.valueOf(5).equals(q.poll()), "poll on fair queue must return 5");
        check(q.poll() == null, "poll after drain on fair queue must return null");
    }
}
