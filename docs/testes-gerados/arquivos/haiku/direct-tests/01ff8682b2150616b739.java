/*
 * @test
 * @summary Tests Executors.callable(Runnable): NullPointerException for null task, and call() runs the task and returns null
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullTaskThrowsNPE();
        testCallRunsTaskAndReturnsNull();
        testEachCallInvokesTaskAgain();
    }

    static void testNullTaskThrowsNPE() {
        boolean thrown = false;
        try {
            Executors.callable(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null task");
    }

    static void testCallRunsTaskAndReturnsNull() throws Exception {
        AtomicInteger counter = new AtomicInteger();
        Runnable r = () -> counter.incrementAndGet();
        Callable<Object> c = Executors.callable(r);
        check(c != null, "callable must not return null");
        Object result = c.call();
        check(counter.get() == 1, "task should have run exactly once");
        check(result == null, "result of call() should be null");
    }

    static void testEachCallInvokesTaskAgain() throws Exception {
        AtomicInteger counter = new AtomicInteger();
        Runnable r = () -> counter.incrementAndGet();
        Callable<Object> c = Executors.callable(r);
        c.call();
        c.call();
        check(counter.get() == 2, "each call() should run the task again");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
