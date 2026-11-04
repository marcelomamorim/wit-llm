/* @test    @summary Test getBoolean method of UnsafeStaticFloatFieldAccessorImpl that throws IllegalArgumentException */
/* @run main UnsafeStaticFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    public static void testGetBooleanThrowsException() {
        try {
            // Create an instance of UnsafeStaticFloatFieldAccessorImpl with a dummy Field
            Field dummyField = null; // No actual reflection needed for this test
            jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl(dummyField);
            accessor.getBoolean(null);
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("getBoolean() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
