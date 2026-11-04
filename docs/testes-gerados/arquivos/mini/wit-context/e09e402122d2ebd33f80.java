/*
 * @test
 * @summary Test UnsafeQualifiedStaticLongFieldAccessorImpl.getInt throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // We need a Field instance to construct UnsafeQualifiedStaticLongFieldAccessorImpl
        // Since no public constructor or factory is known, we use reflection on a known static field
        Field field = java.lang.System.class.getDeclaredField("out"); // public static final PrintStream out

        // Construct instance with isReadOnly = false (arbitrary)
        UnsafeQualifiedStaticLongFieldAccessorImpl accessor = new UnsafeQualifiedStaticLongFieldAccessorImpl(field, false);

        // Test getInt(Object) throws IllegalArgumentException
        try {
            accessor.getInt(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getInt");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
