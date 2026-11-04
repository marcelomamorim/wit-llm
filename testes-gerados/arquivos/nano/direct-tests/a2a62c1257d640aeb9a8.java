/* @test    @summary Test Executors.newCachedThreadPool() returns a ThreadPoolExecutor with expected configuration    @run main ExecutorsWitupTest */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.SynchronousQueue;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testNewCachedThreadPool();
    }

    public static void testNewCachedThreadPool() {
        ExecutorService executor = java.util.concurrent.Executors.newCachedThreadPool();
        if (!(executor instanceof ThreadPoolExecutor)) {
            throw new AssertionError("Executor is not an instance of ThreadPoolExecutor");
        }
        ThreadPoolExecutor tpe = (ThreadPoolExecutor) executor;
        // Check corePoolSize
        if (tpe.getCorePoolSize() != 0) {
            throw new AssertionError("Expected corePoolSize 0, but got " + tpe.getCorePoolSize());
        }
        // Check maximumPoolSize
        if (tpe.getMaximumPoolSize() != Integer.MAX_VALUE) {
            throw new AssertionError("Expected maximumPoolSize " + Integer.MAX_VALUE + ", but got " + tpe.getMaximumPoolSize());
        }
        // Check keepAliveTime
        if (tpe.getKeepAliveTime(TimeUnit.SECONDS) != 60L) {
            throw new AssertionError("Expected keepAliveTime 60 seconds, but got " + tpe.getKeepAliveTime(TimeUnit.SECONDS));
        }
        // Check workQueue type
        if (!(tpe.getQueue() instanceof SynchronousQueue)) {
            throw new AssertionError("Expected workQueue to be SynchronousQueue, but got " + tpe.getQueue().getClass());
        }
        // Check that the executor is not shutdown initially
        if (tpe.isShutdown()) {
            throw new AssertionError("Executor should not be shutdown initially");
        }
        // Optionally, submit a task and verify execution
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                throw new AssertionError("Executor did not terminate in time");
            }
        } catch (InterruptedException e) {
            throw new AssertionError("Interrupted while awaiting termination", e);
        }
    }
}
