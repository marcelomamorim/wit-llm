/*
 * @test
 * @summary Test that getLong(Object) always throws IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor
        Field dummyField = Dummy.class.getDeclaredField("value");

        // Create instance with isReadOnly false
        UnsafeQualifiedObjectFieldAccessorImpl accessor = new UnsafeQualifiedObjectFieldAccessorImpl(dummyField, false);

        // Test getLong throws IllegalArgumentException
        try {
            accessor.getLong(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getLong throws IllegalArgumentException even with null
        try {
            accessor.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for null");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static class Dummy {
        public Object value = null;
    }
}
