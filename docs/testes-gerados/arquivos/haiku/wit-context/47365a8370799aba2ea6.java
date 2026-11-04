/*
 * @test
 * @summary Checks null rejection and delegation of Executors.privilegedCallableUsingCurrentClassLoader
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCallableThrowsNPE();
        testWrapperDelegatesToOriginalCallable();
    }

    static void testNullCallableThrowsNPE() {
        try {
            Executors.privilegedCallableUsingCurrentClassLoader(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null callable");
    }

    static void testWrapperDelegatesToOriginalCallable() throws Exception {
        Callable<String> original = () -> "ok";
        Callable<String> wrapped = Executors.privilegedCallableUsingCurrentClassLoader(original);
        if (wrapped == null) {
            throw new RuntimeException("Wrapper must not be null");
        }
        String result = wrapped.call();
        if (!"ok".equals(result)) {
            throw new RuntimeException("Unexpected result: " + result);
        }
    }
}
