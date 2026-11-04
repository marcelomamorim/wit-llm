/*
 * @test
 * @summary Test UnsafeQualifiedStaticObjectFieldAccessorImpl.getInt throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Setup a Field instance for testing
        Field field = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");

        // Create instance with isReadOnly = false (value irrelevant for getInt)
        UnsafeQualifiedStaticObjectFieldAccessorImpl accessor = new UnsafeQualifiedStaticObjectFieldAccessorImpl(field, false);

        // Test getInt throws IllegalArgumentException as per current implementation
        try {
            accessor.getInt(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getInt");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught, test passes
        }
    }

    // Dummy field to obtain Field instance
    private static Object dummyField = null;
}
