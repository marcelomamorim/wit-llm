/*
 * @test
 * @summary isQueued reports threads waiting in the AQS queue and rejects null
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            return compareAndSetState(0, 1);
        }

        @Override
        protected boolean tryRelease(int arg) {
            setState(0);
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        Sync s = new Sync();
        Thread mainThread = Thread.currentThread();

        if (s.isQueued(mainThread)) {
            throw new RuntimeException("main thread reported as queued initially");
        }

        s.acquire(1);

        Thread worker = new Thread(() -> s.acquire(1));
        worker.start();

        long deadline = System.nanoTime() + 10_000_000_000L;
        while (worker.getState() != Thread.State.WAITING) {
            if (System.nanoTime() > deadline) {
                throw new RuntimeException("worker did not reach WAITING state");
            }
            Thread.sleep(10);
        }

        if (!s.isQueued(worker)) {
            throw new RuntimeException("waiting worker not reported as queued");
        }
        if (s.isQueued(mainThread)) {
            throw new RuntimeException("main thread reported as queued while holding lock");
        }

        s.release(1);
        worker.join(10000);

        if (worker.isAlive()) {
            throw new RuntimeException("worker did not acquire after release");
        }
        if (s.getState() != 1) {
            throw new RuntimeException("expected state 1 held by worker, got " + s.getState());
        }
        if (s.isQueued(worker)) {
            throw new RuntimeException("worker still reported as queued after acquiring");
        }

        try {
            s.isQueued(null);
            throw new RuntimeException("isQueued(null) did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
