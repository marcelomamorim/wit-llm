/* @test    @summary Test submit method of AbstractExecutorService for null input    @run main AbstractExecutorServiceWitupTest */

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;

public class AbstractExecutorServiceWitupTest {

    public static void main(String[] args) {
        testSubmitWithNullTask();
    }

    public static void testSubmitWithNullTask() {
        AbstractExecutorService executor = new AbstractExecutorService() {
            @Override
            public void execute(Runnable command) {
                // No-op
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
                // No-op
            }

            @Override
            public List<Runnable> shutdownNow() {
                return new ArrayList<>();
            }

            @Override
            public <T> Future<T> submit(java.util.concurrent.Callable<T> task) {
                return null;
            }
        };
        try {
            executor.submit(null);
            // If no exception, test fails
            System.err.println("Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for null task.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
