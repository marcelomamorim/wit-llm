/* @test
   @summary Test for Executors.privilegedCallable method.
   @run main ExecutorsWitupTest_P644c0236b162
 */
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
public class ExecutorsWitupTest_P644c0236b162 {
    public static void main(String[] args) {
        testPrivilegedCallableWithNull();
        testPrivilegedCallableWithValid();
    }
    private static void testPrivilegedCallableWithNull() {
        try {
            Executors.privilegedCallable(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
    private static void testPrivilegedCallableWithValid() {
        Callable<String> original = () -> "test";
        Callable<String> wrapped = Executors.privilegedCallable(original);
        if (wrapped == null) {
            throw new AssertionError("Wrapped callable is null");
        }
        try {
            String result = wrapped.call();
            if (!"test".equals(result)) {
                throw new AssertionError("Unexpected result: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Callable threw exception", e);
        }
    }
}
