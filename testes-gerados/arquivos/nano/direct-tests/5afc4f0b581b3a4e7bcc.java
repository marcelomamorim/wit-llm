/* @test    @summary Test getByte method of UnsafeStaticFloatFieldAccessorImpl class    @run main UnsafeStaticFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() {
        try {
            // Create a dummy Field object (null) to pass; method does not use it
            Field dummyField = null;
            jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl(dummyField);
            accessor.getByte(null);
            // If no exception thrown, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
