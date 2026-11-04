/*
 * @test
 * @summary Test UnsafeQualifiedIntegerFieldAccessorImpl.getShort always throws IllegalArgumentException
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeQualifiedIntegerFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeQualifiedIntegerFieldAccessorImpl accessor = new UnsafeQualifiedIntegerFieldAccessorImpl(dummyField, false);

            try {
                accessor.getShort(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort");
            } catch (IllegalArgumentException expected) {
                // Expected exception
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain a Field instance
    @SuppressWarnings("unused")
    private int dummyField;
}
