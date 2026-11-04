/*
 * @test
 * @summary Test privilegedCallableUsingCurrentClassLoader method of Executors
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCallableThrowsNPE();
        testCallableReturnsExpectedValue();
    }

    static void testNullCallableThrowsNPE() {
        try {
            Executors.privilegedCallableUsingCurrentClassLoader(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testCallableReturnsExpectedValue() throws Exception {
        final String expected = "test";
        Callable<String> original = new Callable<String>() {
            public String call() {
                return expected;
            }
        };

        Callable<String> privileged = Executors.privilegedCallableUsingCurrentClassLoader(original);
        String result = privileged.call();

        if (!expected.equals(result)) {
            throw new Error("Returned value does not match expected value");
        }
    }
}
