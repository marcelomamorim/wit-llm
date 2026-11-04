/* @test    @summary Test getBoolean method of UnsafeFloatFieldAccessorImpl that throws IllegalArgumentException */
/* @run main UnsafeFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    public static void testGetBooleanThrowsException() {
        try {
            // Create an instance of UnsafeFloatFieldAccessorImpl with a dummy Field
            Field dummyField = null; // No actual reflection needed for this test
            jdk.internal.reflect.UnsafeFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeFloatFieldAccessorImpl(dummyField);
            accessor.getBoolean(new Object());
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
