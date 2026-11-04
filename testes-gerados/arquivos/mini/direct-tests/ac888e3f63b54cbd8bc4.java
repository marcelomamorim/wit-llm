/*
 * @test
 * @summary Test getShort method throws IllegalArgumentException as expected
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("dummyField");
        UnsafeQualifiedStaticObjectFieldAccessorImpl accessor = new UnsafeQualifiedStaticObjectFieldAccessorImpl(field, false);

        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static Object dummyField = null;
    }
}
