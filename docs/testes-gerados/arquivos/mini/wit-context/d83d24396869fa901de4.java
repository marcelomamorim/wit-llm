/*
 * @test
 * @summary Test UnsafeQualifiedLongFieldAccessorImpl.getShort always throws IllegalArgumentException
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedLongFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");

        // Create instance with isReadOnly = false (value irrelevant for getShort)
        UnsafeQualifiedLongFieldAccessorImpl accessor = new UnsafeQualifiedLongFieldAccessorImpl(dummyField, false);

        // Test that getShort always throws IllegalArgumentException
        try {
            accessor.getShort(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }

        // Also test getShort with null argument
        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort with null");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private long dummyField = 0L;
}
