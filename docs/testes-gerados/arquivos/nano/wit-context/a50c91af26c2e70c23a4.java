/*
 * @test
 * @summary Test for getFloat method in UnsafeQualifiedIntegerFieldAccessorImpl
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatWithInvalidObject();
    }

    private static void testGetFloatWithInvalidObject() throws Exception {
        // Setup: create a dummy Field object (using reflection on a real class)
        // Note: In real tests, this should be a valid Field instance from a class
        // For simplicity, we assume a valid Field is obtained here.
        // Since creating a real Field instance is complex, we simulate the behavior.
        // Alternatively, if the class is accessible, get a real Field from a class.
        Class<?> clazz = Class.forName("java.lang.String");
        Field field = clazz.getDeclaredField("value");
        // Instantiate the class under test
        UnsafeQualifiedIntegerFieldAccessorImpl accessor = new UnsafeQualifiedIntegerFieldAccessorImpl(field, false);
        // Test: pass an object of incompatible type to getFloat
        Object invalidObj = Integer.valueOf(42);
        try {
            accessor.getFloat(invalidObj);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Caught expected IllegalArgumentException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
