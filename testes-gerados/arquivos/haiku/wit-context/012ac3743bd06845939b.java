/*
 * @test
 * @summary WITUP-generated behavioral checks for the ExecutorCompletionService(Executor) constructor
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;

public class ExecutorCompletionServiceWitupTest {

    public static void main(String[] args) throws Exception {
        testNullExecutorThrowsNPE();
        testNonNullExecutorAcceptsTasks();
        testPollOnFreshServiceReturnsNull();
    }

    static void testNullExecutorThrowsNPE() {
        try {
            new ExecutorCompletionService<Integer>(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null executor");
    }

    static void testNonNullExecutorAcceptsTasks() throws Exception {
        Executor direct = r -> r.run();
        ExecutorCompletionService<Integer> cs = new ExecutorCompletionService<Integer>(direct);
        Callable<Integer> task = () -> 42;
        Future<Integer> submitted = cs.submit(task);
        Future<Integer> completed = cs.take();
        if (completed != submitted) {
            throw new RuntimeException("take() must return the future of the completed task");
        }
        if (completed.get().intValue() != 42) {
            throw new RuntimeException("unexpected result from completed task");
        }
        if (cs.poll() != null) {
            throw new RuntimeException("completion queue should be empty after take()");
        }
    }

    static void testPollOnFreshServiceReturnsNull() {
        Executor direct = r -> r.run();
        ExecutorCompletionService<String> cs = new ExecutorCompletionService<String>(direct);
        if (cs.poll() != null) {
            throw new RuntimeException("poll() on a fresh service must return null");
        }
    }
}
