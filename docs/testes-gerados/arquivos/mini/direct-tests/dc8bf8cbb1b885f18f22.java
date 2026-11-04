/*
 * @test
 * @summary Test that UnsafeQualifiedObjectFieldAccessorImpl.getBoolean always throws IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("obj");
        UnsafeQualifiedObjectFieldAccessorImpl accessor = new UnsafeQualifiedObjectFieldAccessorImpl(field, false);

        try {
            accessor.getBoolean(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        Object obj = null;
    }
}
