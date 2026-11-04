/*
 * @test
 * @summary Black-box checks of ExecutorCompletionService.submit(Callable) using public API only
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class ExecutorCompletionServiceWitupTest {

    public static void main(String[] args) throws Exception {
        testNullTaskThrowsNPE();
        testSynchronousCompletion();
        testDeferredExecution();
        testExceptionalTask();
        testCustomQueue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullTaskThrowsNPE() {
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(Runnable::run);
        try {
            ecs.submit(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testSynchronousCompletion() throws Exception {
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(Runnable::run);
        Future<String> f = ecs.submit(() -> "x");
        Future<String> t = ecs.take();
        check(f == t, "take() should return the submitted future");
        check("x".equals(t.get()), "unexpected result");
    }

    static void testDeferredExecution() throws Exception {
        final List<Runnable> pending = new ArrayList<>();
        Executor e = pending::add;
        ExecutorCompletionService<Integer> ecs = new ExecutorCompletionService<>(e);
        Future<Integer> f = ecs.submit(() -> 42);
        check(pending.size() == 1, "executor should receive one runnable");
        check(ecs.poll() == null, "future must not be queued before running");
        check(!f.isDone(), "future must not be done before running");
        pending.get(0).run();
        Future<Integer> p = ecs.poll();
        check(p == f, "poll() should return the completed future");
        check(f.get() == 42, "unexpected result");
    }

    static void testExceptionalTask() throws Exception {
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(Runnable::run);
        Future<String> f = ecs.submit(() -> { throw new IllegalStateException("boom"); });
        Future<String> t = ecs.take();
        check(t == f, "take() should return the failed future");
        try {
            t.get();
            throw new AssertionError("expected ExecutionException");
        } catch (ExecutionException ee) {
            check(ee.getCause() instanceof IllegalStateException, "wrong cause type");
        }
    }

    static void testCustomQueue() throws Exception {
        BlockingQueue<Future<String>> q = new LinkedBlockingQueue<>();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(Runnable::run, q);
        Future<String> f = ecs.submit(() -> "y");
        check(q.size() == 1, "custom queue should hold one future");
        check(q.peek() == f, "custom queue should hold the submitted future");
    }
}
