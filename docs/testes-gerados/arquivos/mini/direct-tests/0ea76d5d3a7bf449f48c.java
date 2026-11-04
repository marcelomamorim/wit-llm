/*
 * @test
 * @summary Test ForkJoinPool.invoke(ForkJoinTask) for normal and exceptional cases
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest {
    public static void main(String[] args) throws Exception {
        testInvokeWithNullTask();
        testInvokeWithSimpleTask();
    }

    static void testInvokeWithNullTask() {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            pool.invoke(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            pool.shutdown();
        }
    }

    static void testInvokeWithSimpleTask() {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            ForkJoinTask<Integer> task = new ForkJoinTask<Integer>() {
                private static final long serialVersionUID = 1L;
                private int result;

                @Override
                public Integer getRawResult() {
                    return result;
                }

                @Override
                protected void setRawResult(Integer value) {
                    result = value;
                }

                @Override
                protected boolean exec() {
                    setRawResult(42);
                    return true;
                }
            };

            Integer res = pool.invoke(task);
            if (res == null || res.intValue() != 42) {
                throw new Error("Unexpected result from invoke: " + res);
            }
        } finally {
            pool.shutdown();
        }
    }
}
