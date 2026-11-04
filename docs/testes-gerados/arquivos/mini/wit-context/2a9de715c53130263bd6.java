/*
 * @test
 * @summary Test UnsafeObjectFieldAccessorImpl.getByte method throws expected IllegalArgumentException
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeObjectFieldAccessorImpl;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeObjectFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(dummyField);

            // Call getByte and expect IllegalArgumentException
            try {
                accessor.getByte(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain Field instance for constructor
    @SuppressWarnings("unused")
    private byte dummyField;
}
