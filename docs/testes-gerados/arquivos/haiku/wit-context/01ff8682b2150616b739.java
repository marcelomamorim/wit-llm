/*
 * @test
 * @summary Tests Executors.callable(Runnable) null rejection and adapter behavior
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullTaskThrowsNPE();
        testCallableRunsTaskAndReturnsNull();
        testCallableInvocationRunsTaskEachTime();
    }

    static void testNullTaskThrowsNPE() {
        try {
            Executors.callable(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null task");
    }

    static void testCallableRunsTaskAndReturnsNull() throws Exception {
        int[] counter = {0};
        Runnable task = () -> counter[0]++;
        Callable<Object> callable = Executors.callable(task);
        if (callable == null) {
            throw new RuntimeException("callable returned null");
        }
        Object result = callable.call();
        if (result != null) {
            throw new RuntimeException("Expected null result, got: " + result);
        }
        if (counter[0] != 1) {
            throw new RuntimeException("Expected task to run once, runs=" + counter[0]);
        }
    }

    static void testCallableInvocationRunsTaskEachTime() throws Exception {
        int[] counter = {0};
        Runnable task = () -> counter[0]++;
        Callable<Object> callable = Executors.callable(task);
        callable.call();
        callable.call();
        if (counter[0] != 2) {
            throw new RuntimeException("Expected task to run twice, runs=" + counter[0]);
        }
    }
}
