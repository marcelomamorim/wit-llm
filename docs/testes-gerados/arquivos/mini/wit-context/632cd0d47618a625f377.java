/*
 * @test
 * @summary Test UnsafeQualifiedStaticFloatFieldAccessorImpl.getInt throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // We cannot instantiate UnsafeQualifiedStaticFloatFieldAccessorImpl directly because it has no public constructors
        // and no public factory methods are known from context.
        // Instead, use reflection to get a Field and create an instance via the package-private constructor.
        // This is allowed here because the test is in the same package (jdk.internal.reflect).

        Field floatField = Dummy.class.getDeclaredField("f");
        UnsafeQualifiedStaticFloatFieldAccessorImpl accessor = new UnsafeQualifiedStaticFloatFieldAccessorImpl(floatField, false);

        // Test that getInt throws IllegalArgumentException as expected
        try {
            accessor.getInt(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getInt");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Dummy class with a static float field to obtain a Field instance
    static class Dummy {
        static float f = 1.0f;
    }
}
