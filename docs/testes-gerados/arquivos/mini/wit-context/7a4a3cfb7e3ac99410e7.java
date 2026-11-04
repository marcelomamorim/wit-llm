/*
 * @test
 * @summary Test that getChar(Object) always throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // We need a Field instance to construct UnsafeQualifiedStaticShortFieldAccessorImpl
        // Since no public constructor or factory is known, use reflection on a known static field
        Field field = UnsafeQualifiedStaticShortFieldAccessorImpl.class.getDeclaredField("fieldOffset");
        // The above field is private, but we only need a Field instance; we can use it as dummy

        // Construct the accessor with isReadOnly = false
        UnsafeQualifiedStaticShortFieldAccessorImpl accessor = new UnsafeQualifiedStaticShortFieldAccessorImpl(field, false);

        // Test getChar throws IllegalArgumentException
        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getChar");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
