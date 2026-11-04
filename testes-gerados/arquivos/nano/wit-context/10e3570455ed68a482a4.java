/*
 * @test
 * @summary Test for UnsafeQualifiedDoubleFieldAccessorImpl.getDouble method
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleWithInvalidObject();
    }

    private static void testGetDoubleWithInvalidObject() throws Exception {
        // Setup: create a dummy field and accessor
        class Dummy {
            public double value;
        }
        Dummy dummyInstance = new Dummy();
        Field field = Dummy.class.getDeclaredField("value");
        // Simulate the accessor with a valid field and isReadOnly = false
        jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl(field, false);
        // Test: pass an object of incompatible class to getDouble
        Object invalidObject = new Object();
        try {
            accessor.getDouble(invalidObject);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Caught expected IllegalArgumentException.");
        }
    }
}
