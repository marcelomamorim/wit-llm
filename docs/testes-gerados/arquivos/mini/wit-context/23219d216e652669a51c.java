/*
 * @test
 * @summary Test UnsafeObjectFieldAccessorImpl.getChar throws IllegalArgumentException
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

            // The getChar method must always throw IllegalArgumentException
            try {
                accessor.getChar(new Dummy());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getChar");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: field not found", e);
        }
    }

    // Dummy class with a field to obtain a Field instance
    static class Dummy {
        public Object value = null;
    }
}
