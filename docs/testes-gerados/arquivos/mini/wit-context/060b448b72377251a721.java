/*
 * @test
 * @summary Test UnsafeIntegerFieldAccessorImpl.getBoolean throws expected IllegalArgumentException
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

            try {
                accessor.getBoolean(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getBoolean");
            } catch (IllegalArgumentException expected) {
                // Expected path: getBoolean throws IllegalArgumentException
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain Field instance for constructor
    @SuppressWarnings("unused")
    private int dummyField = 0;
}
