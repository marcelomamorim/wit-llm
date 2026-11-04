/*
 * @test
 * @summary Test LinkedBlockingDeque.size() method for correctness and concurrency
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptyDequeSize();
        testAddElementsSize();
        testConcurrentSize();
    }

    static void testEmptyDequeSize() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        int size = deque.size();
        if (size != 0) {
            throw new AssertionError("Expected size 0 for empty deque, got " + size);
        }
    }

    static void testAddElementsSize() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        int size = deque.size();
        if (size != 3) {
            throw new AssertionError("Expected size 3 after adding 3 elements, got " + size);
        }
    }

    static void testConcurrentSize() throws Exception {
        final LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        final int threadCount = 10;
        final int addsPerThread = 1000;
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch doneLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        startLatch.await();
                        for (int j = 0; j < addsPerThread; j++) {
                            deque.add(j);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        doneLatch.countDown();
                    }
                }
            }).start();
        }

        startLatch.countDown();
        doneLatch.await();

        int expected = threadCount * addsPerThread;
        int actual = deque.size();
        if (actual != expected) {
            throw new AssertionError("Expected size " + expected + " but got " + actual);
        }
    }
}
