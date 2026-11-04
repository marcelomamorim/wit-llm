/*
 * @test
 * @summary Test UnsafeQualifiedStaticFloatFieldAccessorImpl.getChar throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Use reflection to get a Field instance for a static float field
        Field field = Dummy.class.getDeclaredField("staticFloatField");
        // Create instance with isReadOnly false (arbitrary, as getChar always throws)
        UnsafeQualifiedStaticFloatFieldAccessorImpl accessor = new UnsafeQualifiedStaticFloatFieldAccessorImpl(field, false);

        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Dummy class with a static float field to obtain Field instance
    static class Dummy {
        static float staticFloatField = 1.0f;
    }
}
