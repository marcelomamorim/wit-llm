/*
 * @test
 * @summary Verifies that remainingCapacity reflects capacity minus the current element count
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        check(q.remainingCapacity() == 3, "empty queue should report full capacity");

        q.add(1);
        check(q.remainingCapacity() == 2, "one element should reduce remaining capacity by one");

        q.add(2);
        q.add(3);
        check(q.remainingCapacity() == 0, "full queue should report zero remaining capacity");

        check(!q.offer(4), "offer on full queue should return false");
        check(q.remainingCapacity() == 0, "failed offer must not change remaining capacity");

        q.poll();
        check(q.remainingCapacity() == 1, "poll should free one slot");

        q.offer(4);
        check(q.remainingCapacity() == 0, "refilled queue should report zero remaining capacity");

        ArrayBlockingQueue<Integer> one = new ArrayBlockingQueue<>(1);
        check(one.remainingCapacity() == 1, "capacity one queue should be empty initially");
        one.offer(7);
        check(one.remainingCapacity() == 0, "capacity one queue should be full after offer");
        one.take();
        check(one.remainingCapacity() == 1, "take should restore remaining capacity");

        ArrayBlockingQueue<Integer> fair = new ArrayBlockingQueue<>(5, true, Arrays.asList(1, 2));
        check(fair.remainingCapacity() == 3, "collection constructor should count initial elements");

        ArrayBlockingQueue<Integer> full = new ArrayBlockingQueue<>(2, false, Arrays.asList(1, 2));
        check(full.remainingCapacity() == 0, "queue initialized to capacity should report zero");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
