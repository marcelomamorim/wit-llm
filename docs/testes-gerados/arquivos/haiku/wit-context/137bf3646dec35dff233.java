/*
 * @test
 * @summary Tests Executors.privilegedCallable null handling and delegation of call()
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCallableThrowsNPE();
        testNonNullCallableIsDelegated();
    }

    static void testNullCallableThrowsNPE() {
        try {
            Executors.privilegedCallable(null);
            throw new AssertionError("Expected NullPointerException for null callable");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNonNullCallableIsDelegated() throws Exception {
        Callable<String> delegate = () -> "ok";
        Callable<String> wrapped = Executors.privilegedCallable(delegate);
        if (wrapped == null) {
            throw new AssertionError("privilegedCallable returned null");
        }
        String result = wrapped.call();
        if (!"ok".equals(result)) {
            throw new AssertionError("Expected \"ok\" but got " + result);
        }
    }
}
