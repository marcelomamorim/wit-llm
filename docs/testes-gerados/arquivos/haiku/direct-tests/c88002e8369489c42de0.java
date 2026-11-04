/*
 * @test
 * @summary Tests LinkedBlockingDeque.takeLast for non-empty, interrupted, blocking and bounded deques
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicReference;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testNonEmpty();
        testInterrupted();
        testBlockingUntilElementArrives();
        testFromCollectionAndBounded();
    }

    static void testNonEmpty() throws Exception {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.offerFirst("a");
        d.offerLast("b");
        d.offerLast("c");
        check("c".equals(d.takeLast()));
        check(d.size() == 2);
        check("b".equals(d.takeLast()));
        check(d.size() == 1);
        check("a".equals(d.takeFirst()));
        check(d.isEmpty());
    }

    static void testInterrupted() {
        Thread.currentThread().interrupt();
        try {
            new LinkedBlockingDeque<String>().takeLast();
            throw new RuntimeException("expected InterruptedException");
        } catch (InterruptedException e) {
            // expected
        }
        check(!Thread.interrupted());
    }

    static void testBlockingUntilElementArrives() throws Exception {
        LinkedBlockingDeque<String> q = new LinkedBlockingDeque<>();
        AtomicReference<String> result = new AtomicReference<>();
        Thread t = new Thread(() -> {
            try {
                result.set(q.takeLast());
            } catch (InterruptedException e) {
                // ignored
            }
        });
        t.start();
        q.offerLast("x");
        t.join(10000);
        check(!t.isAlive());
        check("x".equals(result.get()));
        check(q.isEmpty());
    }

    static void testFromCollectionAndBounded() throws Exception {
        LinkedBlockingDeque<String> fromCol = new LinkedBlockingDeque<>(Arrays.asList("p", "q"));
        check("q".equals(fromCol.takeLast()));
        check("p".equals(fromCol.takeLast()));

        LinkedBlockingDeque<Integer> bounded = new LinkedBlockingDeque<>(2);
        bounded.offerLast(1);
        bounded.offerLast(2);
        check(bounded.takeLast() == 2);
        check(bounded.remainingCapacity() == 1);
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("check failed");
        }
    }
}
