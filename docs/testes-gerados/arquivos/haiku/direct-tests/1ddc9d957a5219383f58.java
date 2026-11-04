/*
 * @test
 * @summary takeFirst returns queued elements in FIFO order, blocks until an element is available, and reacts to interruption
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        check(d.offerLast(1), "offerLast 1 should succeed");
        check(d.offerLast(2), "offerLast 2 should succeed");
        check(d.takeFirst() == 1, "takeFirst should return first element");
        check(d.takeFirst() == 2, "takeFirst should return second element");
        check(d.size() == 0, "deque should be empty after takes");

        final LinkedBlockingDeque<String> q = new LinkedBlockingDeque<>();
        final String[] result = new String[1];
        Thread consumer = new Thread(() -> {
            try {
                result[0] = q.takeFirst();
            } catch (InterruptedException e) {
                result[0] = "interrupted";
            }
        });
        consumer.start();
        q.putLast("x");
        consumer.join(10000);
        check(!consumer.isAlive(), "consumer should finish after element is put");
        check("x".equals(result[0]), "takeFirst should return the put element");
        check(q.size() == 0, "deque should be empty after blocked take");

        final LinkedBlockingDeque<String> q2 = new LinkedBlockingDeque<>();
        final boolean[] interrupted = new boolean[1];
        Thread blocked = new Thread(() -> {
            try {
                q2.takeFirst();
            } catch (InterruptedException e) {
                interrupted[0] = true;
            }
        });
        blocked.start();
        blocked.interrupt();
        blocked.join(10000);
        check(!blocked.isAlive(), "blocked taker should finish after interrupt");
        check(interrupted[0], "takeFirst should throw InterruptedException when interrupted");
    }
}
