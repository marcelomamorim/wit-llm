/*
 * @test
 * @summary Test UnsafeStaticObjectFieldAccessorImpl.getFloat throws IllegalArgumentException
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeStaticObjectFieldAccessorImpl accessor = new UnsafeStaticObjectFieldAccessorImpl(dummyField);

            // The getFloat method always throws IllegalArgumentException
            try {
                accessor.getFloat(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getFloat");
            } catch (IllegalArgumentException expected) {
                // Expected exception
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain a Field instance for constructor
    private static Object dummyField = null;
}
