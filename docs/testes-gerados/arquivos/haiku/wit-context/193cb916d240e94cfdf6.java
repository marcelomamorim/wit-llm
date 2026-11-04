/*
 * @test
 * @summary Verify AbstractQueuedLongSynchronizer.acquire(long) propagates UnsupportedOperationException from the default tryAcquire and delegates to a successful tryAcquire override
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static class Plain extends AbstractQueuedLongSynchronizer {
    }

    static class Succeeding extends AbstractQueuedLongSynchronizer {
        long seen = -1L;

        @Override
        protected boolean tryAcquire(long arg) {
            seen = arg;
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        boolean thrown = false;
        try {
            new Plain().acquire(1L);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException from default tryAcquire");
        }

        Succeeding s = new Succeeding();
        s.acquire(42L);
        if (s.seen != 42L) {
            throw new AssertionError("tryAcquire was not invoked with the acquire argument: " + s.seen);
        }
    }
}
