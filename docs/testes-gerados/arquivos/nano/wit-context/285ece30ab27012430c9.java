/*
 * @test
 * @summary Test for AbstractQueuedSynchronizer.isQueued(Thread)
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    public static void main(String[] args) {
        testIsQueuedWithNullThread();
        testIsQueuedWithNonQueuedThread();
        testIsQueuedWithQueuedThread();
    }

    private static void testIsQueuedWithNullThread() {
        try {
            AbstractQueuedSynchronizer aqSync = new DummyAQS();
            aqSync.isQueued(null);
            System.out.println("FAIL: Expected NullPointerException for null thread");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null thread");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null thread: " + t);
        }
    }

    private static void testIsQueuedWithNonQueuedThread() {
        try {
            AbstractQueuedSynchronizer aqSync = new DummyAQS();
            Thread thread = new Thread();
            boolean result = aqSync.isQueued(thread);
            if (result == false) {
                System.out.println("PASS: Thread not queued as expected");
            } else {
                System.out.println("FAIL: Thread unexpectedly reported as queued");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for non-queued thread: " + t);
        }
    }

    private static void testIsQueuedWithQueuedThread() {
        try {
            DummyAQS aqSync = new DummyAQS();
            Thread thread = new Thread();
            aqSync.addThreadToQueue(thread);
            boolean result = aqSync.isQueued(thread);
            if (result) {
                System.out.println("PASS: Thread correctly reported as queued");
            } else {
                System.out.println("FAIL: Thread not reported as queued when it should be");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for queued thread: " + t);
        }
    }

    private static class DummyAQS extends AbstractQueuedSynchronizer {
        private final Collection<Thread> queuedThreads = new ArrayList<>();

        public void addThreadToQueue(Thread thread) {
            queuedThreads.add(thread);
        }

        @Override
        public final boolean isQueued(Thread thread) {
            if (thread == null) {
                throw new NullPointerException();
            }
            for (Thread t : queuedThreads) {
                if (t == thread) {
                    return true;
                }
            }
            return false;
        }
    }
}
