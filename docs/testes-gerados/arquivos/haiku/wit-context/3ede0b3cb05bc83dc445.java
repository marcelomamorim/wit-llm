/*
 * @test
 * @summary Verifies that AbstractExecutorService.submit(Callable) rejects a null task with NullPointerException and still returns a usable Future for a non-null task.
 * @run main AbstractExecutorServiceWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AbstractExecutorServiceWitupTest {

    static class DirectExecutor extends AbstractExecutorService {
        private boolean shutdown;

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
        DirectExecutor executor = new DirectExecutor();

        boolean npeThrown = false;
        try {
            executor.submit((Callable<String>) null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("submit(null) did not throw NullPointerException");
        }

        Future<String> future = executor.submit(() -> "ok");
        if (future == null) {
            throw new RuntimeException("submit(non-null) returned null");
        }
        if (!future.isDone()) {
            throw new RuntimeException("future not done after synchronous execution");
        }
        if (!"ok".equals(future.get())) {
            throw new RuntimeException("unexpected result from future");
        }
    }
}
