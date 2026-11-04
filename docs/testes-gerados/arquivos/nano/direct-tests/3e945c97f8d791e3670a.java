/* @test    @summary Test shutdownNow method of ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest */

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ReentrantLock;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testShutdownNowReturnsTasks();
        testShutdownNowLocksAndUnlocks();
    }

    private static void testShutdownNowReturnsTasks() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, null, null);
        Runnable task1 = new Runnable() {
            public void run() {}
        };
        Runnable task2 = new Runnable() {
            public void run() {}
        };
        executor.execute(task1);
        executor.execute(task2);
        List<Runnable> tasks = executor.shutdownNow();
        assert tasks != null : "shutdownNow should return a list";
        assert tasks.contains(task1) || tasks.contains(task2) : "Returned list should contain submitted tasks";
    }

    private static void testShutdownNowLocksAndUnlocks() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, null, null);
        ReentrantLock lock = new ReentrantLock();
        // Lock the mainLock before calling shutdownNow to test locking behavior
        executor.mainLock.lock();
        try {
            // Call shutdownNow while lock is held to ensure it blocks or handles correctly
            new Thread(() -> {
                try {
                    List<Runnable> tasks = executor.shutdownNow();
                    assert tasks != null : "shutdownNow should return a list";
                } catch (Exception e) {
                    assert false : "shutdownNow threw an exception: " + e;
                }
            }).start();
        } finally {
            executor.mainLock.unlock();
        }
        // Wait briefly to allow thread to complete
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
