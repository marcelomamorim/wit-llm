/*
 * @test
 * @summary Test for ScheduledThreadPoolExecutor.submit method
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testSubmitWithNullTask();
    }

    public static void testSubmitWithNullTask() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            executor.submit((Callable<Object>) null);
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException");
            return;
        }
        throw new AssertionError("Expected NullPointerException was not thrown");
    }
}
