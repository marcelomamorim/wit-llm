/*
 * @test
 * @summary Witup-generated tests for SynchronousQueue.drainTo(Collection)
 * @run main SynchronousQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testEmptyQueueReturnsZero(boolean fair) {
        SynchronousQueue<String> q = new SynchronousQueue<>(fair);
        List<String> out = new ArrayList<>();
        int n = q.drainTo(out);
        check(n == 0, "expected 0 drained from empty queue, got " + n);
        check(out.isEmpty(), "collection must remain empty");
    }

    static void testNullCollectionThrowsNpe(boolean fair) {
        SynchronousQueue<String> q = new SynchronousQueue<>(fair);
        boolean thrown = false;
        try {
            q.drainTo(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "drainTo(null) must throw NullPointerException");
    }

    static void testSelfDrainThrowsIae(boolean fair) {
        SynchronousQueue<String> q = new SynchronousQueue<>(fair);
        boolean thrown = false;
        try {
            Collection<? super String> c = q;
            q.drainTo(c);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "drainTo(this) must throw IllegalArgumentException");
    }

    static void testDrainsFromProducers(boolean fair) throws InterruptedException {
        SynchronousQueue<Integer> q = new SynchronousQueue<>(fair);
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    q.put(i);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        producer.start();
        List<Integer> out = new ArrayList<>();
        int total = 0;
        while (out.size() < 3) {
            total += q.drainTo(out);
            Thread.yield();
        }
        producer.join();
        check(total == 3, "drainTo counts must sum to 3, got " + total);
        check(out.equals(Arrays.asList(0, 1, 2)), "unexpected drained elements: " + out);
        check(q.isEmpty(), "queue must be empty after drain");
        check(q.poll() == null, "poll must return null after drain");
    }

    public static void main(String[] args) throws Exception {
        for (boolean fair : new boolean[] {false, true}) {
            testEmptyQueueReturnsZero(fair);
            testNullCollectionThrowsNpe(fair);
            testSelfDrainThrowsIae(fair);
            testDrainsFromProducers(fair);
        }
        System.out.println("PASSED");
    }
}
