/* @test    @summary Test for Executors.privilegedCallable method to ensure null input throws NullPointerException and valid input returns a PrivilegedCallable instance    @run main ExecutorsWitupTest_Peb99fd6c0bc7 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class ExecutorsWitupTest_Peb99fd6c0bc7 {

    public static void main(String[] args) {
        testNullCallable();
        testValidCallable();
    }

    public static void testNullCallable() {
        boolean exceptionThrown = false;
        try {
            Executors.privilegedCallable(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null argument");
        }
    }

    public static void testValidCallable() {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                return 42;
            }
        };
        Callable<Integer> privileged = Executors.privilegedCallable(callable);
        if (privileged == null) {
            throw new AssertionError("privilegedCallable returned null");
        }
        if (!(privileged instanceof java.util.concurrent.Executors.PrivilegedCallable)) {
            throw new AssertionError("Returned object is not an instance of PrivilegedCallable");
        }
        try {
            Integer result = privileged.call();
            if (result != 42) {
                throw new AssertionError("Unexpected result from call: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Exception while calling privileged Callable", e);
        }
    }
}
