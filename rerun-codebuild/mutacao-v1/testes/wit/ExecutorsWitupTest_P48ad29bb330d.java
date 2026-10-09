/* @test    @summary Test for Executors.privilegedCallableUsingCurrentClassLoader method handling null input    @run main ExecutorsWitupTest_P48ad29bb330d */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_P48ad29bb330d {

    public static void main(String[] args) {
        testPrivilegedCallableUsingCurrentClassLoader_NullInput();
    }

    private static void testPrivilegedCallableUsingCurrentClassLoader_NullInput() {
        try {
            Callable<?> result = Executors.privilegedCallableUsingCurrentClassLoader(null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
