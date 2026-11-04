/*
 * @test
 * @summary Checks observable behavior of ThreadPoolExecutor.getCompletedTaskCount
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNoTasks() throws InterruptedException {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor exec = new ThreadPoolExecutor(2, 2, 1L, TimeUnit.SECONDS, queue);
        check(exec.getCompletedTaskCount() == 0L, "initial completed count must be 0");
        exec.shutdown();
        check(exec.awaitTermination(10, TimeUnit.SECONDS), "executor did not terminate");
        check(exec.getCompletedTaskCount() == 0L, "completed count must stay 0 without tasks");
    }

    static void testCountsExecutedTasks() throws InterruptedException {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor exec = new ThreadPoolExecutor(2, 2, 1L, TimeUnit.SECONDS, queue);
        final int n = 10;
        for (int i = 0; i < n; i++) {
            exec.execute(() -> { });
        }
        exec.shutdown();
        check(exec.awaitTermination(10, TimeUnit.SECONDS), "executor did not terminate");
        long completed = exec.getCompletedTaskCount();
        check(completed == n, "expected " + n + " completed tasks but was " + completed);
        check(completed <= exec.getTaskCount(), "completed count must not exceed task count");
    }

    public static void main(String[] args) throws Exception {
        testNoTasks();
        testCountsExecutedTasks();
    }
}
