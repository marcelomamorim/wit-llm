/*
 * @test
 * @summary Test that UnsafeQualifiedObjectFieldAccessorImpl.getBoolean always throws IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = Dummy.class.getDeclaredField("value");

        // Create instance with isReadOnly = false
        UnsafeQualifiedObjectFieldAccessorImpl accessor = new UnsafeQualifiedObjectFieldAccessorImpl(dummyField, false);

        boolean threw = false;
        try {
            accessor.getBoolean(new Dummy());
        } catch (IllegalArgumentException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException not thrown by getBoolean");
        }
    }

    // Dummy class with a field to obtain Field instance
    static class Dummy {
        public Object value = null;
    }
}
