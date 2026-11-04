/*
 * @test
 * @summary Behavioral checks for LinkedBlockingDeque.takeFirst: FIFO retrieval, blocking until an element arrives, and InterruptedException when interrupted while empty
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> q = new LinkedBlockingDeque<>();
        q.addLast("a");
        q.addLast("b");
        check("a".equals(q.takeFirst()));
        check("b".equals(q.takeFirst()));
        check(q.isEmpty());

        LinkedBlockingDeque<String> c = new LinkedBlockingDeque<>(Arrays.asList("x", "y"));
        check("x".equals(c.takeFirst()));
        check(c.size() == 1);

        LinkedBlockingDeque<Integer> bounded = new LinkedBlockingDeque<>(1);
        bounded.putFirst(7);
        check(bounded.takeFirst() == 7);

        Thread.currentThread().interrupt();
        try {
            new LinkedBlockingDeque<String>().takeFirst();
            throw new AssertionError("expected InterruptedException");
        } catch (InterruptedException expected) {
        }
        check(!Thread.currentThread().isInterrupted());

        final LinkedBlockingDeque<String> w = new LinkedBlockingDeque<>();
        final AtomicReference<String> result = new AtomicReference<>();
        Thread t = new Thread(() -> {
            try {
                result.set(w.takeFirst());
            } catch (InterruptedException e) {
            }
        });
        t.start();
        w.putLast("late");
        t.join(10000);
        check(!t.isAlive());
        check("late".equals(result.get()));
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new AssertionError();
        }
    }
}
