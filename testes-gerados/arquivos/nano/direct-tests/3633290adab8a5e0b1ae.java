/* @test    @summary Test getDouble method of UnsafeObjectFieldAccessorImpl class    @run main UnsafeObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleThrowsException();
    }

    public static void testGetDoubleThrowsException() {
        try {
            // Create an instance of UnsafeObjectFieldAccessorImpl with a dummy Field
            Field dummyField = null; // No actual reflection needed for this test
            jdk.internal.reflect.UnsafeObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeObjectFieldAccessorImpl(dummyField);
            // Call getDouble and expect an IllegalArgumentException
            accessor.getDouble(new Object());
            // If no exception, the test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
