/* @test    @summary Test submit method of AbstractExecutorService with a simple Runnable    @run main AbstractExecutorServiceWitupTest */

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class AbstractExecutorServiceWitupTest {

    public static void main(String[] args) {
        testSubmitWithRunnable();
    }

    public static void testSubmitWithRunnable() {
        AbstractExecutorService executor = new AbstractExecutorService() {
            @Override
            public void execute(Runnable command) {
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

            @Override
            public <T> Future<T> submit(java.util.concurrent.Callable<T> task) {
                return null;
            }

            @Override
            public <T> List<Future<T>> invokeAll(Collection<? extends java.util.concurrent.Callable<T>> tasks) {
                return null;
            }

            @Override
            public <T> List<Future<T>> invokeAll(Collection<? extends java.util.concurrent.Callable<T>> tasks, long timeout, java.util.concurrent.TimeUnit unit) {
                return null;
            }

            @Override
            public <T> T invokeAny(Collection<? extends java.util.concurrent.Callable<T>> tasks) {
                return null;
            }

            @Override
            public <T> T invokeAny(Collection<? extends java.util.concurrent.Callable<T>> tasks, long timeout, java.util.concurrent.TimeUnit unit) {
                return null;
            }
        };

        final boolean[] executed = {false};
        Runnable task = () -> executed[0] = true;
        Future<?> future = executor.submit(task);
        if (future instanceof FutureTask) {
            FutureTask<?> ft = (FutureTask<?>) future;
            try {
                ft.run();
                ft.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        assert executed[0] : "Runnable task was not executed";
    }
}
