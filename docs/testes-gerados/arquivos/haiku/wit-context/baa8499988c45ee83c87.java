/*
 * @test
 * @summary Checks AbstractExecutorService.submit(Runnable) null rejection and normal submission behavior
 * @run main AbstractExecutorServiceWitupTest
 */

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AbstractExecutorServiceWitupTest {

    static class Probe extends AbstractExecutorService {
        int executed;

        public void execute(Runnable command) {
            executed++;
            command.run();
        }

        public void shutdown() {
        }

        public List<Runnable> shutdownNow() {
            return null;
        }

        public boolean isShutdown() {
            return false;
        }

        public boolean isTerminated() {
            return false;
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) {
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        testNullTaskThrowsNPE();
        testNonNullTaskIsExecutedAndReturnsFuture();
    }

    static void testNullTaskThrowsNPE() {
        Probe p = new Probe();
        boolean thrown = false;
        try {
            p.submit(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null task");
        }
        if (p.executed != 0) {
            throw new RuntimeException("execute must not be called for null task");
        }
    }

    static void testNonNullTaskIsExecutedAndReturnsFuture() throws Exception {
        Probe p = new Probe();
        int[] ran = new int[1];
        Future<?> f = p.submit(() -> ran[0]++);
        if (f == null) {
            throw new RuntimeException("submit returned null");
        }
        if (ran[0] != 1) {
            throw new RuntimeException("Runnable was not run exactly once: " + ran[0]);
        }
        if (p.executed != 1) {
            throw new RuntimeException("execute was not called exactly once: " + p.executed);
        }
        if (!f.isDone()) {
            throw new RuntimeException("Future should be done");
        }
        if (f.get() != null) {
            throw new RuntimeException("Future result for Runnable should be null");
        }
    }
}
