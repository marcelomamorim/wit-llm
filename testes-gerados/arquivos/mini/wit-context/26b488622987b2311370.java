/*
 * @test
 * @summary Test for jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl.getBoolean(Object) throwing IllegalArgumentException
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // We need a Field instance to construct UnsafeQualifiedStaticLongFieldAccessorImpl
        // Since no public constructor or factory is documented, we use reflection to get a Field from a known class
        Field field = Dummy.class.getDeclaredField("value");
        UnsafeQualifiedStaticLongFieldAccessorImpl accessor = new UnsafeQualifiedStaticLongFieldAccessorImpl(field, false);

        // Test getBoolean throws IllegalArgumentException as per current implementation
        try {
            accessor.getBoolean(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getBoolean");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught, test passes
        }
    }

    // Dummy class to provide a Field instance for test
    static class Dummy {
        static long value = 0L;
    }
}
