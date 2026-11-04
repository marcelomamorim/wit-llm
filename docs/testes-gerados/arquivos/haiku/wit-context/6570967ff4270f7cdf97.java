/*
 * @test
 * @summary WITUP-derived tests for ExecutorCompletionService.submit(Callable) null-task handling and completion queueing
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class ExecutorCompletionServiceWitupTest {

    public static void main(String[] args) throws Exception {
        testNullTaskThrowsNPE();
        testSubmitReturnsFutureAndQueuesCompletedTask();
        testCustomQueueReceivesCompletedFuture();
    }

    static void testNullTaskThrowsNPE() {
        Executor direct = r -> r.run();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(direct);
        try {
            ecs.submit((Callable<String>) null);
            throw new AssertionError("expected NullPointerException for null task");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testSubmitReturnsFutureAndQueuesCompletedTask() throws Exception {
        Executor direct = r -> r.run();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(direct);
        Future<String> f = ecs.submit(() -> "ok");
        if (f == null) {
            throw new AssertionError("submit returned null");
        }
        if (!f.isDone()) {
            throw new AssertionError("future should be done with direct executor");
        }
        Future<String> taken = ecs.take();
        if (taken != f) {
            throw new AssertionError("take() returned a different future");
        }
        if (!"ok".equals(taken.get())) {
            throw new AssertionError("unexpected result: " + taken.get());
        }
    }

    static void testCustomQueueReceivesCompletedFuture() throws Exception {
        Executor direct = r -> r.run();
        BlockingQueue<Future<String>> queue = new LinkedBlockingQueue<>();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(direct, queue);
        Future<String> f = ecs.submit(() -> "custom");
        Future<String> polled = queue.poll();
        if (polled != f) {
            throw new AssertionError("custom queue did not receive the submitted future");
        }
        if (!"custom".equals(polled.get())) {
            throw new AssertionError("unexpected result from custom queue");
        }
    }
}
