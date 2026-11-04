/* @test    @summary Testa o método peek() de DelayQueue para comportamento padrão e exceções.    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.NoSuchElementException;

public class DelayQueueWitupTest {

    public static void main(String[] args) {
        testPeekEmptyQueue();
        testPeekWithElements();
        testPeekWithLockHeld();
    }

    private static void testPeekEmptyQueue() {
        DelayQueue<Object> dq = new DelayQueue<>();
        Object result = dq.peek();
        if (result != null) {
            throw new AssertionError("Expected null for empty queue, got: " + result);
        }
    }

    private static void testPeekWithElements() {
        DelayQueue<String> dq = new DelayQueue<>();
        dq.offer("element1");
        Object result = dq.peek();
        if (result == null || !result.equals("element1")) {
            throw new AssertionError("Expected 'element1', got: " + result);
        }
    }

    private static void testPeekWithLockHeld() {
        DelayQueue<Object> dq = new DelayQueue<>();
        ReentrantLock lock = dq.lock;
        lock.lock();
        try {
            Object result = dq.peek();
            if (result != null) {
                throw new AssertionError("Expected null when lock is held and queue is empty, got: " + result);
            }
        } finally {
            lock.unlock();
        }
    }
}
