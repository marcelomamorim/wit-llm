/*
 * @test
 * @summary Test for getFloat method in UnsafeShortFieldAccessorImpl
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatWithInvalidObject();
        testGetFloatWithValidObject();
    }

    private static void testGetFloatWithInvalidObject() throws Exception {
        Field dummyField = null; // Placeholder, as actual field is not used in test
        // Create an instance of UnsafeShortFieldAccessorImpl with a dummy field
        jdk.internal.reflect.UnsafeShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeShortFieldAccessorImpl(dummyField);
        Object invalidObject = new Object();
        try {
            accessor.getFloat(invalidObject);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException not thrown in getFloat with invalid object");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Caught expected IllegalArgumentException in getFloat with invalid object");
        }
    }

    private static void testGetFloatWithValidObject() throws Exception {
        // Setup a dummy class with a short field
        class Dummy {
            public short value = 42;
        }
        Dummy dummyInstance = new Dummy();
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeShortFieldAccessorImpl(field);
        // Call getFloat with a valid object
        float result = accessor.getFloat(dummyInstance);
        if (result == 42.0f) {
            System.out.println("getFloat returned expected value: " + result);
        } else {
            System.err.println("Unexpected value from getFloat: " + result);
        }
    }
}
