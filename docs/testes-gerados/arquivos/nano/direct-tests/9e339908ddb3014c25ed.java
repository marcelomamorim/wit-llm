/*
 * @test    @summary Test shutdownNow method of ScheduledThreadPoolExecutor
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.List;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testShutdownNowReturnsList();
    }

    public static void testShutdownNowReturnsList() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        List<Runnable> remainingTasks = executor.shutdownNow();
        if (remainingTasks == null) {
            throw new AssertionError("shutdownNow() returned null");
        }
        System.out.println("shutdownNow() returned a list with size: " + remainingTasks.size());
    }
}
