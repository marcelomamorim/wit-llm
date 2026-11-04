/*
 * @test
 * @summary Checks observable size() of ArrayBlockingQueue across add, offer, put, poll, take, remove, clear and collection constructor
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        check(q.size() == 0, "new queue size must be 0");
        q.add(1);
        check(q.size() == 1, "size after add must be 1");
        q.offer(2);
        check(q.size() == 2, "size after offer must be 2");
        q.put(3);
        check(q.size() == 3, "size after put must be 3");
        check(!q.offer(4), "offer on full queue must return false");
        check(q.size() == 3, "size must remain at capacity after rejected offer");
        q.poll();
        check(q.size() == 2, "size after poll must be 2");
        q.take();
        check(q.size() == 1, "size after take must be 1");
        q.clear();
        check(q.size() == 0, "size after clear must be 0");

        ArrayBlockingQueue<String> c = new ArrayBlockingQueue<>(4, true, Arrays.asList("a", "b"));
        check(c.size() == 2, "size from collection constructor must be 2");
        check(c.remove("a"), "remove of present element must return true");
        check(c.size() == 1, "size after remove must be 1");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
