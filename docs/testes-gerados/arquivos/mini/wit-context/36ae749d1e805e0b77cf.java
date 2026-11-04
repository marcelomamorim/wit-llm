/*
 * @test
 * @summary Test UnsafeFloatFieldAccessorImpl.getChar throws IllegalArgumentException
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeFloatFieldAccessorImpl;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeFloatFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeFloatFieldAccessorImpl accessor = new UnsafeFloatFieldAccessorImpl(dummyField);

        // Test that getChar always throws IllegalArgumentException
        try {
            accessor.getChar(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getChar");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private float dummyField;
}
