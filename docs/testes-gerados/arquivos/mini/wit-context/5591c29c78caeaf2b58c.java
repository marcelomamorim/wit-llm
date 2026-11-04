/*
 * @test
 * @summary Test UnsafeQualifiedFloatFieldAccessorImpl.getLong throws IllegalArgumentException
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongThrows();
    }

    private static void testGetLongThrows() {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = Dummy.class.getDeclaredField("f");
            UnsafeQualifiedFloatFieldAccessorImpl accessor = new UnsafeQualifiedFloatFieldAccessorImpl(dummyField, false);
            accessor.getLong(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (NoSuchFieldException | SecurityException e) {
            throw new RuntimeException("Reflection setup failed", e);
        }
    }

    // Dummy class with a float field to create a Field instance
    static class Dummy {
        public float f = 1.0f;
    }
}
