/* @test
   @summary Test for Executors.privilegedCallableUsingCurrentClassLoader method.
   @run main ExecutorsWitupTest_Pace2915581bd
 */
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
public class ExecutorsWitupTest_Pace2915581bd {
    public static void main(String[] args) {
        testNullCallable();
        testCallableExecution();
    }
    static void testNullCallable() {
        try {
            Executors.privilegedCallableUsingCurrentClassLoader(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
    static void testCallableExecution() {
        Callable<String> callable = () -> "test";
        Callable<String> privilegedCallable = Executors.privilegedCallableUsingCurrentClassLoader(callable);
        try {
            String result = privilegedCallable.call();
            if (!"test".equals(result)) {
                throw new AssertionError("Unexpected result: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
