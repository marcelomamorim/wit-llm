/*
 * @test
 * @summary Test UnsafeFloatFieldAccessorImpl.getLong throws IllegalArgumentException
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeFloatFieldAccessorImpl;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeFloatFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeFloatFieldAccessorImpl accessor = new UnsafeFloatFieldAccessorImpl(dummyField);

            // Call getLong and expect IllegalArgumentException
            try {
                accessor.getLong(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getLong");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private float dummyField;
}
