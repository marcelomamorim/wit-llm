/*
 * @test
 * @summary Test getChar method of UnsafeStaticObjectFieldAccessorImpl
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsIllegalArgumentException();
    }

    public static void testGetCharThrowsIllegalArgumentException() {
        try {
            // Create an instance of UnsafeStaticObjectFieldAccessorImpl with a dummy Field
            Field dummyField = null; // No actual reflection needed for this test
            jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl(dummyField);
            // Call getChar and expect an IllegalArgumentException
            accessor.getChar(null);
            // If no exception thrown, the test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
