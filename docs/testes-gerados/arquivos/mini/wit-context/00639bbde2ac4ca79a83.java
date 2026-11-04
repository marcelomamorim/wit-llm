/*
 * @test
 * @summary Test UnsafeQualifiedStaticFloatFieldAccessorImpl.getByte throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedStaticFloatFieldAccessorImpl.class.getDeclaredField("fieldOffset");
        dummyField.setAccessible(true);

        // Construct instance with isReadOnly false (arbitrary)
        UnsafeQualifiedStaticFloatFieldAccessorImpl accessor = new UnsafeQualifiedStaticFloatFieldAccessorImpl(dummyField, false);

        // Test getByte throws IllegalArgumentException
        try {
            accessor.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
