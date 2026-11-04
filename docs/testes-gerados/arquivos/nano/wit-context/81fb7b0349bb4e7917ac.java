/*
 * @test
 * @summary Test for UnsafeQualifiedIntegerFieldAccessorImpl.getInt method
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntWithInvalidObject();
    }

    public static void testGetIntWithInvalidObject() throws Exception {
        // Setup: create an instance of UnsafeQualifiedIntegerFieldAccessorImpl with a dummy Field
        Field dummyField = null;
        try {
            dummyField = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            // Should not happen, as String has a "value" field
        }
        // Create an instance of UnsafeQualifiedIntegerFieldAccessorImpl
        jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl(dummyField, false);
        // Call getInt with an object of incompatible class to trigger IllegalArgumentException
        try {
            accessor.getInt(new Object());
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
            System.out.println("Caught expected IllegalArgumentException.");
        }
    }
}
