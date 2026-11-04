/*
 * @test
 * @summary Test getBoolean method throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor (using reflection)
        Field field = Dummy.class.getDeclaredField("dummyFloat");
        UnsafeQualifiedStaticFloatFieldAccessorImpl accessor = new UnsafeQualifiedStaticFloatFieldAccessorImpl(field, false);

        try {
            accessor.getBoolean(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static float dummyFloat = 0.0f;
    }
}
