/*
 * @test
 * @summary Test getBoolean method throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static int field to pass to constructor
        Field field = Dummy.class.getDeclaredField("dummyInt");
        UnsafeQualifiedStaticIntegerFieldAccessorImpl accessor = new UnsafeQualifiedStaticIntegerFieldAccessorImpl(field, false);

        try {
            accessor.getBoolean(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static int dummyInt = 42;
    }
}
