/*
 * @test
 * @summary Test UnsafeIntegerFieldAccessorImpl.getChar throws expected IllegalArgumentException
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl;

public class UnsafeIntegerFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeIntegerFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeIntegerFieldAccessorImpl accessor = new UnsafeIntegerFieldAccessorImpl(dummyField);

            // The getChar method always throws IllegalArgumentException
            try {
                accessor.getChar(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getChar");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain a Field instance for constructor
    @SuppressWarnings("unused")
    private int dummyField;
}
