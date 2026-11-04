/*
 * @test
 * @summary Test for jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl.getBoolean(Object) throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // We cannot instantiate UnsafeQualifiedStaticFloatFieldAccessorImpl directly because it has no public constructors
        // and no public factory methods are known from context.
        // Instead, we use reflection to get a Field instance and create the accessor via the package-private constructor.
        // Since the class is package-private and in the same package, we can instantiate it here.

        // Use a known static float field to test, e.g., java.lang.Float.MAX_VALUE (public static final float)
        Field field = Float.class.getDeclaredField("MAX_VALUE");

        // Create instance with isReadOnly = true (since MAX_VALUE is final)
        UnsafeQualifiedStaticFloatFieldAccessorImpl accessor = new UnsafeQualifiedStaticFloatFieldAccessorImpl(field, true);

        // Test getBoolean throws IllegalArgumentException
        try {
            accessor.getBoolean(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getBoolean");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
