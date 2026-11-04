/* @test    @summary Test submit method of AbstractExecutorService for Callable tasks    @run main AbstractExecutorServiceWitupTest */

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class AbstractExecutorServiceWitupTest {

    static class DummyExecutor extends AbstractExecutorService {
        private boolean executed = false;
        private Runnable command;

        @Override
        public void execute(Runnable command) {
            this.command = command;
            executed = true;
            command.run();
        }

        @Override
        public boolean isShutdown() {
            return false;
        }

        @Override
        public boolean isTerminated() {
            return false;
        }

        @Override
        public void shutdown() {
            // no-op
        }

        @Override
        public List<Runnable> shutdownNow() {
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        testSubmitCallableReturnsFuture();
    }

    public static void testSubmitCallableReturnsFuture() {
        DummyExecutor executor = new DummyExecutor();
        Callable<String> task = new Callable<String>() {
            @Override
            public String call() {
                return "test";
            }
        };
        Future<String> future = executor.submit(task);
        assert future != null : "Future should not be null";
        try {
            String result = future.get();
            assert "test".equals(result) : "Expected result 'test', got " + result;
        } catch (Exception e) {
            throw new AssertionError("Exception while getting future result", e);
        }
    }
}
