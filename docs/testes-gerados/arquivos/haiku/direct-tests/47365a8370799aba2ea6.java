/*
 * @test
 * @summary Tests Executors.privilegedCallableUsingCurrentClassLoader null check, result propagation and context class loader
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCallableThrowsNPE();
        testReturnsWrappedResult();
        testAppliesCapturedContextClassLoader();
    }

    static void testNullCallableThrowsNPE() {
        boolean thrown = false;
        try {
            Executors.privilegedCallableUsingCurrentClassLoader(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null callable");
        }
    }

    static void testReturnsWrappedResult() throws Exception {
        Callable<String> inner = () -> "value";
        Callable<String> wrapped = Executors.privilegedCallableUsingCurrentClassLoader(inner);
        if (wrapped == null) {
            throw new RuntimeException("Wrapped callable must not be null");
        }
        String result = wrapped.call();
        if (!"value".equals(result)) {
            throw new RuntimeException("Unexpected result: " + result);
        }
    }

    static void testAppliesCapturedContextClassLoader() throws Exception {
        Thread current = Thread.currentThread();
        ClassLoader original = current.getContextClassLoader();
        ClassLoader captured = new ClassLoader(Executors.class.getClassLoader()) { };
        try {
            current.setContextClassLoader(captured);
            Callable<ClassLoader> probe = () -> Thread.currentThread().getContextClassLoader();
            Callable<ClassLoader> wrapped = Executors.privilegedCallableUsingCurrentClassLoader(probe);
            current.setContextClassLoader(original);
            ClassLoader seen = wrapped.call();
            if (seen != captured) {
                throw new RuntimeException("Context class loader not applied during call");
            }
        } finally {
            current.setContextClassLoader(original);
        }
    }
}
