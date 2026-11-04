/*
 * @test
 * @summary Test UnsafeStaticObjectFieldAccessorImpl.getByte method throws IllegalArgumentException
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            UnsafeStaticObjectFieldAccessorImpl accessor = new UnsafeStaticObjectFieldAccessorImpl(dummyField);

            // The getByte method must throw IllegalArgumentException unconditionally
            try {
                accessor.getByte(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

            System.out.println("Test passed.");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummy field not found", e);
        }
    }

    // Dummy class with a static Object field to obtain a Field instance
    static class Dummy {
        static Object dummy = null;
    }
}
