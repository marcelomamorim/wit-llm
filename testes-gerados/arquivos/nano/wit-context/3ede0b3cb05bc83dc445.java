/* @test    @summary Test for AbstractExecutorService.submit handling null task    @run main AbstractExecutorServiceWitupTest */

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

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
            public <T> Future<T> submit(Callable<T> task) {
                // Call the real method
                return super.submit(task);
            }
        };
        try {
            executor.submit(null);
            // If no exception, test fails
            System.err.println("Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
