/*
 * @test
 * @summary Test ForkJoinPool.submit(Runnable) for null argument and normal usage
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest {
    public static void main(String[] args) throws Exception {
        ForkJoinPool pool = new ForkJoinPool();

        // Test that submit(null) throws NullPointerException
        boolean npeThrown = false;
        try {
            pool.submit((Runnable) null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when submitting null task");
        }

        // Test submitting a simple Runnable returns a non-null ForkJoinTask
        Runnable r = new Runnable() {
            public void run() {
                // no-op
            }
        };
        ForkJoinTask<?> task = pool.submit(r);
        if (task == null) {
            throw new AssertionError("submit(Runnable) returned null task");
        }

        // Test submitting a ForkJoinTask returns the same instance
        ForkJoinTask<Void> fjt = new ForkJoinTask<Void>() {
            private static final long serialVersionUID = 1L;
            protected boolean exec() { return true; }
            public Void getRawResult() { return null; }
            protected void setRawResult(Void value) {}
        };
        ForkJoinTask<?> submitted = pool.submit(fjt);
        if (submitted != fjt) {
            throw new AssertionError("submit(ForkJoinTask) did not return the same instance");
        }

        pool.shutdown();
        if (!pool.isShutdown()) {
            throw new AssertionError("Pool should be shutdown");
        }
    }
}
