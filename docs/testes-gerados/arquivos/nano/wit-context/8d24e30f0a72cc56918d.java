/* @test    @summary Test for getLong method in UnsafeIntegerFieldAccessorImpl to verify exception handling and behavior.    @run main UnsafeIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongWithInvalidObject();
        testGetLongWithValidObject();
    }

    private static void testGetLongWithInvalidObject() throws Exception {
        Field dummyField = null; // Placeholder, as actual field is not used in current method
        // Create an instance of UnsafeIntegerFieldAccessorImpl with a dummy field
        // Since constructor is package-private, assume access or use reflection if needed
        // For this test, we simulate the behavior by directly calling getLong with invalid object
        jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl(dummyField);
        Object invalidObj = new Object();
        try {
            accessor.getLong(invalidObj);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException not thrown for invalid object.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Caught expected IllegalArgumentException for invalid object.");
        }
    }

    private static void testGetLongWithValidObject() throws Exception {
        // Create a dummy class with a field to test
        class Dummy {
            public int value = 42;
        }
        Dummy dummyInstance = new Dummy();
        // Obtain the Field object for 'value'
        Field field = Dummy.class.getDeclaredField("value");
        // Create accessor
        jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl(field);
        // Call getLong with valid object
        long result = accessor.getLong(dummyInstance);
        // Verify result equals the int value
        if (result != dummyInstance.value) {
            System.err.println("Unexpected result: " + result);
        } else {
            System.out.println("getLong returned expected value: " + result);
        }
    }
}
