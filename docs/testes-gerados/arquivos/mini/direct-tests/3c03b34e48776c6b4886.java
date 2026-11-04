/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.execute(Runnable) schedules task with zero delay
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        final AtomicBoolean ran = new AtomicBoolean(false);

        Runnable task = new Runnable() {
            public void run() {
                ran.set(true);
            }
        };

        executor.execute(task);

        // Wait up to 1 second for the task to run
        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            throw new RuntimeException("Executor did not terminate in time");
        }

        if (!ran.get()) {
            throw new RuntimeException("Task did not run as expected");
        }
    }
}
