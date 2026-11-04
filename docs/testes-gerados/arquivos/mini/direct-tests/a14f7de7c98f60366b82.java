/*
 * @test
 * @summary Test UnsafeStaticFloatFieldAccessorImpl.getLong throws IllegalArgumentException
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static float field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("dummyFloat");
        UnsafeStaticFloatFieldAccessorImpl accessor = new UnsafeStaticFloatFieldAccessorImpl(field);

        try {
            accessor.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static float dummyFloat = 0.0f;
    }
}
