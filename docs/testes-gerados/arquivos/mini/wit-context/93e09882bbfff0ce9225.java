/*
 * @test
 * @summary Test ForkJoinTask.adapt(Callable) for null argument and normal usage
 * @run main ForkJoinTaskWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {
    public static void main(String[] args) throws Exception {
        testAdaptNullCallable();
        testAdaptNonNullCallable();
    }

    static void testAdaptNullCallable() {
        try {
            ForkJoinTask.adapt(null);
            throw new Error("Expected NullPointerException for null callable");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testAdaptNonNullCallable() throws Exception {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                return "test";
            }
        };
        ForkJoinTask<String> task = ForkJoinTask.adapt(callable);
        if (task == null) {
            throw new Error("adapt returned null task");
        }
        // The task should complete and return the callable's result
        task.fork();
        String result = task.join();
        if (!"test".equals(result)) {
            throw new Error("Expected result 'test' but got: " + result);
        }
    }
}
