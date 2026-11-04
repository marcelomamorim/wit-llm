/*
 * @test
 * @summary Basic observable behavior of the ExecutorCompletionService(Executor) constructor
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;

public class ExecutorCompletionServiceWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullExecutorThrowsNPE() {
        boolean thrown = false;
        try {
            new ExecutorCompletionService<String>(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null executor");
    }

    static void testSubmittedTaskIsQueuedOnCompletion() throws Exception {
        Executor direct = r -> r.run();
        CompletionService<String> cs = new ExecutorCompletionService<String>(direct);
        cs.submit(() -> "done");
        Future<String> f = cs.take();
        check("done".equals(f.get()), "unexpected result from completed task");
    }

    static void testCustomExecutorIsUsed() throws Exception {
        int[] runs = new int[1];
        Executor counting = r -> {
            runs[0]++;
            r.run();
        };
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(counting);
        cs.submit(() -> 42);
        check(runs[0] == 1, "executor should run the submitted task exactly once");
        check(cs.take().get() == 42, "unexpected result from custom executor");
    }

    static void testCompletionOrderPreserved() throws Exception {
        Executor direct = r -> r.run();
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(direct);
        cs.submit(() -> 1);
        cs.submit(() -> 2);
        check(cs.take().get() == 1, "first completed task should be taken first");
        check(cs.take().get() == 2, "second completed task should be taken second");
    }

    public static void main(String[] args) throws Exception {
        testNullExecutorThrowsNPE();
        testSubmittedTaskIsQueuedOnCompletion();
        testCustomExecutorIsUsed();
        testCompletionOrderPreserved();
    }
}
