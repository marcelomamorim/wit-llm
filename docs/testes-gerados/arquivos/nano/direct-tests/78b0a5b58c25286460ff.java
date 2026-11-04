/* @test    @summary Test getByte method of UnsafeFloatFieldAccessorImpl class to ensure it throws the expected exception.    @run main UnsafeFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() {
        try {
            // Create a dummy Field object; since the method does not use it, null suffices for this test.
            Field dummyField = null;
            jdk.internal.reflect.UnsafeFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeFloatFieldAccessorImpl(dummyField);
            accessor.getByte(new Object());
            // If no exception is thrown, the test fails.
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception; test passes.
        } catch (Throwable t) {
            // Any other exception indicates a failure.
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
