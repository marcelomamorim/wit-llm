/*
 * @test
 * @summary Test for shutdownNow method in ScheduledThreadPoolExecutor
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.List;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testShutdownNowReturnsList();
    }

    public static void testShutdownNowReturnsList() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        List<Runnable> result = executor.shutdownNow();
        if (result == null) {
            throw new AssertionError("shutdownNow() returned null");
        }
        System.out.println("Test passed: shutdownNow() returned a non-null list.");
    }
}
