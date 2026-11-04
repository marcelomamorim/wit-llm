/*
 * @test
 * @summary Test ForkJoinTask.adapt(Runnable, T) creates a task that runs the runnable and returns the given result
 * @run main ForkJoinTaskWitupTest
 */

import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {
    public static void main(String[] args) throws Exception {
        // Test 1: adapt with a runnable that sets a flag and returns a result
        final boolean[] ran = {false};
        Runnable r = new Runnable() {
            public void run() {
                ran[0] = true;
            }
        };
        String expectedResult = "done";
        ForkJoinTask<String> task = ForkJoinTask.adapt(r, expectedResult);

        // Before running, task should not be done
        if (task.isDone()) {
            throw new Error("Task should not be done before execution");
        }

        // Fork and join the task to execute it
        task.fork();
        String result = task.join();

        // The runnable should have run
        if (!ran[0]) {
            throw new Error("Runnable was not executed");
        }

        // The result should be the expected one
        if (!expectedResult.equals(result)) {
            throw new Error("Expected result '" + expectedResult + "' but got '" + result + "'");
        }

        // Test 2: adapt with a runnable that does nothing and null result
        Runnable noOp = new Runnable() {
            public void run() {
                // do nothing
            }
        };
        ForkJoinTask<Void> task2 = ForkJoinTask.adapt(noOp, null);
        task2.fork();
        Void res2 = task2.join();
        if (res2 != null) {
            throw new Error("Expected null result but got non-null");
        }

        // Test 3: adapt with a runnable that throws RuntimeException
        Runnable throwing = new Runnable() {
            public void run() {
                throw new RuntimeException("fail");
            }
        };
        ForkJoinTask<Object> task3 = ForkJoinTask.adapt(throwing, null);
        task3.fork();
        try {
            task3.join();
            throw new Error("Expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            if (!"fail".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
