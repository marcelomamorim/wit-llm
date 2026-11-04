/*
 * @test
 * @summary Test for UnsafeIntegerFieldAccessorImpl.getDouble method behavior
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleWithInvalidObject();
        testGetDoubleWithValidObject();
    }

    private static void testGetDoubleWithInvalidObject() throws Exception {
        Field dummyField = null;
        try {
            dummyField = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            // ignore, as we only need a dummy Field reference
        }
        // Create an instance of UnsafeIntegerFieldAccessorImpl with dummyField
        jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl(dummyField);
        Object invalidObj = new Object();
        try {
            accessor.getDouble(invalidObj);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException not thrown");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testGetDoubleWithValidObject() throws Exception {
        // Create a dummy class with an int field
        class Dummy {
            public int value = 42;
        }
        Dummy dummyInstance = new Dummy();
        Field field = Dummy.class.getDeclaredField("value");
        jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl(field);
        double result = accessor.getDouble(dummyInstance);
        if (result != 42.0) {
            System.err.println("Unexpected result: " + result);
            System.exit(1);
        }
    }
}
