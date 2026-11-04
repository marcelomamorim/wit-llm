/*
 * @test
 * @summary Test that UnsafeStaticFloatFieldAccessorImpl.getShort always throws IllegalArgumentException
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = Dummy.class.getDeclaredField("dummyFloat");
        UnsafeStaticFloatFieldAccessorImpl accessor = new UnsafeStaticFloatFieldAccessorImpl(dummyField);

        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        public static float dummyFloat = 0.0f;
    }
}
