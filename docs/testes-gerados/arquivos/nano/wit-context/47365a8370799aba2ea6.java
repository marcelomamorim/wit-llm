/* @test    @summary Test for Executors.privilegedCallableUsingCurrentClassLoader method to verify NullPointerException when input is null    @run main ExecutorsWitupTest */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testPrivilegedCallableUsingCurrentClassLoader_NullInput();
    }

    public static void testPrivilegedCallableUsingCurrentClassLoader_NullInput() {
        try {
            Executors.privilegedCallableUsingCurrentClassLoader(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Test passes if exception is thrown
            System.out.println("NullPointerException correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
