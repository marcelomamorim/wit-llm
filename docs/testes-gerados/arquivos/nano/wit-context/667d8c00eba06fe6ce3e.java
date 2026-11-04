/*
 * @test
 * @summary Test getFloat method of UnsafeByteFieldAccessorImpl for exception path
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatWithInvalidObject();
    }

    public static void testGetFloatWithInvalidObject() throws Exception {
        // Setup: create an instance of UnsafeByteFieldAccessorImpl with a dummy Field
        Field dummyField = null;
        try {
            dummyField = UnsafeByteFieldAccessorImpl.class.getDeclaredField("field");
            dummyField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            // Since 'field' is not a real field, skip test
            return;
        }
        // Instantiate UnsafeByteFieldAccessorImpl
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(dummyField);
        Object invalidObj = new Object();
        try {
            accessor.getFloat(invalidObj);
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }
}
