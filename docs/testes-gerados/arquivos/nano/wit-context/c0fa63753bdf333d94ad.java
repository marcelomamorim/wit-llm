/* @test    @summary Test for getDouble method in UnsafeShortFieldAccessorImpl class    @run main UnsafeShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleWithInvalidObject();
        testGetDoubleWithValidObject();
    }

    private static void testGetDoubleWithInvalidObject() throws Exception {
        Field dummyField = null; // Placeholder, as actual field is not used in test
        // Create an instance of UnsafeShortFieldAccessorImpl with a dummy field
        jdk.internal.reflect.UnsafeShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeShortFieldAccessorImpl(dummyField);
        Object invalidObject = new Object();
        try {
            accessor.getDouble(invalidObject);
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException for invalid object")
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }

    private static void testGetDoubleWithValidObject() throws Exception {
        // Setup a dummy class with a short field
        class Dummy {
            public short value = 42;
        }
        Dummy dummyInstance = new Dummy();
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeShortFieldAccessorImpl(field);
        // Call getDouble with a valid object
        double result = accessor.getDouble(dummyInstance);
        if (result != 42.0) {
            throw new AssertionError("Expected 42.0, got " + result);
        }
    }
}
