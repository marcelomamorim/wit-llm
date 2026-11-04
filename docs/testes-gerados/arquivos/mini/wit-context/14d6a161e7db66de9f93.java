/*
 * @test
 * @summary Test UnsafeStaticObjectFieldAccessorImpl.getShort method throws expected IllegalArgumentException
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Use reflection to get a static Object field from a known class
        Field field = SampleStaticObjectFieldHolder.class.getDeclaredField("staticObjectField");
        UnsafeStaticObjectFieldAccessorImpl accessor = new UnsafeStaticObjectFieldAccessorImpl(field);

        // The getShort method always throws IllegalArgumentException
        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught
        }
    }

    // Helper class with a static Object field to obtain a Field instance
    static class SampleStaticObjectFieldHolder {
        static Object staticObjectField = "test";
    }
}
