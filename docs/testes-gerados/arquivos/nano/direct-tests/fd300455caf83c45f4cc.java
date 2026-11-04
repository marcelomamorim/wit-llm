/* @test    @summary Test getBoolean method of UnsafeByteFieldAccessorImpl class    @run main UnsafeByteFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        try {
            // Create an instance of UnsafeByteFieldAccessorImpl with a dummy Field
            Field dummyField = null; // No actual reflection needed for this test
            jdk.internal.reflect.UnsafeByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeByteFieldAccessorImpl(dummyField);
            // Call getBoolean, expecting an IllegalArgumentException
            accessor.getBoolean(new Object());
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
