/*
 * @test
 * @summary Test privilegedCallable method for null argument and normal wrapping
 * @run main ExecutorsWitupTest_P481ea6fd35e9
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_P481ea6fd35e9 {
    public static void main(String[] args) {
        testNullCallableThrowsNPE();
        testPrivilegedCallableWrapsCallable();
    }

    private static void testNullCallableThrowsNPE() {
        try {
            Executors.privilegedCallable(null);
            throw new AssertionError("Expected NullPointerException for null callable");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testPrivilegedCallableWrapsCallable() {
        Callable<String> original = new Callable<String>() {
            public String call() {
                return "success";
            }
        };

        Callable<String> wrapped = Executors.privilegedCallable(original);
        if (wrapped == null) {
            throw new AssertionError("privilegedCallable returned null");
        }

        try {
            String result = wrapped.call();
            if (!"success".equals(result)) {
                throw new AssertionError("Wrapped callable did not return expected result");
            }
        } catch (Exception e) {
            throw new AssertionError("Wrapped callable threw unexpected exception", e);
        }
    }
}
