/*
 * @test
 * @summary Tests Executors.privilegedCallable null rejection and the wrapped callable's call result
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCallableRejected();
        testWrapperReturnsDelegateResult();
        testWrapperReturnsNullFromDelegate();
    }

    static void testNullCallableRejected() {
        try {
            Executors.privilegedCallable(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testWrapperReturnsDelegateResult() throws Exception {
        Callable<Integer> task = () -> 42;
        Callable<Integer> wrapped = Executors.privilegedCallable(task);
        if (wrapped == null) {
            throw new RuntimeException("wrapper must not be null");
        }
        if (wrapped == task) {
            throw new RuntimeException("wrapper must be a new callable instance");
        }
        Integer result = wrapped.call();
        if (!Integer.valueOf(42).equals(result)) {
            throw new RuntimeException("unexpected result: " + result);
        }
    }

    static void testWrapperReturnsNullFromDelegate() throws Exception {
        Callable<String> task = () -> null;
        Callable<String> wrapped = Executors.privilegedCallable(task);
        if (wrapped.call() != null) {
            throw new RuntimeException("expected null result from wrapper");
        }
    }
}
