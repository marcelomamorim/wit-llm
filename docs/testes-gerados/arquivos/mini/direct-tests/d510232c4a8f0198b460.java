/*
 * @test
 * @summary Test ArrayBlockingQueue.offer(E) method behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Objects;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testOfferNullThrowsNPE();
        testOfferReturnsTrueWhenNotFull();
        testOfferReturnsFalseWhenFull();
    }

    static void testOfferNullThrowsNPE() {
        ArrayBlockingQueue<Object> q = new ArrayBlockingQueue<>(1);
        try {
            q.offer(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testOfferReturnsTrueWhenNotFull() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(2);
        boolean offered = q.offer("test");
        if (!offered) {
            throw new RuntimeException("offer returned false when queue not full");
        }
        // offer again, still space
        offered = q.offer("test2");
        if (!offered) {
            throw new RuntimeException("offer returned false when queue not full");
        }
    }

    static void testOfferReturnsFalseWhenFull() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(1);
        boolean offered = q.offer(1);
        if (!offered) {
            throw new RuntimeException("offer returned false when queue not full");
        }
        // Now queue is full
        offered = q.offer(2);
        if (offered) {
            throw new RuntimeException("offer returned true when queue is full");
        }
    }
}
