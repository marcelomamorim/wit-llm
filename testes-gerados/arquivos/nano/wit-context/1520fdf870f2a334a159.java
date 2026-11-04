/* @test    @summary Test getDouble method of UnsafeFloatFieldAccessorImpl for exception path.    @run main UnsafeFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleWithInvalidObject();
    }

    private static void testGetDoubleWithInvalidObject() throws Exception {
        // Setup: create an instance of UnsafeFloatFieldAccessorImpl with a valid Field
        Field dummyField = DummyClass.class.getDeclaredField("floatValue");
        dummyField.setAccessible(true);
        jdk.internal.reflect.UnsafeFloatFieldFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeFloatFieldFieldAccessorImpl(dummyField);

        // Create an object of a class that is not assignable to the field's declaring class
        Object invalidObj = new String("invalid");

        // Expect IllegalArgumentException due to guard condition
        try {
            accessor.getDouble(invalidObj);
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Test passes if exception is thrown
            System.out.println("Caught expected IllegalArgumentException.");
        }
    }

    static class DummyClass {
        public float floatValue;
    }
}
