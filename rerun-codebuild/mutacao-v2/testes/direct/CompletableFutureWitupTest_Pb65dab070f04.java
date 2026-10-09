/*
 * @test
 * @summary Test CompletableFuture.delayedExecutor behavior and exceptions
 * @run main CompletableFutureWitupTest_Pb65dab070f04
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest_Pb65dab070f04 {
    public static void main(String[] args) {
        testDelayedExecutorReturnsExecutor();
        testDelayedExecutorNullUnitThrowsNPE();
    }

    static void testDelayedExecutorReturnsExecutor() {
        Executor exec = CompletableFuture.delayedExecutor(10L, TimeUnit.MILLISECONDS);
        if (exec == null) {
            throw new AssertionError("delayedExecutor returned null");
        }
        // Check that the returned executor runs a task after delay
        final boolean[] ran = {false};
        exec.execute(new Runnable() {
            public void run() {
                ran[0] = true;
            }
        });
        // Wait briefly to allow task to run
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Interrupted during sleep");
        }
        if (!ran[0]) {
            throw new AssertionError("Executor did not run the task");
        }
    }

    static void testDelayedExecutorNullUnitThrowsNPE() {
        try {
            CompletableFuture.delayedExecutor(10L, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
