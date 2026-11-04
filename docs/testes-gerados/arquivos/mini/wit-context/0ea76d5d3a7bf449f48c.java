/*
 * @test
 * @summary Test ForkJoinPool.invoke(ForkJoinTask) for null argument and normal execution
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest {
    public static void main(String[] args) throws Exception {
        testInvokeNullTask();
        testInvokeNormalTask();
    }

    static void testInvokeNullTask() {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            pool.invoke(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testInvokeNormalTask() throws Exception {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<String> task = new ForkJoinTask<String>() {
            private static final long serialVersionUID = 1L;
            String result = "test";
            @Override
            public String getRawResult() {
                return result;
            }
            @Override
            protected void setRawResult(String value) {
                result = value;
            }
            @Override
            protected boolean exec() {
                return true;
            }
        };
        String res = pool.invoke(task);
        if (!"test".equals(res))
            throw new Error("Unexpected result from invoke: " + res);
    }
}
