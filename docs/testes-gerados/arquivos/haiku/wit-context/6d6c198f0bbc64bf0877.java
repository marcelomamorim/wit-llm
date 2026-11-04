/*
 * @test
 * @summary Verifies size() of PriorityBlockingQueue across add, poll, clear and constructor variants
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(q.size() == 0, "new empty queue must have size 0");

        q.offer(3);
        q.offer(1);
        q.offer(2);
        check(q.size() == 3, "size after three offers must be 3, was " + q.size());

        Integer head = q.poll();
        check(head != null && head == 1, "poll must return smallest element");
        check(q.size() == 2, "size after poll must be 2, was " + q.size());

        q.clear();
        check(q.size() == 0, "size after clear must be 0, was " + q.size());

        PriorityBlockingQueue<Integer> cap = new PriorityBlockingQueue<>(1);
        check(cap.size() == 0, "capacity constructor must start empty");
        cap.offer(10);
        cap.offer(20);
        cap.offer(30);
        check(cap.size() == 3, "queue must grow beyond initial capacity, size was " + cap.size());

        PriorityBlockingQueue<Integer> rev = new PriorityBlockingQueue<>(11, Comparator.reverseOrder());
        check(rev.size() == 0, "comparator constructor must start empty");
        rev.offer(5);
        rev.offer(7);
        check(rev.size() == 2, "size with comparator queue must be 2, was " + rev.size());

        PriorityBlockingQueue<Integer> fromColl = new PriorityBlockingQueue<>(Arrays.asList(5, 4, 6));
        check(fromColl.size() == 3, "collection constructor size must be 3, was " + fromColl.size());
        fromColl.poll();
        check(fromColl.size() == 2, "size after poll from collection-based queue must be 2");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
