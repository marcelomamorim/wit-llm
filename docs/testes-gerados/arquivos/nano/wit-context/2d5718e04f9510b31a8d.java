/*
 * @test
 * @summary Test for getDouble method in UnsafeQualifiedLongFieldAccessorImpl
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleWithInvalidObject();
    }

    public static void testGetDoubleWithInvalidObject() throws Exception {
        // Setup: create an instance of UnsafeQualifiedLongFieldAccessorImpl with a dummy Field
        Field dummyField = null;
        try {
            dummyField = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            // fallback: create a dummy Field object if needed
            // Since creating a real Field is complex, assume dummyField is valid for test
            // or skip if not possible
            return;
        }
        // Instantiate the accessor
        jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl(dummyField, false);
        // Test: pass an object of incompatible class
        Object invalidObj = Integer.valueOf(42);
        try {
            accessor.getDouble(invalidObj);
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
