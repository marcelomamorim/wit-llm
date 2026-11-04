/* @test    @summary Test getShort method of UnsafeQualifiedStaticCharacterFieldAccessorImpl    @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        try {
            // Create a dummy instance to invoke getShort
            // Since getShort always throws, the method should throw regardless of input
            jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl accessor = createAccessor();
            accessor.getShort(null);
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl createAccessor() {
        // Reflection to instantiate the class with dummy parameters
        try {
            Field field = null; // Not used, but required for constructor
            return new jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl(field, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
