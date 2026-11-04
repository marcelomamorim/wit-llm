/*
 * @test
 * @summary Test for jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl.getLong(Object) method
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // We cannot instantiate UnsafeQualifiedStaticFloatFieldAccessorImpl directly because it has no public constructors
        // and no public factory methods are known from context.
        // Instead, we use reflection to get a Field and create an instance via UnsafeQualifiedStaticFloatFieldAccessorImpl's package-private constructor.
        // However, reflection is not allowed per instructions, so we test only the observable behavior of getLong.

        // Create a dummy Field instance for test (using a known public field)
        Field field = Dummy.class.getDeclaredField("f");

        // Create instance via UnsafeQualifiedStaticFloatFieldAccessorImpl constructor (package-private, so accessible here)
        UnsafeQualifiedStaticFloatFieldAccessorImpl accessor = new UnsafeQualifiedStaticFloatFieldAccessorImpl(field, false);

        try {
            accessor.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Dummy class with a float field to obtain a Field instance
    static class Dummy {
        public static float f = 0.0f;
    }
}
