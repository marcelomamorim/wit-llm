/*
 * @test
 * @summary Test UnsafeObjectFieldAccessorImpl.getBoolean throws expected IllegalArgumentException
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeObjectFieldAccessorImpl;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = Dummy.class.getDeclaredField("value");
            UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(dummyField);

            // The getBoolean method must throw IllegalArgumentException always
            try {
                accessor.getBoolean(new Dummy());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getBoolean");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

            // Also test that getBoolean throws regardless of null argument
            try {
                accessor.getBoolean(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getBoolean with null");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: Field not found", e);
        }
    }

    // Dummy class with a field to obtain a Field instance
    static class Dummy {
        Object value = null;
    }
}
