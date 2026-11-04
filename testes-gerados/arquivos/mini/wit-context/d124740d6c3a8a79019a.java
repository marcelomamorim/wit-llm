/*
 * @test
 * @summary Test UnsafeFloatFieldAccessorImpl.getBoolean throws expected IllegalArgumentException
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeFloatFieldAccessorImpl;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field floatField = Dummy.class.getDeclaredField("f");
        UnsafeFloatFieldAccessorImpl accessor = new UnsafeFloatFieldAccessorImpl(floatField);

        // Test getBoolean always throws IllegalArgumentException
        try {
            accessor.getBoolean(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught
        }
    }

    // Dummy class with a float field to create Field instance
    static class Dummy {
        public float f = 1.0f;
    }
}
