/*
 * @test
 * @summary Test that UnsafeQualifiedFloatFieldAccessorImpl.getBoolean always throws IllegalArgumentException
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor (using reflection on a known field)
        Field floatField = Dummy.class.getDeclaredField("f");
        UnsafeQualifiedFloatFieldAccessorImpl accessor = new UnsafeQualifiedFloatFieldAccessorImpl(floatField, false);

        // Test that getBoolean throws IllegalArgumentException
        try {
            accessor.getBoolean(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        public float f = 1.0f;
    }
}
