/*
 * @test
 * @summary Test for Executors.privilegedCallableUsingCurrentClassLoader handling null input
 * @run main ExecutorsWitupTest_P4d743710b7e9
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_P4d743710b7e9 {

    public static void main(String[] args) {
        testPrivilegedCallableUsingCurrentClassLoader_null();
    }

    public static void testPrivilegedCallableUsingCurrentClassLoader_null() {
        try {
            Callable<?> result = Executors.privilegedCallableUsingCurrentClassLoader(null);
            // If no exception thrown, this is an error according to specification
            System.err.println("Failed: Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
