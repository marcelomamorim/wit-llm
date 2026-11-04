/*
 * @test
 * @summary Test UnsafeQualifiedStaticObjectFieldAccessorImpl.getBoolean throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = Dummy.class.getDeclaredField("value");
            UnsafeQualifiedStaticObjectFieldAccessorImpl accessor = new UnsafeQualifiedStaticObjectFieldAccessorImpl(dummyField, false);

            // Call getBoolean and expect IllegalArgumentException
            try {
                accessor.getBoolean(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getBoolean");
            } catch (IllegalArgumentException expected) {
                // Expected exception
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummy field not found", e);
        }
    }

    // Dummy class with a field to obtain a Field instance
    static class Dummy {
        public Object value;
    }
}
