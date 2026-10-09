/*
 * @test
 * @summary Test ForkJoinPool.execute(Runnable) for null input and normal execution
 * @run main ForkJoinPoolWitupTest_P782a2ec6609f
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest_P782a2ec6609f {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        // Test that execute(null) throws NullPointerException
        try {
            pool.execute(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test that execute with a Runnable that is a ForkJoinTask does not wrap
        ForkJoinTask<?> task = new ForkJoinTask<Void>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Void getRawResult() { return null; }
            @Override
            protected void setRawResult(Void value) { }
            @Override
            protected boolean exec() { return true; }
        };

        pool.execute(task); // should accept without exception

        // Test that execute with a normal Runnable wraps into RunnableExecuteAction
        Runnable normalRunnable = new Runnable() {
            public void run() { /* no-op */ }
        };

        pool.execute(normalRunnable); // should accept without exception

        // No direct observable effect to assert, but no exceptions means pass
    }
}
