/*
 * @test
 * @summary Witup-derived observable behavior checks for LinkedBlockingQueue.peek()
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testPeekOnEmptyQueueReturnsNull();
        testPeekDoesNotRemoveElement();
        testPeekReturnsHeadAfterPoll();
        testPeekOnCapacityBoundedQueue();
        testPeekOnCollectionConstructor();
        testPeekAfterClear();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testPeekOnEmptyQueueReturnsNull() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        check(q.peek() == null, "peek on empty queue must return null");
        check(q.size() == 0, "size must remain 0 after peek on empty queue");
    }

    static void testPeekDoesNotRemoveElement() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        check(Integer.valueOf(1).equals(q.peek()), "first peek must return head 1");
        check(Integer.valueOf(1).equals(q.peek()), "second peek must still return head 1");
        check(q.size() == 2, "peek must not remove elements");
    }

    static void testPeekReturnsHeadAfterPoll() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        check(Integer.valueOf(1).equals(q.poll()), "poll must return 1");
        check(Integer.valueOf(2).equals(q.peek()), "peek must return new head 2");
        check(q.size() == 2, "size must be 2 after poll");
    }

    static void testPeekOnCapacityBoundedQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(2);
        check(q.peek() == null, "peek on empty bounded queue must return null");
        check(q.offer(5), "first offer must succeed");
        check(q.offer(6), "second offer must succeed");
        check(!q.offer(7), "offer beyond capacity must return false");
        check(Integer.valueOf(5).equals(q.peek()), "peek must return head 5");
    }

    static void testPeekOnCollectionConstructor() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(4, 5, 6));
        check(Integer.valueOf(4).equals(q.peek()), "peek must return first element of source collection");
        check(q.size() == 3, "size must equal source collection size");
    }

    static void testPeekAfterClear() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(9);
        q.clear();
        check(q.peek() == null, "peek after clear must return null");
    }
}
