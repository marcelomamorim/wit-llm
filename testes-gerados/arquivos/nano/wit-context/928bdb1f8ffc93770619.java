/*
 * @test
 * @summary Test shutdown() method of ScheduledThreadPoolExecutor for normal behavior.
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testShutdownNormal();
    }

    public static void testShutdownNormal() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.shutdown();
        boolean isTerminated = false;
        try {
            isTerminated = executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // Ignore for test
        }
        if (!isTerminated) {
            System.err.println("Test failed: executor did not terminate after shutdown.");
            System.exit(1);
        }
        System.out.println("Test passed: shutdown() terminates executor.");
    }
}
