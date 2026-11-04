/*
 * @test
 * @summary Test java.util.concurrent.Executors.privilegedCallable method
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCallableThrowsNPE();
        testPrivilegedCallableReturnsWrapper();
        testPrivilegedCallableWrapperCallsOriginal() throws Exception;
    }

    private static void testNullCallableThrowsNPE() {
        try {
            Executors.privilegedCallable(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testPrivilegedCallableReturnsWrapper() throws Exception {
        Callable<String> original = new Callable<String>() {
            public String call() {
                return "test";
            }
        };
        Callable<String> wrapped = Executors.privilegedCallable(original);
        if (wrapped == null) {
            throw new Error("privilegedCallable returned null");
        }
        if (wrapped == original) {
            throw new Error("privilegedCallable returned original callable instead of wrapper");
        }
    }

    private static void testPrivilegedCallableWrapperCallsOriginal() throws Exception {
        final boolean[] called = new boolean[1];
        Callable<String> original = new Callable<String>() {
            public String call() {
                called[0] = true;
                return "called";
            }
        };
        Callable<String> wrapped = Executors.privilegedCallable(original);
        String result = wrapped.call();
        if (!called[0]) {
            throw new Error("Wrapped callable did not call original callable");
        }
        if (!"called".equals(result)) {
            throw new Error("Wrapped callable returned unexpected result: " + result);
        }
    }
}
