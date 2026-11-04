/*
 * @test
 * @summary Black-box checks of ExecutorCompletionService.submit(Runnable, V) using a synchronous executor
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorCompletionServiceWitupTest {

    public static void main(String[] args) throws Exception {
        testNullTaskThrowsNPE();
        testSubmitRunsTaskAndQueuesFuture();
        testNullResultIsReturned();
        testCustomQueueReceivesFuture();
        testRejectedExecutionPropagates();
    }

    static void check(boolean condition, String msg) {
        if (!condition) throw new RuntimeException(msg);
    }

    static void testNullTaskThrowsNPE() {
        ExecutorCompletionService<String> ecs =
            new ExecutorCompletionService<>(Runnable::run);
        try {
            ecs.submit(null, "x");
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testSubmitRunsTaskAndQueuesFuture() throws Exception {
        AtomicInteger runs = new AtomicInteger();
        ExecutorCompletionService<String> ecs =
            new ExecutorCompletionService<>(Runnable::run);
        Future<String> f = ecs.submit(() -> { runs.incrementAndGet(); }, "done");
        check(runs.get() == 1, "task should run exactly once");
        check(f.isDone(), "future should be done");
        check("done".equals(f.get()), "future should return supplied result");
        Future<String> taken = ecs.take();
        check(taken == f, "take() should return the submitted future");
        check(ecs.poll() == null, "queue should be empty after take");
    }

    static void testNullResultIsReturned() throws Exception {
        ExecutorCompletionService<String> ecs =
            new ExecutorCompletionService<>(Runnable::run);
        Future<String> f = ecs.submit(() -> { }, null);
        check(f.get() == null, "null result should be returned");
        check(ecs.take() == f, "future with null result should be queued");
    }

    static void testCustomQueueReceivesFuture() throws Exception {
        BlockingQueue<Future<String>> q = new LinkedBlockingQueue<>();
        ExecutorCompletionService<String> ecs =
            new ExecutorCompletionService<>(Runnable::run, q);
        Future<String> f = ecs.submit(() -> { }, "v");
        check(q.size() == 1, "custom queue should contain one future");
        check(q.peek() == f, "custom queue should contain submitted future");
        check("v".equals(f.get()), "result should be v");
    }

    static void testRejectedExecutionPropagates() {
        Executor rejecting = r -> { throw new RejectedExecutionException(); };
        ExecutorCompletionService<String> ecs =
            new ExecutorCompletionService<>(rejecting);
        try {
            ecs.submit(() -> { }, "x");
            throw new RuntimeException("expected RejectedExecutionException");
        } catch (RejectedExecutionException expected) {
            // expected
        }
    }
}
