/* @test    @summary Test the poll() method of DelayQueue for normal and exceptional behavior    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Delayed;
import java.util.concurrent.locks.ReentrantLock;
import java.util.NoSuchElementException;

public class DelayQueueWitupTest {

    public static void main(String[] args) {
        testPollEmptyQueueReturnsNull();
        testPollWithExpiredElementReturnsElement();
        testPollWithNonExpiredElementReturnsNull();
        testPollLockAcquisitionFailure();
    }

    private static void testPollEmptyQueueReturnsNull() {
        DelayQueue<Delayed> dq = new DelayQueue<>();
        assert dq.poll() == null : "Expected null when polling empty queue";
    }

    private static void testPollWithExpiredElementReturnsElement() {
        DelayQueue<Delayed> dq = new DelayQueue<>();
        dq.offer(new ExpiredDelayed());
        Delayed result = dq.poll();
        assert result != null : "Expected element, got null";
        assert result instanceof ExpiredDelayed : "Expected ExpiredDelayed instance";
    }

    private static void testPollWithNonExpiredElementReturnsNull() {
        DelayQueue<Delayed> dq = new DelayQueue<>();
        dq.offer(new NonExpiredDelayed());
        assert dq.poll() == null : "Expected null for non-expired element";
    }

    private static void testPollLockAcquisitionFailure() {
        DelayQueue<Delayed> dq = new DelayQueue<>();
        dq.offer(new ExpiredDelayed());
        // Simulate lock acquisition failure by subclassing and overriding lock()
        DelayQueue<Delayed> dqWithFailLock = new DelayQueue<Delayed>() {
            @Override
            public Delayed poll() {
                final ReentrantLock lock = this.lock;
                boolean lockAcquired = false;
                try {
                    lockAcquired = lock.tryLock();
                    if (!lockAcquired) {
                        throw new UnsupportedOperationException("Lock acquisition failed");
                    }
                    return super.poll();
                } finally {
                    if (lockAcquired) {
                        lock.unlock();
                    }
                }
            }
        };
        dqWithFailLock.offer(new ExpiredDelayed());
        try {
            dqWithFailLock.poll();
            assert false : "Expected UnsupportedOperationException";
        } catch (UnsupportedOperationException e) {
            assert e.getMessage().equals("Lock acquisition failed") : "Unexpected exception message";
        }
    }

    private static class ExpiredDelayed implements Delayed {
        @Override
        public long getDelay(TimeUnit unit) {
            return -1; // Already expired
        }
        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }

    private static class NonExpiredDelayed implements Delayed {
        @Override
        public long getDelay(TimeUnit unit) {
            return 1000; // Not expired
        }
        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }
}
