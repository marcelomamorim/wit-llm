/*
 * @test
 * @summary Test that getFloat(Object) always throws IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy field to pass to constructor
        Field field = Dummy.class.getDeclaredField("value");
        UnsafeQualifiedObjectFieldAccessorImpl accessor = new UnsafeQualifiedObjectFieldAccessorImpl(field, false);

        try {
            accessor.getFloat(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            accessor.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown when obj is null");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static class Dummy {
        public Object value = null;
    }
}
