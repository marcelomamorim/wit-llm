/*
 * @test
 * @summary Tests Executors.unconfigurableExecutorService null handling, delegation, and hidden implementation-specific API
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNullPointerException();
        testReturnsNewWrapperThatDelegatesSubmit();
        testWrapperHidesThreadPoolExecutorType();
        testShutdownPropagatesToDelegate();
        System.out.println("PASSED");
    }

    static void testNullThrowsNullPointerException() {
        try {
            Executors.unconfigurableExecutorService(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReturnsNewWrapperThatDelegatesSubmit() throws Exception {
        ExecutorService delegate = Executors.newFixedThreadPool(1);
        try {
            ExecutorService wrapper = Executors.unconfigurableExecutorService(delegate);
            if (wrapper == delegate) {
                throw new AssertionError("Wrapper must be a distinct object");
            }
            Future<Integer> f = wrapper.submit(new Callable<Integer>() {
                public Integer call() {
                    return 42;
                }
            });
            Integer result = f.get();
            if (result.intValue() != 42) {
                throw new AssertionError("Unexpected result: " + result);
            }
        } finally {
            delegate.shutdown();
        }
    }

    static void testWrapperHidesThreadPoolExecutorType() {
        ExecutorService delegate = Executors.newFixedThreadPool(1);
        try {
            ExecutorService wrapper = Executors.unconfigurableExecutorService(delegate);
            if (wrapper instanceof ThreadPoolExecutor) {
                throw new AssertionError("Wrapper must not expose ThreadPoolExecutor");
            }
        } finally {
            delegate.shutdown();
        }
    }

    static void testShutdownPropagatesToDelegate() {
        ExecutorService delegate = Executors.newFixedThreadPool(1);
        ExecutorService wrapper = Executors.unconfigurableExecutorService(delegate);
        if (wrapper.isShutdown() || delegate.isShutdown()) {
            throw new AssertionError("Executor should not start shut down");
        }
        wrapper.shutdown();
        if (!wrapper.isShutdown() || !delegate.isShutdown()) {
            throw new AssertionError("Shutdown was not propagated to delegate");
        }
    }
}
