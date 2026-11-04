/*
 * @test
 * @summary Test getBoolean method throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("dummyStaticField");
        UnsafeQualifiedStaticObjectFieldAccessorImpl accessor = new UnsafeQualifiedStaticObjectFieldAccessorImpl(field, false);

        try {
            accessor.getBoolean(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static Object dummyStaticField = null;
    }
}
