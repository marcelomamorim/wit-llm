/* @test    @summary Test for Executors.privilegedCallable handling null input    @run main ExecutorsWitupTest_P59bbe009ee26 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_P59bbe009ee26 {

    public static void main(String[] args) {
        testPrivilegedCallableNull();
    }

    private static void testPrivilegedCallableNull() {
        try {
            Executors.privilegedCallable(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
