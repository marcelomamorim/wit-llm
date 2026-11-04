/*
 * @test
 * @summary Test that getChar(Object) always throws IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor (using reflection)
        Field dummyField = Dummy.class.getDeclaredField("value");

        UnsafeQualifiedObjectFieldAccessorImpl accessor = new UnsafeQualifiedObjectFieldAccessorImpl(dummyField, false);

        try {
            accessor.getChar(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown when obj is null");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static class Dummy {
        public Object value = null;
    }
}
