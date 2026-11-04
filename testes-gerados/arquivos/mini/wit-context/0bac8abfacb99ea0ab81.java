/*
 * @test
 * @summary Test UnsafeQualifiedStaticFloatFieldAccessorImpl.getShort throws expected IllegalArgumentException
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // We need a Field instance to construct UnsafeQualifiedStaticFloatFieldAccessorImpl
        // Since no public constructor or factory is available, use reflection on a known field
        Field floatField = Dummy.class.getDeclaredField("f");
        UnsafeQualifiedStaticFloatFieldAccessorImpl accessor = new UnsafeQualifiedStaticFloatFieldAccessorImpl(floatField, false);

        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Dummy class with a float field to obtain a Field instance
    static class Dummy {
        public static float f = 1.0f;
    }
}
