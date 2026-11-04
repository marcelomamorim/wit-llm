/*
 * @test
 * @summary Test that UnsafeQualifiedStaticFloatFieldAccessorImpl.getInt always throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor (using reflection)
        Field dummyField = UnsafeQualifiedStaticFloatFieldAccessorImpl.class.getDeclaredField("fieldOffset");
        dummyField.setAccessible(true);

        // Construct instance with isReadOnly = false
        UnsafeQualifiedStaticFloatFieldAccessorImpl accessor = new UnsafeQualifiedStaticFloatFieldAccessorImpl(dummyField, false);

        try {
            accessor.getInt(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // Test passed
        }
    }
}
