/*
 * @test
 * @summary Verifies observable remainingCapacity behavior of ArrayBlockingQueue across insertion, removal, full state and construction variants
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        check(q.remainingCapacity() == 3, "empty queue should report full capacity");

        check(q.offer("a"), "offer into non-full queue should succeed");
        check(q.remainingCapacity() == 2, "remainingCapacity should decrease after offer");

        q.put("b");
        check(q.remainingCapacity() == 1, "remainingCapacity should decrease after put");

        q.add("c");
        check(q.remainingCapacity() == 0, "full queue should report zero remaining capacity");

        check(!q.offer("d"), "offer into full queue should return false");
        check(q.remainingCapacity() == 0, "failed offer must not change remainingCapacity");

        check("a".equals(q.poll()), "poll should return head element");
        check(q.remainingCapacity() == 1, "remainingCapacity should increase after poll");

        check("b".equals(q.take()), "take should return next head element");
        check(q.remainingCapacity() == 2, "remainingCapacity should increase after take");

        check(q.size() + q.remainingCapacity() == 3, "size plus remainingCapacity should equal capacity");

        q.clear();
        check(q.remainingCapacity() == 3, "clear should restore full remaining capacity");

        ArrayBlockingQueue<Integer> fair = new ArrayBlockingQueue<>(4, true);
        check(fair.remainingCapacity() == 4, "fair empty queue should report full capacity");
        fair.offer(1);
        check(fair.remainingCapacity() == 3, "fair queue remainingCapacity should decrease after offer");

        ArrayBlockingQueue<String> initial =
                new ArrayBlockingQueue<>(4, false, Arrays.asList("x", "y"));
        check(initial.remainingCapacity() == 2, "collection constructor should reduce remaining capacity");
        check(initial.size() + initial.remainingCapacity() == 4,
              "size plus remainingCapacity should equal capacity for collection constructor");

        ArrayBlockingQueue<String> full =
                new ArrayBlockingQueue<>(2, false, Arrays.asList("p", "q"));
        check(full.remainingCapacity() == 0, "queue initialized full should report zero remaining capacity");

        ArrayBlockingQueue<String> one = new ArrayBlockingQueue<>(1);
        check(one.remainingCapacity() == 1, "capacity one empty queue should report 1");
        one.add("z");
        check(one.remainingCapacity() == 0, "capacity one full queue should report 0");
    }
}
