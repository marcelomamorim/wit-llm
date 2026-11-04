/*
 * @test
 * @summary Test ThreadPoolExecutor.shutdownNow() for expected behavior and exception paths
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        testShutdownNowReturnsPendingTasks();
        testShutdownNowThrowsIfSecurityManagerDenies();
    }

    private static void testShutdownNowReturnsPendingTasks() {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, queue);

        // Add a task that will remain in the queue
        Runnable task = new Runnable() {
            public void run() {
                // no-op
            }
        };
        executor.execute(task);

        // ShutdownNow should return the queued task
        List<Runnable> pending = executor.shutdownNow();

        if (!pending.contains(task)) {
            throw new AssertionError("shutdownNow() did not return the queued task");
        }

        // After shutdownNow, executor should be shutdown
        if (!executor.isShutdown()) {
            throw new AssertionError("Executor not shutdown after shutdownNow()");
        }
    }

    private static void testShutdownNowThrowsIfSecurityManagerDenies() {
        // This test attempts to verify that shutdownNow() throws SecurityException if security manager denies
        // Since no security manager is installed by default, we simulate by installing a custom one
        SecurityManager original = System.getSecurityManager();
        SecurityManager denyShutdownManager = new SecurityManager() {
            @Override
            public void checkPermission(java.security.Permission perm) {
                if ("modifyThread".equals(perm.getName())) {
                    throw new SecurityException("deny modifyThread");
                }
            }
        };

        System.setSecurityManager(denyShutdownManager);

        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, queue);

        boolean thrown = false;
        try {
            executor.shutdownNow();
        } catch (SecurityException se) {
            thrown = true;
        } finally {
            System.setSecurityManager(original);
            executor.shutdownNow(); // cleanup
        }

        if (!thrown) {
            throw new AssertionError("Expected SecurityException was not thrown by shutdownNow()");
        }
    }
}
