/*
 * @test
 * @summary Tests ExecutorCompletionService.submit(Runnable, V): null task NPE, result propagation, and queueing of completed futures
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class ExecutorCompletionServiceWitupTest {

    private static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    public static void main(String[] args) throws Exception {
        Executor direct = Runnable::run;
        Runnable noop = () -> { };

        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(direct);
        try {
            ecs.submit(null, "x");
            fail("expected NullPointerException for null task");
        } catch (NullPointerException expected) {
        }

        Future<String> f = ecs.submit(noop, "v");
        if (!f.isDone()) {
            fail("future should be done with direct executor");
        }
        Future<String> taken = ecs.take();
        if (taken != f) {
            fail("take() should return the submitted future");
        }
        if (!"v".equals(taken.get())) {
            fail("get() should return the supplied result");
        }

        LinkedBlockingQueue<Future<String>> queue = new LinkedBlockingQueue<>();
        ExecutorCompletionService<String> ecs2 = new ExecutorCompletionService<>(direct, queue);
        Future<String> f2 = ecs2.submit(noop, "w");
        if (queue.poll() != f2) {
            fail("completed future should be enqueued in the supplied queue");
        }
        if (!"w".equals(f2.get())) {
            fail("get() should return the supplied result from custom queue service");
        }
    }
}
