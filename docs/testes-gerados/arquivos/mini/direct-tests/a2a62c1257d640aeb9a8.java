/*
 * @test
 * @summary Test java.util.concurrent.Executors.newCachedThreadPool basic behavior
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = java.util.concurrent.Executors.newCachedThreadPool();
        if (!(executor instanceof ThreadPoolExecutor)) {
            throw new RuntimeException("Returned ExecutorService is not a ThreadPoolExecutor");
        }
        ThreadPoolExecutor tpe = (ThreadPoolExecutor) executor;

        // Check core pool size is 0
        if (tpe.getCorePoolSize() != 0) {
            throw new RuntimeException("Core pool size expected 0 but was " + tpe.getCorePoolSize());
        }

        // Check maximum pool size is Integer.MAX_VALUE
        if (tpe.getMaximumPoolSize() != Integer.MAX_VALUE) {
            throw new RuntimeException("Maximum pool size expected Integer.MAX_VALUE but was " + tpe.getMaximumPoolSize());
        }

        // Check keep alive time is 60 seconds
        long keepAlive = tpe.getKeepAliveTime(TimeUnit.SECONDS);
        if (keepAlive != 60L) {
            throw new RuntimeException("Keep alive time expected 60 seconds but was " + keepAlive);
        }

        // Check work queue is a SynchronousQueue
        if (!(tpe.getQueue() instanceof SynchronousQueue)) {
            throw new RuntimeException("Work queue is not a SynchronousQueue");
        }

        // Submit a simple task and check result
        Future<String> future = executor.submit(new java.util.concurrent.Callable<String>() {
            public String call() {
                return "test";
            }
        });
        String result = future.get();
        if (!"test".equals(result)) {
            throw new RuntimeException("Task returned unexpected result: " + result);
        }

        // Shutdown and await termination
        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Executor did not terminate in time");
        }
    }
}
