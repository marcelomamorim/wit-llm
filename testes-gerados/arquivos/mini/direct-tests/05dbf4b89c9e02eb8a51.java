/*
 * @test
 * @summary Test UnsafeStaticFloatFieldAccessorImpl.getInt throws IllegalArgumentException
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field floatField = Dummy.class.getDeclaredField("f");
        UnsafeStaticFloatFieldAccessorImpl accessor = new UnsafeStaticFloatFieldAccessorImpl(floatField);

        try {
            accessor.getInt(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        public static float f = 1.0f;
    }
}
