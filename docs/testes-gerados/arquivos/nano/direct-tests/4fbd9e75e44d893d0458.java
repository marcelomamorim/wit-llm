/* @test    @summary Test getActiveCount() method of ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testGetActiveCount();
    }

    public static void testGetActiveCount() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 1, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
        int initialActiveCount = executor.getActiveCount();
        assert initialActiveCount == 0 : "Expected initial active count to be 0, but was " + initialActiveCount;

        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100); // Sleep to keep thread active
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        executor.execute(task);
        Thread.sleep(50); // Wait a bit for the task to start
        int activeCountDuringTask = executor.getActiveCount();
        assert activeCountDuringTask >= 1 : "Expected active count to be at least 1 during task, but was " + activeCountDuringTask;

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        int finalActiveCount = executor.getActiveCount();
        assert finalActiveCount == 0 : "Expected final active count to be 0 after shutdown, but was " + finalActiveCount;
    }
}
