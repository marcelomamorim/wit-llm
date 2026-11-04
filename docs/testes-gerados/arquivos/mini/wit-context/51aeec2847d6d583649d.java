/*
 * @test
 * @summary Test ThreadPoolExecutor.shutdown() for expected UnsupportedOperationException and normal behavior
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        testShutdownThrowsUnsupportedOperationException();
    }

    private static void testShutdownThrowsUnsupportedOperationException() {
        // Create a ThreadPoolExecutor with minimal configuration
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, queue);

        boolean unsupportedOperationThrown = false;
        try {
            executor.shutdown();
        } catch (UnsupportedOperationException e) {
            unsupportedOperationThrown = true;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }

        if (!unsupportedOperationThrown) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown by shutdown()");
        }
    }
}
