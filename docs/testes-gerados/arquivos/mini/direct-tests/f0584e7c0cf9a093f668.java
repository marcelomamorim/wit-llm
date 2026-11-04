/*
 * @test
 * @summary Test ForkJoinPool.execute(Runnable) method behavior
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest {
    public static void main(String[] args) {
        testExecuteWithNull();
        testExecuteWithForkJoinTask();
        testExecuteWithRunnable();
    }

    static void testExecuteWithNull() {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            pool.execute(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            pool.shutdown();
        }
    }

    static void testExecuteWithForkJoinTask() {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            // Create a simple ForkJoinTask subclass instance
            ForkJoinTask<?> task = new ForkJoinTask<Void>() {
                private static final long serialVersionUID = 1L;
                @Override
                public Void getRawResult() { return null; }
                @Override
                protected void setRawResult(Void value) { }
                @Override
                protected boolean exec() { return true; }
            };
            pool.execute(task);
            // Wait for task to complete
            task.join();
            if (!task.isDone()) {
                throw new Error("Task should be done after join");
            }
        } finally {
            pool.shutdown();
        }
    }

    static void testExecuteWithRunnable() {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            final boolean[] ran = new boolean[1];
            Runnable r = new Runnable() {
                public void run() {
                    ran[0] = true;
                }
            };
            pool.execute(r);
            // Wait briefly for task to run
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new Error("Interrupted during sleep");
            }
            if (!ran[0]) {
                throw new Error("Runnable task did not run");
            }
        } finally {
            pool.shutdown();
        }
    }
}
