/*
 * @test
 * @summary Test privilegedCallableUsingCurrentClassLoader method for null input and normal wrapping
 * @run main ExecutorsWitupTest_P2f03994f5684
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_P2f03994f5684 {
    public static void main(String[] args) throws Exception {
        testNullCallableThrowsNPE();
        testCallableWrapping();
    }

    private static void testNullCallableThrowsNPE() {
        try {
            Executors.privilegedCallableUsingCurrentClassLoader(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testCallableWrapping() throws Exception {
        Callable<String> original = new Callable<String>() {
            public String call() {
                return "success";
            }
        };

        Callable<String> wrapped = Executors.privilegedCallableUsingCurrentClassLoader(original);
        if (wrapped == null) {
            throw new Error("Returned callable must not be null");
        }

        String result = wrapped.call();
        if (!"success".equals(result)) {
            throw new Error("Wrapped callable did not return expected result");
        }

        if (wrapped == original) {
            throw new Error("Returned callable should be a new instance, not the original");
        }
    }
}
