/*
 * @test
 * @summary Verify PriorityQueue(int) rejects non-positive initial capacity and accepts a positive capacity
 * @run main PriorityQueueWitupTest
 */
import java.util.PriorityQueue;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        expectIllegalArgument(0);
        expectIllegalArgument(-1);
        expectIllegalArgument(Integer.MIN_VALUE);

        PriorityQueue<Integer> q = new PriorityQueue<>(1);
        check(q.isEmpty() && q.size() == 0, "new queue with capacity 1 should be empty");
        q.offer(5);
        q.offer(3);
        q.offer(9);
        check(q.size() == 3, "size should be 3 after three offers");
        check(q.poll() == 3, "head should be the least element (3)");
        check(q.poll() == 5, "next head should be 5");
        check(q.poll() == 9, "last head should be 9");
        check(q.poll() == null, "poll on empty queue should return null");
    }

    static void expectIllegalArgument(int capacity) {
        try {
            new PriorityQueue<Integer>(capacity);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for initialCapacity " + capacity);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
