/*
 * @test
 * @summary Test UnsafeQualifiedObjectFieldAccessorImpl.getLong throws IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = Dummy.class.getDeclaredField("value");
            UnsafeQualifiedObjectFieldAccessorImpl accessor = new UnsafeQualifiedObjectFieldAccessorImpl(dummyField, false);

            // The getLong method must throw IllegalArgumentException always
            try {
                accessor.getLong(new Dummy());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getLong");
            } catch (IllegalArgumentException expected) {
                // Expected exception
            }

            // Also test that getLong throws regardless of null argument
            try {
                accessor.getLong(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getLong with null");
            } catch (IllegalArgumentException expected) {
                // Expected exception
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: Field not found", e);
        }
    }

    // Dummy class with a field to obtain Field instance
    static class Dummy {
        Object value = null;
    }
}
