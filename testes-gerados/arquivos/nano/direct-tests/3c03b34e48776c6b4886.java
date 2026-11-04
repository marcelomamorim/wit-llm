/* @test    @summary Test execute method of ScheduledThreadPoolExecutor for immediate execution.    @run main ScheduledThreadPoolExecutorWitupTest */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testExecuteWithRunnable();
    }

    public static void testExecuteWithRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        final boolean[] executed = {false};
        Runnable task = new Runnable() {
            @Override
            public void run() {
                executed[0] = true;
            }
        };
        executor.execute(task);
        try {
            Thread.sleep(50); // Wait briefly to allow task to run
        } catch (InterruptedException e) {
            // Ignore
        }
        assert executed[0] : "Runnable task was not executed";
        executor.shutdown();
    }
}
