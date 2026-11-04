/*
 * @test
 * @summary Test UnsafeQualifiedObjectFieldAccessorImpl.getShort throws expected IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = Dummy.class.getDeclaredField("value");
            UnsafeQualifiedObjectFieldAccessorImpl accessor = new UnsafeQualifiedObjectFieldAccessorImpl(dummyField, false);

            // Call getShort and expect IllegalArgumentException
            try {
                accessor.getShort(new Dummy());
                throw new RuntimeException("Expected IllegalArgumentException not thrown");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: field not found", e);
        }
    }

    static class Dummy {
        public Object value = null;
    }
}
