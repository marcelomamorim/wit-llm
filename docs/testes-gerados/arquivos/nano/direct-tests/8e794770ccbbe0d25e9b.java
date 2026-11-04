/* @test    @summary Test getLong method of UnsafeStaticBooleanFieldAccessorImpl for exception throwing    @run main UnsafeStaticBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsException();
    }

    public static void testGetLongThrowsException() {
        try {
            // Create an instance of the class with a dummy Field object
            Field dummyField = null; // No actual reflection needed for this test
            jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl(dummyField);
            accessor.getLong(null);
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("getLong() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
