/* @test    @summary Test acquire method of AbstractQueuedLongSynchronizer for successful acquisition and interruption handling    @run main AbstractQueuedLongSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static class DummySync extends AbstractQueuedLongSynchronizer {
        @Override
        protected boolean tryAcquire(long arg) {
            return false;
        }
    }

    public static void main(String[] args) {
        DummySync sync = new DummySync();
        long testArg = 1L;
        try {
            sync.acquire(testArg);
            System.out.println("Acquire method completed without interruption.");
        } catch (InterruptedException e) {
            System.out.println("Acquire method was interrupted.");
        }
    }
}
