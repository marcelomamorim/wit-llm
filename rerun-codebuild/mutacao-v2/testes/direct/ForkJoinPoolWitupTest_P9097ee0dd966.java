/*
 * @test
 * @summary Test ForkJoinPool.submit(Runnable) method behavior
 * @run main ForkJoinPoolWitupTest_P9097ee0dd966
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest_P9097ee0dd966 {
    public static void main(String[] args) throws Exception {
        ForkJoinPool pool = new ForkJoinPool();

        // Test 1: submit null should throw NullPointerException
        try {
            pool.submit((Runnable) null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: submit a Runnable that is NOT a ForkJoinTask
        Runnable r = new Runnable() {
            boolean ran = false;
            public void run() {
                ran = true;
            }
        };

        ForkJoinTask<?> task = pool.submit(r);
        if (task == null) {
            throw new Error("submit returned null task");
        }

        // Wait for completion
        task.join();

        // Since we cannot access 'ran' from outside anonymous class, test by re-submitting a Runnable that sets a flag externally
        final boolean[] flag = new boolean[1];
        Runnable r2 = new Runnable() {
            public void run() {
                flag[0] = true;
            }
        };

        ForkJoinTask<?> task2 = pool.submit(r2);
        task2.join();

        if (!flag[0]) {
            throw new Error("Runnable task did not run");
        }

        // Test 3: submit a Runnable that is a ForkJoinTask (AdaptedRunnableAction is package-private, so create a ForkJoinTask directly)
        ForkJoinTask<Void> directTask = new ForkJoinTask<Void>() {
            private static final long serialVersionUID = 1L;
            protected boolean exec() {
                return true;
            }
            public Void getRawResult() { return null; }
            protected void setRawResult(Void value) {}
        };

        ForkJoinTask<?> submittedTask = pool.submit(directTask);

        if (submittedTask != directTask) {
            throw new Error("submit should return the same ForkJoinTask instance if passed one");
        }

        submittedTask.join();

        pool.shutdown();
        if (!pool.isTerminated()) {
            pool.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
        }
    }
}
