/*
 * @test
 * @summary Verify Executors.unconfigurableExecutorService rejects null and returns a non-configurable delegating wrapper
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
        testWrapsAndDelegates();
        System.out.println("PASS");
    }

    static void testNullThrowsNullPointerException() {
        try {
            Executors.unconfigurableExecutorService(null);
            throw new AssertionError("expected NullPointerException for null executor");
        } catch (NullPointerException expected) {
        }
    }

    static void testWrapsAndDelegates() throws Exception {
        ExecutorService inner = Executors.newSingleThreadExecutor();
        try {
            ExecutorService wrapped = Executors.unconfigurableExecutorService(inner);
            if (wrapped == inner) {
                throw new AssertionError("wrapper must not be the same instance as the input");
            }
            if (wrapped instanceof ThreadPoolExecutor) {
                throw new AssertionError("wrapper must not expose the configurable pool type");
            }
            Future<Integer> f = wrapped.submit((Callable<Integer>) () -> 42);
            if (f.get().intValue() != 42) {
                throw new AssertionError("delegated submit returned wrong result");
            }
            inner.shutdown();
            if (!wrapped.isShutdown()) {
                throw new AssertionError("wrapper should reflect delegate shutdown state");
            }
        } finally {
            inner.shutdownNow();
        }
    }
}
