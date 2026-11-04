/* @test    @summary Test getBoolean method of UnsafeQualifiedStaticShortFieldAccessorImpl    @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        try {
            // Create a dummy instance of the class to invoke getBoolean
            // Since getBoolean always throws, the actual instance is irrelevant
            jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl accessor = createAccessor();
            accessor.getBoolean(null);
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }

    private static jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl createAccessor() {
        // Reflection to instantiate the class with dummy parameters
        try {
            Field dummyField = null; // Not used in getBoolean, so null suffices
            boolean isReadOnly = false;
            return new jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl(dummyField, isReadOnly);
        } catch (Throwable t) {
            throw new AssertionError("Failed to create accessor instance", t);
        }
    }
}
