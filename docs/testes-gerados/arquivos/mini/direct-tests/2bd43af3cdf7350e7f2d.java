/*
 * @test
 * @summary Test getChar method throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field floatField = Dummy.class.getDeclaredField("f");
        UnsafeQualifiedStaticFloatFieldAccessorImpl accessor = new UnsafeQualifiedStaticFloatFieldAccessorImpl(floatField, false);

        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static float f = 0.0f;
    }
}
