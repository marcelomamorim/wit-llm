/*
 * @test
 * @summary Test the get() method of FutureTask for normal completion and interruption.
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.FutureTask;
import java.util.concurrent.ExecutionException;

public class FutureTaskWitupTest {

    public static void main(String[] args) throws Exception {
        testGetReturnsResult();
        testGetInterrupted();
    }

    static void testGetReturnsResult() throws Exception {
        // Create a FutureTask with a dummy callable that completes immediately
        FutureTask<String> task = new FutureTask<>(() -> "result");
        Thread t = new Thread(task);
        t.start();
        String result = task.get();
        assert "result".equals(result) : "Expected 'result', got " + result;
        t.join();
    }

    static void testGetInterrupted() throws Exception {
        // Create a FutureTask that waits indefinitely
        FutureTask<String> task = new FutureTask<>(() -> {
            Thread.sleep(10000);
            return "done";
        });
        Thread t = new Thread(task);
        t.start();
        // Interrupt the thread while get() is blocking
        Thread interrupter = new Thread(() -> {
            try {
                Thread.sleep(100);
                t.interrupt();
            } catch (InterruptedException e) {
                // ignore
            }
        });
        interrupter.start();
        try {
            task.get();
            assert false : "Expected InterruptedException";
        } catch (InterruptedException e) {
            // expected
        }
        t.join();
        interrupter.join();
    }
}
