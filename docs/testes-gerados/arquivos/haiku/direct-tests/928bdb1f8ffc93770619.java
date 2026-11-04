/*
 * @test
 * @summary Verifies observable behavior of ScheduledThreadPoolExecutor.shutdown()
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testShutdownStateAndTermination() throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        check(!ex.isShutdown(), "executor must not be shutdown before shutdown()");
        ex.shutdown();
        check(ex.isShutdown(), "isShutdown() must be true after shutdown()");
        check(ex.awaitTermination(10, TimeUnit.SECONDS), "executor must terminate after shutdown()");
        check(ex.isTerminated(), "isTerminated() must be true after termination");
    }

    static void testDelayedTaskRunsAfterShutdownByDefault() throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        check(ex.getExecuteExistingDelayedTasksAfterShutdownPolicy(),
              "default policy must execute existing delayed tasks after shutdown");
        ScheduledFuture<Integer> f = ex.schedule(() -> 42, 100, TimeUnit.MILLISECONDS);
        ex.shutdown();
        check(f.get(10, TimeUnit.SECONDS) == 42,
              "delayed task must still run after shutdown() under default policy");
        check(ex.awaitTermination(10, TimeUnit.SECONDS), "executor must terminate");
    }

    static void testDelayedTaskCancelledWhenPolicyDisabled() throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        ex.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        ScheduledFuture<Integer> f = ex.schedule(() -> 7, 1, TimeUnit.HOURS);
        ex.shutdown();
        check(f.isCancelled(),
              "delayed task must be cancelled by shutdown() when policy is false");
        check(ex.awaitTermination(10, TimeUnit.SECONDS), "executor must terminate");
    }

    static void testShutdownIsIdempotent() throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(2);
        ex.shutdown();
        ex.shutdown();
        check(ex.isShutdown(), "isShutdown() must remain true after repeated shutdown()");
        check(ex.awaitTermination(10, TimeUnit.SECONDS), "executor must terminate");
    }

    public static void main(String[] args) throws Exception {
        testShutdownStateAndTermination();
        testDelayedTaskRunsAfterShutdownByDefault();
        testDelayedTaskCancelledWhenPolicyDisabled();
        testShutdownIsIdempotent();
    }
}
