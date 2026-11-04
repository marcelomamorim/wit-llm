/*
 * @test
 * @summary Test UnsafeIntegerFieldAccessorImpl.getShort method throws expected exception
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

            // The getShort method always throws IllegalArgumentException
            try {
                accessor.getShort(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain Field instance for constructor
    @SuppressWarnings("unused")
    private int dummyField = 0;
}
