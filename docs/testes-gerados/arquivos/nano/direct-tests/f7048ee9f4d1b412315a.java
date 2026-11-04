/* @test    @summary Test getInt method of UnsafeStaticBooleanFieldAccessorImpl for exception throwing    @run main UnsafeStaticBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsException();
    }

    private static void testGetIntThrowsException() {
        try {
            // Create an instance of the class with a dummy Field (null, as actual reflection is not used here)
            jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl(null);
            // Call getInt, expecting an IllegalArgumentException
            accessor.getInt(null);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException was thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
