/*
 * @test
 * @summary Test UnsafeQualifiedObjectFieldAccessorImpl.getInt method throws expected IllegalArgumentException
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

            // Call getInt and expect IllegalArgumentException as per current implementation
            accessor.getInt(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception caught, test passes
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: Field not found", e);
        }
    }

    static class Dummy {
        public Object value = null;
    }
}
