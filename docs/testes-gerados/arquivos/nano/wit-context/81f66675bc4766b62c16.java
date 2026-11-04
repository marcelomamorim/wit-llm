/*
 * @test
 * @summary Test for getDouble method in UnsafeQualifiedFloatFieldAccessorImpl
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleWithInvalidObject();
    }

    public static void testGetDoubleWithInvalidObject() throws Exception {
        // Setup: create an instance of UnsafeQualifiedFloatFieldAccessorImpl with a dummy Field
        Field dummyField = null;
        try {
            dummyField = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            // Should not happen, as String has a "value" field
        }
        // Create an instance of UnsafeQualifiedFloatFieldAccessorImpl
        jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl(dummyField, false);
        // Use an object of incompatible class to trigger IllegalArgumentException
        Object invalidObj = Integer.valueOf(42);
        try {
            accessor.getDouble(invalidObj);
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }
}
