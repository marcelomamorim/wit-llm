/*
 * @test
 * @summary Test UnsafeQualifiedObjectFieldAccessorImpl.getChar throws expected IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeQualifiedObjectFieldAccessorImpl accessor = new UnsafeQualifiedObjectFieldAccessorImpl(dummyField, false);

            // Call getChar and expect IllegalArgumentException
            accessor.getChar(new Object());
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception caught, test passes
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain Field instance
    private Object dummyField;
}
