/*
 * @test
 * @summary Test for AbstractExecutorService.submit(Runnable, T) method
 * @run main AbstractExecutorServiceWitupTest
 */

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;

public class AbstractExecutorServiceWitupTest {

    public static void main(String[] args) {
        testSubmitWithNullTask();
        testSubmitWithValidTask();
    }

    private static void testSubmitWithNullTask() {
        AbstractExecutorService executor = new DummyExecutor();
        try {
            executor.submit(null, "result");
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testSubmitWithValidTask() {
        AbstractExecutorService executor = new DummyExecutor();
        Runnable dummyRunnable = new Runnable() {
            public void run() {
                // do nothing
            }
        };
        String expectedResult = "done";
        Future<String> future = executor.submit(dummyRunnable, expectedResult);
        if (future != null && future.isDone()) {
            try {
                Object result = future.get();
                if (expectedResult.equals(result)) {
                    System.out.println("PASS: submit with valid task returns expected result");
                } else {
                    System.out.println("FAIL: Result mismatch: " + result);
                }
            } catch (Exception e) {
                System.out.println("FAIL: Exception while getting result: " + e);
            }
        } else {
            System.out.println("FAIL: Future is null or not done");
        }
    }

    static class DummyExecutor extends AbstractExecutorService {
        public void execute(Runnable command) {
            command.run();
        }
        public boolean isShutdown() { return false; }
        public boolean isTerminated() { return false; }
        public void shutdown() {}
        public List<Runnable> shutdownNow() { return new ArrayList<Runnable>(); }
    }
}
