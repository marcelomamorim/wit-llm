/*
 * @test
 * @summary Tests AbstractExecutorService.submit(Runnable, T) null-task rejection and result propagation
 * @run main AbstractExecutorServiceWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AbstractExecutorServiceWitupTest {

    static class DirectExecutor extends AbstractExecutorService {
        private volatile boolean shutdown;

        public void execute(Runnable command) {
            command.run();
        }

        public void shutdown() {
            shutdown = true;
        }

        public List<Runnable> shutdownNow() {
            shutdown = true;
            return new ArrayList<Runnable>();
        }

        public boolean isShutdown() {
            return shutdown;
        }

        public boolean isTerminated() {
            return shutdown;
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) {
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        DirectExecutor ex = new DirectExecutor();

        boolean npe = false;
        try {
            ex.submit(null, "x");
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new AssertionError("submit(null, T) must throw NullPointerException");
        }

        Future<String> f = ex.submit(() -> { }, "value");
        if (!f.isDone()) {
            throw new AssertionError("future should be done after synchronous execute");
        }
        if (!"value".equals(f.get())) {
            throw new AssertionError("future must yield the supplied result");
        }

        Future<Object> f2 = ex.submit(() -> { throw new IllegalStateException("boom"); }, null);
        try {
            f2.get();
            throw new AssertionError("get() must throw ExecutionException");
        } catch (ExecutionException e) {
            if (!(e.getCause() instanceof IllegalStateException)) {
                throw new AssertionError("unexpected cause: " + e.getCause());
            }
        }
    }
}
