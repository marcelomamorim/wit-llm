/*
 * @test
 * @summary Test getShort method throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static float field to pass to the constructor
        Field floatField = Dummy.class.getDeclaredField("f");
        UnsafeQualifiedStaticFloatFieldAccessorImpl accessor = new UnsafeQualifiedStaticFloatFieldAccessorImpl(floatField, false);

        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static float f = 0.0f;
    }
}
